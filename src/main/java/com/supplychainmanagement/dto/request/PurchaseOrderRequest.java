package com.supplychainmanagement.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record PurchaseOrderRequest(

        @NotNull(message = "Supplier ID is required")
        Long supplierId,

        @NotNull(message = "User ID is required")
        Long createdById,

        @NotNull(message = "Order date is required")
        LocalDate orderDate,

        @NotNull(message = "Expected delivery date is required")
        LocalDate expectedDeliveryDate,

        @NotEmpty(message = "At least one product is required")
        List<@Valid PurchaseOrderItemRequest> items,

        @Size(max = 500, message = "Notes cannot exceed 500 characters")
        String notes

) {}
