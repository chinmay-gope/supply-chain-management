package com.supplychainmanagement.dto.response;


import java.math.BigDecimal;

public record InventoryReportResponse(

        Long totalProducts,
        Long totalStockUnits,
        Long lowStockProducts,
        Long outOfStockProducts,
        BigDecimal totalInventoryValue
) {}
