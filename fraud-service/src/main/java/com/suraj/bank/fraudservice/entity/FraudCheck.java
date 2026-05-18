package com.suraj.bank.fraudservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fraud_checks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FraudCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private String currency;
    private String referenceId;
    private String decision;
    private String reason;
    private LocalDateTime checkedAt;
}
