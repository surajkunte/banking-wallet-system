package com.suraj.bank.ledgerservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ledger_entries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Wallet affected by this entry
    @Column(name = "wallet_id", nullable = false)
    private Long walletId;

    // CREDIT or DEBIT
    @Column(nullable = false)
    private String entryType;

    // Amount of the entry
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    // Business reference (transfer ID, order ID, etc.)
    @Column(name = "reference_id")
    private String referenceId;

    // Human-readable description
    private String description;

    // Timestamp of entry creation
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}