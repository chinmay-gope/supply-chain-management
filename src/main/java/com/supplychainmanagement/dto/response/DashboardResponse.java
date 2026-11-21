package com.supplychainmanagement.dto.response;


import java.math.BigDecimal;

public record DashboardResponse(

        Long totalProducts,
        Long totalSuppliers,
        Long totalPurchaseOrders,
        Long totalSales,

        Long lowStockProducts,
        Long outOfStockProducts,

        BigDecimal totalRevenue,
        BigDecimal inventoryValue

) {
}
