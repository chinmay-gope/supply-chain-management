package com.supplychainmanagement.repository;


import com.supplychainmanagement.entity.PurchaseRecommendation;
import com.supplychainmanagement.entity.RecommendationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRecommendationRepository
        extends JpaRepository<PurchaseRecommendation, Long> {

    // Find recommendations for a product
    List<PurchaseRecommendation> findByProductId(Long productId);

    // Find recommendations for a supplier
    List<PurchaseRecommendation> findBySupplierId(Long supplierId);

    // Find recommendations by status
    List<PurchaseRecommendation> findByStatus(
            RecommendationStatus status
    );

    // Find pending recommendations
    List<PurchaseRecommendation>
    findByStatusOrderByGeneratedAtDesc(
            RecommendationStatus status
    );

    // Find all recommendations for a product and status
    List<PurchaseRecommendation> findByProductIdAndStatus(
            Long productId,
            RecommendationStatus status
    );
}
