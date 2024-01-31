package com.jar.kirana.register.kiranaregister.controller;

import com.jar.kirana.register.kiranaregister.exception.CurrencyExchangeRateNotFoundException;
import com.jar.kirana.register.kiranaregister.model.Transaction;
import com.jar.kirana.register.kiranaregister.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/record")
    public ResponseEntity<String> recordTransaction(@RequestBody Transaction transaction) {
        try {
            transactionService.recordTransaction(transaction);
            return ResponseEntity.ok("Transaction recorded successfully");
        } catch (CurrencyExchangeRateNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error recording transaction: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error recording transaction: " + e.getMessage());
        }
    }

    @GetMapping("/daily-report")
    public ResponseEntity<?> getDailyTransactions(@RequestParam
                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<Transaction> dailyTransactions = transactionService.getDailyTransactions(date);
            return ResponseEntity.ok(dailyTransactions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching daily transactions: " + e.getMessage());
        }
    }
}
