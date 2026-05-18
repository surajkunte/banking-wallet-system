package com.suraj.bank.customerservice.controller;

import com.suraj.bank.customerservice.dto.CreateCustomerRequest;
import com.suraj.bank.customerservice.entity.Customer;
import com.suraj.bank.customerservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public Customer createCustomer(
            @Valid @RequestBody CreateCustomerRequest request) {
        return customerService.createCustomer(request);
    }

    @GetMapping("/{userId}")
    public Customer getCustomer(@PathVariable Long userId) {
        return customerService.getCustomerByUserId(userId);
    }
}