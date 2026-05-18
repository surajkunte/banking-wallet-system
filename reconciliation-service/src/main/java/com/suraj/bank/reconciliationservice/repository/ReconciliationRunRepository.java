package com.suraj.bank.reconciliationservice.repository;

import com.suraj.bank.reconciliationservice.entity.ReconciliationRun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReconciliationRunRepository extends JpaRepository<ReconciliationRun, Long> {
    List<ReconciliationRun> findAllByOrderByCreatedAtDesc();
}
