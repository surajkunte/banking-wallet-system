package com.suraj.bank.notificationservice.controller;

import com.suraj.bank.notificationservice.dto.NotificationRequest;
import com.suraj.bank.notificationservice.entity.Notification;
import com.suraj.bank.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    public Notification send(@Valid @RequestBody NotificationRequest request) {
        return notificationService.send(request);
    }

    @GetMapping("/users/{userId}")
    public List<Notification> byUser(@PathVariable Long userId) {
        return notificationService.byUser(userId);
    }
}
