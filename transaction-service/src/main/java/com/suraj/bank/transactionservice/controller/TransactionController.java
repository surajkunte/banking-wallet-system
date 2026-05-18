package com.suraj.bank.transactionservice.controller;

import com.suraj.bank.transactionservice.dto.CreateTransactionRequest;
import com.suraj.bank.transactionservice.entity.WalletTransaction;
import com.suraj.bank.transactionservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public WalletTransaction create(@Valid @RequestBody CreateTransactionRequest request) {
        return transactionService.create(request);
    }

    @GetMapping("/{id}")
    public WalletTransaction get(@PathVariable Long id) {
        return transactionService.get(id);
    }

    @GetMapping("/users/{userId}")
    public List<WalletTransaction> byUser(@PathVariable Long userId) {
        return transactionService.byUser(userId);
    }
}
