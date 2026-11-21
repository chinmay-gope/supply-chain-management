package com.supplychainmanagement.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.supplychainmanagement.entity.TransactionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockTransactionResponse {

    private Long id;
    private Long productId;
    private String productName;
    private TransactionType transactionType;
    private Integer quantity;
    private Integer previousStock;
    private Integer newStock;
    private BigDecimal unitPrice;
    private BigDecimal totalAmount;
    private Long referenceId;
    private String notes;
    private Long performedBy;
    private LocalDateTime createdAt;
}