package com.suraj.bank.kycservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubmitKycRequest {
    @NotNull
    private Long userId;

    @NotBlank
    private String documentType;

    @NotBlank
    private String documentNumber;
}
