package com.suraj.bank.kycservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewKycRequest {
    @NotBlank
    private String status;

    private String reviewer;
    private String remarks;
}
