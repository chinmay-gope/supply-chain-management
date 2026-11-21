package com.supplychainmanagement.service;


import com.supplychainmanagement.dto.response.OptimizationResponse;
import com.supplychainmanagement.entity.DemandForecast;
import com.supplychainmanagement.entity.Product;
import com.supplychainmanagement.repository.DemandForecastRepository;
import com.supplychainmanagement.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OptimizationService {

    private final ProductRepository productRepository;
    private final DemandForecastRepository forecastRepository;

    public OptimizationService(
            ProductRepository productRepository,
            DemandForecastRepository forecastRepository) {
        this.productRepository = productRepository;
        this.forecastRepository = forecastRepository;
    }

    public List<OptimizationResponse> optimizeAll() {

        return productRepository.findAll()
                .stream()
                .map(p -> optimizeProduct(p.getId()))
                .toList();
    }

    public OptimizationResponse optimizeProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        int currentStock = product.getCurrentStock();
        int safetyStock = product.getReorderLevel();

        BigDecimal predictedDemand = forecastRepository
                .findByProductIdOrderByForecastDateAsc(productId)
                .stream()
                .limit(7)
                .map(DemandForecast::getPredictedDemand)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int forecastDemand = predictedDemand.intValue();

        int reorderPoint = safetyStock + forecastDemand;
        int optimalStock = reorderPoint * 2;
        int recommendedQuantity =
                Math.max(0, optimalStock - currentStock);

        String reason = recommendedQuantity > 0
                ? "Stock is below the calculated optimal inventory level"
                : "Current inventory is sufficient";

        return new OptimizationResponse(
                product.getId(),
                product.getName(),
                currentStock,
                safetyStock,
                reorderPoint,
                optimalStock,
                recommendedQuantity,
                reason);
    }
}
