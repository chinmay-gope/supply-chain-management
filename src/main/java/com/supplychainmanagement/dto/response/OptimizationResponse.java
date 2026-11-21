package com.supplychainmanagement.dto.response;


public record OptimizationResponse(

        Long productId,
        String productName,

        Integer currentStock,
        Integer safetyStock,
        Integer reorderPoint,
        Integer optimalStockLevel,
        Integer recommendedOrderQuantity,

        String recommendationReason

) {
}
