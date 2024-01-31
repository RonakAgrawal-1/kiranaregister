package com.jar.kirana.register.kiranaregister.service;

import com.jar.kirana.register.kiranaregister.constants.Constants;
import com.jar.kirana.register.kiranaregister.model.Transaction;
import com.jar.kirana.register.kiranaregister.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CurrencyConversionService currencyConversionService;

    @Override
    public void recordTransaction(Transaction transaction) {
        if (transaction == null || transaction.getAmount() == null || transaction.getTimestamp() == null) {
            throw new IllegalArgumentException("Invalid transaction data");
        }
        // If the currency is INR, keep it as is
        if (Constants.CURRENCY_INR.equals(transaction.getCurrency())) {
            transactionRepository.save(transaction);
        } else {
            // Convert amount to USD for currencies other than INR
            BigDecimal exchangeRate = currencyConversionService.getExchangeRate(transaction.getCurrency(), Constants.CURRENCY_USD);
            transaction.setAmount(transaction.getAmount().divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP));
            transaction.setCurrency(Constants.CURRENCY_USD);
            transactionRepository.save(transaction);
        }
    }

    @Override
    public List<Transaction> getDailyTransactions(LocalDate date) {
        LocalDate start = date.atStartOfDay().toLocalDate();
        LocalDate end = date.atTime(23, 59, 59).toLocalDate();
        return transactionRepository.findByTimestampBetween(start.atStartOfDay(), end.atTime(23, 59, 59));
    }
}
