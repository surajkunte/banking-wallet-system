package com.suraj.bank.reconciliationservice.service;

import com.suraj.bank.reconciliationservice.dto.ReconciliationRequest;
import com.suraj.bank.reconciliationservice.entity.ReconciliationRun;
import com.suraj.bank.reconciliationservice.repository.ReconciliationRunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReconciliationService {
    private final ReconciliationRunRepository repository;

    public ReconciliationRun run(ReconciliationRequest request) {
        long variance = request.getLedgerCount() - request.getExternalCount();
        return repository.save(ReconciliationRun.builder()
                .source(request.getSource())
                .businessDate(request.getBusinessDate() == null ? LocalDate.now() : request.getBusinessDate())
                .ledgerCount(request.getLedgerCount())
                .externalCount(request.getExternalCount())
                .variance(variance)
                .status(variance == 0 ? "MATCHED" : "MISMATCHED")
                .createdAt(LocalDateTime.now())
                .build());
    }

    public List<ReconciliationRun> runs() {
        return repository.findAllByOrderByCreatedAtDesc();
    }
}
