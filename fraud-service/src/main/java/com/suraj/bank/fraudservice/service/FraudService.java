package com.suraj.bank.fraudservice.service;

import com.suraj.bank.fraudservice.dto.FraudCheckRequest;
import com.suraj.bank.fraudservice.entity.FraudCheck;
import com.suraj.bank.fraudservice.repository.FraudCheckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FraudService {
    private static final BigDecimal MANUAL_REVIEW_LIMIT = new BigDecimal("50000.00");

    private final FraudCheckRepository repository;

    public FraudCheck check(FraudCheckRequest request) {
        boolean review = request.getAmount().compareTo(MANUAL_REVIEW_LIMIT) > 0;
        return repository.save(FraudCheck.builder()
                .userId(request.getUserId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .referenceId(request.getReferenceId())
                .decision(review ? "REVIEW" : "APPROVED")
                .reason(review ? "Amount exceeds manual review threshold" : "Rules passed")
                .checkedAt(LocalDateTime.now())
                .build());
    }
}
