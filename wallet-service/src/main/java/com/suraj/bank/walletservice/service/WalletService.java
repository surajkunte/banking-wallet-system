package com.suraj.bank.walletservice.service;

import com.suraj.bank.walletservice.dto.CreateWalletRequest;
import com.suraj.bank.walletservice.entity.Wallet;
import com.suraj.bank.walletservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.suraj.bank.walletservice.dto.BalanceUpdateRequest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    public Wallet createWallet(CreateWalletRequest request) {

        if (walletRepository.existsByUserId(request.getUserId())) {
            throw new RuntimeException("Wallet already exists for this user");
        }

        Wallet wallet = Wallet.builder()
                .userId(request.getUserId())
                .balance(BigDecimal.ZERO)
                .currency(request.getCurrency())
                .status("ACTIVE")
                .build();

        return walletRepository.save(wallet);
    }

    public Wallet getWalletByUserId(Long userId) {
        return walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }
    @Transactional
    public Wallet credit(BalanceUpdateRequest request) {

        Wallet wallet = getWalletByUserId(request.getUserId());

        wallet.setBalance(
                wallet.getBalance().add(request.getAmount())
        );

        return walletRepository.save(wallet);
    }
    @Transactional
    public Wallet debit(BalanceUpdateRequest request) {

        Wallet wallet = getWalletByUserId(request.getUserId());

        if (wallet.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        wallet.setBalance(
                wallet.getBalance().subtract(request.getAmount())
        );

        return walletRepository.save(wallet);
    }
}