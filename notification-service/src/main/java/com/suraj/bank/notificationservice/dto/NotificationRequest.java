package com.suraj.bank.notificationservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationRequest {
    @NotNull
    private Long userId;

    @NotBlank
    private String channel;

    @NotBlank
    private String message;
}
