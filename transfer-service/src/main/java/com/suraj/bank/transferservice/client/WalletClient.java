package com.suraj.bank.transferservice.client;

import com.suraj.bank.transferservice.dto.BalanceUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "wallet-service", url = "http://localhost:8083")
public interface WalletClient {

    @PostMapping("/wallets/debit")
    void debit(@RequestBody BalanceUpdateRequest request);

    @PostMapping("/wallets/credit")
    void credit(@RequestBody BalanceUpdateRequest request);
}