package com.supplychainmanagement.dto.response;

import com.supplychainmanagement.entity.RecommendationStatus;

import java.time.LocalDateTime;

public record PurchaseRecommendationResponse(

        Long id,

        Long productId,
        String productName,

        Long supplierId,
        String supplierName,

        Integer recommendedQuantity,

        String reason,

        RecommendationStatus status,

        LocalDateTime generatedAt

) {
}
