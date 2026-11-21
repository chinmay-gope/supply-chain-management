package com.supplychainmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supplychainmanagement.entity.StockTransaction;
import com.supplychainmanagement.entity.TransactionType;

public interface StockTransactionRepository
        extends JpaRepository<StockTransaction, Long> {

    List<StockTransaction> findByProduct_IdOrderByCreatedAtDesc(
            Long productId);

    List<StockTransaction> findByTransactionType(
            TransactionType transactionType);

    List<StockTransaction> findAllByOrderByCreatedAtDesc();
}