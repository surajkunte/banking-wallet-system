package com.suraj.bank.kycservice.repository;

import com.suraj.bank.kycservice.entity.KycCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KycCaseRepository extends JpaRepository<KycCase, Long> {
    Optional<KycCase> findByUserId(Long userId);
}
