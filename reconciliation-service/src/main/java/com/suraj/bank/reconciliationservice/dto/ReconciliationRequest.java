package com.suraj.bank.reconciliationservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReconciliationRequest {
    @NotBlank
    private String source;

    private LocalDate businessDate;
    private long ledgerCount;
    private long externalCount;
}
