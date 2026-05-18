package com.suraj.bank.kycservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "kyc_cases")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KycCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false)
    private String documentType;

    @Column(nullable = false)
    private String documentNumber;

    @Column(nullable = false)
    private String status;

    private String reviewer;
    private String remarks;
    private LocalDateTime submittedAt;
    private LocalDateTime reviewedAt;
}
