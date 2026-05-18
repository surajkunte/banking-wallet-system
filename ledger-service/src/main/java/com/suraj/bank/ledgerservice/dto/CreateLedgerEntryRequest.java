package com.suraj.bank.ledgerservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateLedgerEntryRequest {

    @NotNull
    private Long walletId;

    @NotBlank
    private String entryType; // CREDIT or DEBIT

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    private String referenceId;

    private String description;
}