package com.suraj.bank.kycservice.service;

import com.suraj.bank.kycservice.dto.ReviewKycRequest;
import com.suraj.bank.kycservice.dto.SubmitKycRequest;
import com.suraj.bank.kycservice.entity.KycCase;
import com.suraj.bank.kycservice.repository.KycCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KycService {

    private final KycCaseRepository repository;

    public KycCase submit(SubmitKycRequest request) {
        return repository.findByUserId(request.getUserId())
                .map(existing -> {
                    existing.setDocumentType(request.getDocumentType());
                    existing.setDocumentNumber(request.getDocumentNumber());
                    existing.setStatus("PENDING");
                    existing.setSubmittedAt(LocalDateTime.now());
                    return repository.save(existing);
                })
                .orElseGet(() -> repository.save(KycCase.builder()
                        .userId(request.getUserId())
                        .documentType(request.getDocumentType())
                        .documentNumber(request.getDocumentNumber())
                        .status("PENDING")
                        .submittedAt(LocalDateTime.now())
                        .build()));
    }

    public KycCase getByUserId(Long userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("KYC case not found"));
    }

    public KycCase review(Long id, ReviewKycRequest request) {
        KycCase kycCase = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("KYC case not found"));
        kycCase.setStatus(request.getStatus());
        kycCase.setReviewer(request.getReviewer());
        kycCase.setRemarks(request.getRemarks());
        kycCase.setReviewedAt(LocalDateTime.now());
        return repository.save(kycCase);
    }
}
