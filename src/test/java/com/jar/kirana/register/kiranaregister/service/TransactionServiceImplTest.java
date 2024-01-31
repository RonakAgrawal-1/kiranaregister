package com.jar.kirana.register.kiranaregister.service;

import com.jar.kirana.register.kiranaregister.constants.Constants;
import com.jar.kirana.register.kiranaregister.exception.CurrencyExchangeRateNotFoundException;
import com.jar.kirana.register.kiranaregister.model.Transaction;
import com.jar.kirana.register.kiranaregister.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CurrencyConversionService currencyConversionService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void testRecordTransaction_Success_INR() {
        Transaction validTransaction = createTransaction("credit", Constants.CURRENCY_INR, BigDecimal.TEN);
        transactionService.recordTransaction(validTransaction);

        Mockito.verify(transactionRepository).save(validTransaction);
    }

    @Test
    void testRecordTransaction_Success_OtherCurrency() {
        Transaction validTransaction = createTransaction("debit", "EUR", BigDecimal.TEN);

        when(currencyConversionService.getExchangeRate("EUR", Constants.CURRENCY_USD)).thenReturn(BigDecimal.valueOf(2));
        transactionService.recordTransaction(validTransaction);

        Mockito.verify(transactionRepository).save(validTransaction);
        assertEquals(0, BigDecimal.valueOf(5).compareTo(validTransaction.getAmount()));
        assertEquals(Constants.CURRENCY_USD, validTransaction.getCurrency());
    }


    @Test
    void testRecordTransaction_CurrencyExchangeRateNotFoundException() {
        Transaction invalidTransaction = createTransaction("credit","GBP", BigDecimal.TEN);

        when(currencyConversionService.getExchangeRate("GBP", Constants.CURRENCY_USD))
                .thenThrow(new CurrencyExchangeRateNotFoundException("Exchange rate not found"));

        CurrencyExchangeRateNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CurrencyExchangeRateNotFoundException.class,
                () -> transactionService.recordTransaction(invalidTransaction)
        );

        assertEquals("Exchange rate not found", exception.getMessage());
    }

    @Test
    void testGetDailyTransactions_Success() {
        LocalDate validDate = LocalDate.now();
        LocalDateTime startDateTime = validDate.atStartOfDay();
        LocalDateTime endDateTime = validDate.atTime(23, 59, 59);

        List<Transaction> mockTransactions = Collections.singletonList(createTransaction("debit", Constants.CURRENCY_INR, BigDecimal.TEN));
        when(transactionRepository.findByTimestampBetween(startDateTime, endDateTime)).thenReturn(mockTransactions);

        List<Transaction> result = transactionService.getDailyTransactions(validDate);

        assertEquals(mockTransactions, result);
    }

    private Transaction createTransaction(String transactionType, String currency, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setTransactionType(transactionType);
        return transaction;
    }
}
