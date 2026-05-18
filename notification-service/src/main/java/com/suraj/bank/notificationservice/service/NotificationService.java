package com.suraj.bank.notificationservice.service;

import com.suraj.bank.notificationservice.dto.NotificationRequest;
import com.suraj.bank.notificationservice.entity.Notification;
import com.suraj.bank.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository repository;

    public Notification send(NotificationRequest request) {
        return repository.save(Notification.builder()
                .userId(request.getUserId())
                .channel(request.getChannel())
                .message(request.getMessage())
                .status("SENT")
                .createdAt(LocalDateTime.now())
                .build());
    }

    public List<Notification> byUser(Long userId) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
