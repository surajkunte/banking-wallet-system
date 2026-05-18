package com.suraj.bank.walletservice.service;

import com.suraj.bank.walletservice.dto.BalanceUpdateRequest;
import com.suraj.bank.walletservice.dto.CreateWalletRequest;
import com.suraj.bank.walletservice.entity.Wallet;
import com.suraj.bank.walletservice.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    private Wallet wallet;

    @BeforeEach
    void setUp() {
        wallet = Wallet.builder()
                .id(1L)
                .userId(1L)
                .balance(new BigDecimal("1000.00"))
                .currency("INR")
                .status("ACTIVE")
                .build();
    }

    @Test
    void shouldCreateWallet() {
        CreateWalletRequest request = new CreateWalletRequest();
        request.setUserId(1L);
        request.setCurrency("INR");

        when(walletRepository.existsByUserId(1L)).thenReturn(false);
        when(walletRepository.save(any(Wallet.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Wallet result = walletService.createWallet(request);

        assertEquals(1L, result.getUserId());
        assertEquals(BigDecimal.ZERO, result.getBalance());
        assertEquals("INR", result.getCurrency());
    }

    @Test
    void shouldCreditWallet() {
        BalanceUpdateRequest request = new BalanceUpdateRequest();
        request.setUserId(1L);
        request.setAmount(new BigDecimal("500.00"));

        when(walletRepository.findByUserId(1L))
                .thenReturn(Optional.of(wallet));
        when(walletRepository.save(any(Wallet.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Wallet result = walletService.credit(request);

        assertEquals(
                new BigDecimal("1500.00"),
                result.getBalance()
        );
    }

    @Test
    void shouldDebitWallet() {
        BalanceUpdateRequest request = new BalanceUpdateRequest();
        request.setUserId(1L);
        request.setAmount(new BigDecimal("300.00"));

        when(walletRepository.findByUserId(1L))
                .thenReturn(Optional.of(wallet));
        when(walletRepository.save(any(Wallet.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Wallet result = walletService.debit(request);

        assertEquals(
                new BigDecimal("700.00"),
                result.getBalance()
        );
    }

    @Test
    void shouldThrowWhenInsufficientBalance() {
        BalanceUpdateRequest request = new BalanceUpdateRequest();
        request.setUserId(1L);
        request.setAmount(new BigDecimal("2000.00"));

        when(walletRepository.findByUserId(1L))
                .thenReturn(Optional.of(wallet));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> walletService.debit(request)
        );

        assertEquals(
                "Insufficient balance",
                exception.getMessage()
        );
    }
}