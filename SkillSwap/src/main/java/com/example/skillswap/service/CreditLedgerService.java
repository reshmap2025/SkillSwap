package com.example.skillswap.service;

import com.example.skillswap.entity.CreditLedger;
import com.example.skillswap.repository.CreditLedgerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditLedgerService {

    private final CreditLedgerRepository creditLedgerRepository;

    public CreditLedgerService(CreditLedgerRepository creditLedgerRepository) {
        this.creditLedgerRepository = creditLedgerRepository;
    }

    public CreditLedger createLedger(CreditLedger ledger) {
        return creditLedgerRepository.save(ledger);
    }

    public List<CreditLedger> getAllLedgerEntries() {
        return creditLedgerRepository.findAll();
    }

    public CreditLedger getLedgerById(Long id) {
        return creditLedgerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ledger entry not found"));
    }
}