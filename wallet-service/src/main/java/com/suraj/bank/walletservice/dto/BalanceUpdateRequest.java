package com.suraj.bank.walletservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceUpdateRequest {

    @NotNull
    private Long userId;

    @NotNull
    @DecimalMin(value = "0.01")
    //Prevents invalid amounts such as:
    //
    //0
    //-10
    private BigDecimal amount;
}