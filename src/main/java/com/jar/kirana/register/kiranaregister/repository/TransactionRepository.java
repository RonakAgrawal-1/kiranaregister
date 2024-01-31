package com.jar.kirana.register.kiranaregister.repository;

import com.jar.kirana.register.kiranaregister.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
