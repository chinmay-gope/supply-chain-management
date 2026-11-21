package com.supplychainmanagement.dto.response;


import java.math.BigDecimal;

public record PurchaseOrderItemResponse(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        Integer receivedQuantity,
        BigDecimal unitPrice,
        BigDecimal subtotal

) {}
