package com.supplychainmanagement.dto.response;


import java.math.BigDecimal;

public record InventoryReportResponse(

        int totalProducts,
        int totalStockUnits,
        Long lowStockProducts,
        Long outOfStockProducts,
        BigDecimal totalInventoryValue
) {}
