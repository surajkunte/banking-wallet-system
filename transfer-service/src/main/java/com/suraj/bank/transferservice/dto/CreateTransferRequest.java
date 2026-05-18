package com.suraj.bank.transferservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateTransferRequest {

    @NotNull
    private Long fromUserId;

    @NotNull
    private Long toUserId;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    private String currency = "INR";

    @NotBlank
    private String idempotencyKey;
}