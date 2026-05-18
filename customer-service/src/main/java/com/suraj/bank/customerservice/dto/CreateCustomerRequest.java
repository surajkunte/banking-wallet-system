package com.suraj.bank.customerservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateCustomerRequest {

    private Long userId;

    @NotBlank
    private String fullName;

    @NotBlank
    private String phone;

    private LocalDate dateOfBirth;

    private String address;
}