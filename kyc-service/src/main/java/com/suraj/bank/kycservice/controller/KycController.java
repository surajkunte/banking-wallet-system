package com.suraj.bank.kycservice.controller;

import com.suraj.bank.kycservice.dto.ReviewKycRequest;
import com.suraj.bank.kycservice.dto.SubmitKycRequest;
import com.suraj.bank.kycservice.entity.KycCase;
import com.suraj.bank.kycservice.service.KycService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kyc")
@RequiredArgsConstructor
public class KycController {

    private final KycService kycService;

    @PostMapping
    public KycCase submit(@Valid @RequestBody SubmitKycRequest request) {
        return kycService.submit(request);
    }

    @GetMapping("/users/{userId}")
    public KycCase getByUserId(@PathVariable Long userId) {
        return kycService.getByUserId(userId);
    }

    @PostMapping("/{id}/review")
    public KycCase review(@PathVariable Long id, @Valid @RequestBody ReviewKycRequest request) {
        return kycService.review(id, request);
    }
}
