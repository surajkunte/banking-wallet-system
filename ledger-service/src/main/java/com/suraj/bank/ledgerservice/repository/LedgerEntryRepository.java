package com.suraj.bank.ledgerservice.repository;

import com.suraj.bank.ledgerservice.entity.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LedgerEntryRepository
        extends JpaRepository<LedgerEntry, Long> {

    List<LedgerEntry> findByWalletIdOrderByCreatedAtDesc(Long walletId);
}