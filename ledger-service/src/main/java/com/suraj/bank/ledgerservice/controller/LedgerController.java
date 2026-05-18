package com.suraj.bank.ledgerservice.controller;

import com.suraj.bank.ledgerservice.dto.CreateLedgerEntryRequest;
import com.suraj.bank.ledgerservice.entity.LedgerEntry;
import com.suraj.bank.ledgerservice.service.LedgerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ledger")
@RequiredArgsConstructor
public class LedgerController {

    private final LedgerService ledgerService;

    @PostMapping
    public LedgerEntry createEntry(
            @Valid @RequestBody CreateLedgerEntryRequest request) {
        return ledgerService.createEntry(request);
    }

    @GetMapping("/{walletId}")
    public List<LedgerEntry> getEntries(@PathVariable Long walletId) {
        return ledgerService.getEntriesByWalletId(walletId);
    }
}