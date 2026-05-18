package com.suraj.bank.transactionservice.service;

import com.suraj.bank.transactionservice.dto.CreateTransactionRequest;
import com.suraj.bank.transactionservice.entity.WalletTransaction;
import com.suraj.bank.transactionservice.repository.WalletTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final WalletTransactionRepository repository;

    public WalletTransaction create(CreateTransactionRequest request) {
        return repository.save(WalletTransaction.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .referenceId(request.getReferenceId())
                .status("POSTED")
                .createdAt(LocalDateTime.now())
                .build());
    }

    public WalletTransaction get(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public List<WalletTransaction> byUser(Long userId) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
