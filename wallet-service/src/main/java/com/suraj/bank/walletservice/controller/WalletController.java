package com.suraj.bank.walletservice.controller;

import com.suraj.bank.walletservice.dto.BalanceUpdateRequest;
import com.suraj.bank.walletservice.dto.CreateWalletRequest;
import com.suraj.bank.walletservice.entity.Wallet;
import com.suraj.bank.walletservice.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public Wallet createWallet(
            @Valid @RequestBody CreateWalletRequest request) {
        return walletService.createWallet(request);
    }

    @GetMapping("/{userId}")
    public Wallet getWallet(@PathVariable Long userId) {
        return walletService.getWalletByUserId(userId);
    }

    @PostMapping("/credit")
    public Wallet credit(
            @Valid @RequestBody BalanceUpdateRequest request) {
        return walletService.credit(request);
    }

    @PostMapping("/debit")
    public Wallet debit(
            @Valid @RequestBody BalanceUpdateRequest request) {
        return walletService.debit(request);
    }
}