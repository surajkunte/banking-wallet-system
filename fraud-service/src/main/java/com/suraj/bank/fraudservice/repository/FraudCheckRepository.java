package com.suraj.bank.fraudservice.repository;

import com.suraj.bank.fraudservice.entity.FraudCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudCheckRepository extends JpaRepository<FraudCheck, Long> {
}
