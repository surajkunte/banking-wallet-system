package com.suraj.bank.reconciliationservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reconciliation_runs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReconciliationRun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String source;
    private LocalDate businessDate;
    private long ledgerCount;
    private long externalCount;
    private long variance;
    private String status;
    private LocalDateTime createdAt;
}
