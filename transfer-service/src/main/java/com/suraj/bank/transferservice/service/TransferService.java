package com.suraj.bank.transferservice.service;

import com.suraj.bank.transferservice.client.LedgerClient;
import com.suraj.bank.transferservice.client.WalletClient;
import com.suraj.bank.transferservice.dto.BalanceUpdateRequest;
import com.suraj.bank.transferservice.dto.CreateLedgerEntryRequest;
import com.suraj.bank.transferservice.dto.CreateTransferRequest;
import com.suraj.bank.transferservice.entity.Transfer;
import com.suraj.bank.transferservice.exception.InvalidTransferException;
import com.suraj.bank.transferservice.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;
    private final WalletClient walletClient;
    private final LedgerClient ledgerClient;

    @Transactional
    public Transfer transfer(CreateTransferRequest request) {

        // Idempotency check
        return transferRepository.findByIdempotencyKey(
                request.getIdempotencyKey()
        ).orElseGet(() -> executeTransfer(request));
    }

    private Transfer executeTransfer(CreateTransferRequest request) {
        if (request.getFromUserId().equals(request.getToUserId())) {
            throw new InvalidTransferException(
                    "Sender and receiver cannot be the same"
            );
        }

        Transfer transfer = Transfer.builder()
                .fromUserId(request.getFromUserId())
                .toUserId(request.getToUserId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status("PENDING")
                .idempotencyKey(request.getIdempotencyKey())
                .createdAt(LocalDateTime.now())
                .build();

        transfer = transferRepository.save(transfer);

        try {
            // Debit sender wallet
            walletClient.debit(
                    new BalanceUpdateRequest(
                            request.getFromUserId(),
                            request.getAmount()
                    )
            );

            // Credit receiver wallet
            walletClient.credit(
                    new BalanceUpdateRequest(
                            request.getToUserId(),
                            request.getAmount()
                    )
            );

            // Ledger entry for sender
            ledgerClient.createEntry(
                    new CreateLedgerEntryRequest(
                            request.getFromUserId(),
                            "DEBIT",
                            request.getAmount(),
                            transfer.getId().toString(),
                            "Transfer to user " + request.getToUserId()
                    )
            );

            // Ledger entry for receiver
            ledgerClient.createEntry(
                    new CreateLedgerEntryRequest(
                            request.getToUserId(),
                            "CREDIT",
                            request.getAmount(),
                            transfer.getId().toString(),
                            "Transfer from user " + request.getFromUserId()
                    )
            );

            transfer.setStatus("SUCCESS");

        } catch (Exception e) {
            transfer.setStatus("FAILED");
            transfer.setFailureReason(e.getMessage());
        }


        return transferRepository.save(transfer);
    }
}