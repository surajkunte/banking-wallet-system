package com.suraj.bank.fraudservice.controller;

import com.suraj.bank.fraudservice.dto.FraudCheckRequest;
import com.suraj.bank.fraudservice.entity.FraudCheck;
import com.suraj.bank.fraudservice.service.FraudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fraud")
@RequiredArgsConstructor
public class FraudController {
    private final FraudService fraudService;

    @PostMapping("/check")
    public FraudCheck check(@Valid @RequestBody FraudCheckRequest request) {
        return fraudService.check(request);
    }
}
