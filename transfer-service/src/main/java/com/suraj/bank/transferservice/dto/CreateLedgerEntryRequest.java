package com.suraj.bank.transferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CreateLedgerEntryRequest {

    private Long walletId;
    private String entryType;
    private BigDecimal amount;
    private String referenceId;
    private String description;
}