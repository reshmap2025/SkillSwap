package com.example.skillswap.repository;

import com.example.skillswap.entity.CreditLedger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditLedgerRepository extends JpaRepository<CreditLedger, Long> {
}