package com.supplychainmanagement.service;

import com.supplychainmanagement.dto.response.PurchaseRecommendationResponse;
import com.supplychainmanagement.entity.PurchaseRecommendation;
import com.supplychainmanagement.entity.RecommendationStatus;
import com.supplychainmanagement.repository.ProductRepository;
import com.supplychainmanagement.repository.PurchaseRecommendationRepository;
import com.supplychainmanagement.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PurchaseRecommendationService {

    private final PurchaseRecommendationRepository recommendationRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    public PurchaseRecommendationService(
            PurchaseRecommendationRepository recommendationRepository,
            ProductRepository productRepository,
            SupplierRepository supplierRepository) {
        this.recommendationRepository = recommendationRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
    }

    public List<PurchaseRecommendationResponse> generateRecommendations() {

        return productRepository.findAll().stream()
                .map(product -> {

                    int stock = product.getCurrentStock();
                    int minimum = product.getReorderLevel();

                    if (stock > minimum) {
                        return null;
                    }

                    PurchaseRecommendation recommendation =
                            new PurchaseRecommendation();

                    recommendation.setProduct(product);
                    recommendation.setRecommendedQuantity(
                            Math.max(minimum * 2 - stock, 1));
                    recommendation.setReason(
                            "Current stock has reached the minimum stock level");
                    recommendation.setStatus(
                            RecommendationStatus.PENDING);
                    recommendation.setGeneratedAt(LocalDateTime.now());

                    PurchaseRecommendation saved =
                            recommendationRepository.save(recommendation);

                    return mapToResponse(saved);
                })
                .filter(java.util.Objects::nonNull)
                .toList();
    }

    public List<PurchaseRecommendationResponse> getAllRecommendations() {

        return recommendationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public PurchaseRecommendationResponse accept(Long id) {

        PurchaseRecommendation recommendation = getRecommendation(id);

        if (recommendation.getStatus() != RecommendationStatus.PENDING) {
            throw new RuntimeException(
                    "Only pending recommendations can be accepted");
        }

        recommendation.setStatus(RecommendationStatus.APPROVED);

        return mapToResponse(
                recommendationRepository.save(recommendation));
    }

    public PurchaseRecommendationResponse reject(Long id) {

        PurchaseRecommendation recommendation = getRecommendation(id);

        if (recommendation.getStatus() != RecommendationStatus.PENDING) {
            throw new RuntimeException(
                    "Only pending recommendations can be rejected");
        }

        recommendation.setStatus(RecommendationStatus.REJECTED);

        return mapToResponse(
                recommendationRepository.save(recommendation));
    }

    private PurchaseRecommendation getRecommendation(Long id) {
        return recommendationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Recommendation not found"));
    }

    private PurchaseRecommendationResponse mapToResponse(
            PurchaseRecommendation r) {

        return new PurchaseRecommendationResponse(
                r.getId(),
                r.getProduct().getId(),
                r.getProduct().getName(),
                r.getSupplier() == null ? null : r.getSupplier().getId(),
                r.getSupplier() == null ? null : r.getSupplier().getName(),
                r.getRecommendedQuantity(),
                r.getReason(),
                r.getStatus(),
                r.getGeneratedAt());
    }
}
