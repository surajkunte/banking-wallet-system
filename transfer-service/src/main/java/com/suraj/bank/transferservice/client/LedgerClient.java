package com.suraj.bank.transferservice.client;

import com.suraj.bank.transferservice.dto.CreateLedgerEntryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ledger-service", url = "http://localhost:8084")
public interface LedgerClient {

    @PostMapping("/ledger")
    void createEntry(@RequestBody CreateLedgerEntryRequest request);
}