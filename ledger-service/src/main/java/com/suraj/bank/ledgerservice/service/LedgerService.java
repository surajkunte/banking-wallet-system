package com.suraj.bank.ledgerservice.service;

import com.suraj.bank.ledgerservice.dto.CreateLedgerEntryRequest;
import com.suraj.bank.ledgerservice.entity.LedgerEntry;
import com.suraj.bank.ledgerservice.repository.LedgerEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LedgerService {

    private final LedgerEntryRepository ledgerEntryRepository;

    public LedgerEntry createEntry(CreateLedgerEntryRequest request) {

        LedgerEntry entry = LedgerEntry.builder()
                .walletId(request.getWalletId())
                .entryType(request.getEntryType())
                .amount(request.getAmount())
                .referenceId(request.getReferenceId())
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .build();

        return ledgerEntryRepository.save(entry);
    }

    public List<LedgerEntry> getEntriesByWalletId(Long walletId) {
        return ledgerEntryRepository.findByWalletIdOrderByCreatedAtDesc(walletId);
    }
}