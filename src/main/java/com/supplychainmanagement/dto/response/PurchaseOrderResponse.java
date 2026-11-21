package com.supplychainmanagement.dto.response;


import com.supplychainmanagement.entity.PurchaseOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record PurchaseOrderResponse(

        Long id,
        String orderNumber,

        Long supplierId,
        String supplierName,

        Long createdById,
        String createdByName,

        List<PurchaseOrderItemResponse> items,

        BigDecimal totalAmount,

        LocalDate orderDate,
        LocalDate expectedDeliveryDate,
        LocalDate receivedDate,

        PurchaseOrderStatus status,

        String notes,

        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
