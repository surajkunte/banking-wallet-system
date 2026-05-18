package com.suraj.bank.reconciliationservice.controller;

import com.suraj.bank.reconciliationservice.dto.ReconciliationRequest;
import com.suraj.bank.reconciliationservice.entity.ReconciliationRun;
import com.suraj.bank.reconciliationservice.service.ReconciliationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reconciliation")
@RequiredArgsConstructor
public class ReconciliationController {
    private final ReconciliationService reconciliationService;

    @PostMapping("/runs")
    public ReconciliationRun run(@Valid @RequestBody ReconciliationRequest request) {
        return reconciliationService.run(request);
    }

    @GetMapping("/runs")
    public List<ReconciliationRun> runs() {
        return reconciliationService.runs();
    }
}
