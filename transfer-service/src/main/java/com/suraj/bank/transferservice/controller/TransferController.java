package com.suraj.bank.transferservice.controller;

import com.suraj.bank.transferservice.dto.CreateTransferRequest;
import com.suraj.bank.transferservice.entity.Transfer;
import com.suraj.bank.transferservice.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public Transfer createTransfer(
            @Valid @RequestBody CreateTransferRequest request) {
        return transferService.transfer(request);
    }
}