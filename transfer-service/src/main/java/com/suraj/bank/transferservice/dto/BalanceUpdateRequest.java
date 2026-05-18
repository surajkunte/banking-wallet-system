package com.suraj.bank.transferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BalanceUpdateRequest {

    private Long userId;
    private BigDecimal amount;
}