package com.supplychainmanagement.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseOrderItemRequest(

        @NotNull(message = "Product ID is required")
        Long productId,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be greater than zero")
        Integer quantity,

        @NotNull(message = "Unit price is required")
        @Positive(message = "Unit price must be greater than zero")
        java.math.BigDecimal unitPrice

) {
}
