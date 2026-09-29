package com.example.skillswap.controller;

import com.example.skillswap.entity.CreditLedger;
import com.example.skillswap.service.CreditLedgerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit-ledger")
@CrossOrigin(origins = "*")
public class CreditLedgerController {

    private final CreditLedgerService creditLedgerService;

    public CreditLedgerController(CreditLedgerService creditLedgerService) {
        this.creditLedgerService = creditLedgerService;
    }

    @PostMapping
    public ResponseEntity<CreditLedger> createLedger(
            @RequestBody CreditLedger ledger) {
        return new ResponseEntity<>(
                creditLedgerService.createLedger(ledger),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<CreditLedger>> getAllLedgerEntries() {
        return ResponseEntity.ok(
                creditLedgerService.getAllLedgerEntries()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditLedger> getLedgerById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                creditLedgerService.getLedgerById(id)
        );
    }
}