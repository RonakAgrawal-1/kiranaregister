package com.jar.kirana.register.kiranaregister.service;

import com.jar.kirana.register.kiranaregister.model.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    void recordTransaction(Transaction transaction);

    List<Transaction> getDailyTransactions(LocalDate date);
}
