package com.suraj.bank.walletservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateWalletRequest {

    @NotNull
    private Long userId;

    private String currency = "INR";
}