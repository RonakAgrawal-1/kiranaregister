package com.jar.kirana.register.kiranaregister.controller;

import com.jar.kirana.register.kiranaregister.model.Transaction;
import com.jar.kirana.register.kiranaregister.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRecordTransaction_Success() {
        Transaction validTransaction = new Transaction();
        ResponseEntity<String> response = transactionController.recordTransaction(validTransaction);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals("Transaction recorded successfully", response.getBody());
    }

    @Test
    void testRecordTransaction_GeneralException() {
        Transaction invalidTransaction = new Transaction();
        Mockito.doThrow(new RuntimeException("Some unexpected error"))
                .when(transactionService).recordTransaction(any(Transaction.class));

        ResponseEntity<String> response = transactionController.recordTransaction(invalidTransaction);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals("Error recording transaction: Some unexpected error", response.getBody());
    }

    @Test
    void testGetDailyTransactions_Success() {
        LocalDate validDate = LocalDate.now();
        List<Transaction> mockTransactions = Arrays.asList(new Transaction(), new Transaction());
        when(transactionService.getDailyTransactions(validDate)).thenReturn(mockTransactions);

        ResponseEntity<?> response = transactionController.getDailyTransactions(validDate);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(mockTransactions, response.getBody());
    }

    @Test
    void testGetDailyTransactions_Exception() {
        LocalDate validDate = LocalDate.now();
        Mockito.when(transactionService.getDailyTransactions(validDate))
                .thenThrow(new RuntimeException("Some unexpected error"));

        ResponseEntity<?> response = transactionController.getDailyTransactions(validDate);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("Error fetching daily transactions: Some unexpected error", response.getBody());
    }
}
