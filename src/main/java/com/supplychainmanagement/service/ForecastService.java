package com.supplychainmanagement.service;



import com.supplychainmanagement.dto.response.ForecastResponse;
import com.supplychainmanagement.entity.DemandForecast;
import com.supplychainmanagement.entity.Product;
import com.supplychainmanagement.entity.SaleItem;
import com.supplychainmanagement.entity.SaleStatus;
import com.supplychainmanagement.repository.DemandForecastRepository;
import com.supplychainmanagement.repository.ProductRepository;
import com.supplychainmanagement.repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ForecastService {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final DemandForecastRepository forecastRepository;

    public ForecastService(
            ProductRepository productRepository,
            SaleRepository saleRepository,
            DemandForecastRepository forecastRepository) {
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
        this.forecastRepository = forecastRepository;
    }

    public List<ForecastResponse> forecast(Long productId, int days) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (days <= 0 || days > 365) {
            throw new IllegalArgumentException(
                    "Forecast days must be between 1 and 365");
        }

        // Basic baseline: average daily quantity sold over last 30 days.
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(30);

        int totalSold = saleRepository
                .findBySaleDateBetween(
                        start.atStartOfDay(),
                        end.plusDays(1).atStartOfDay())
                .stream()
                .filter(s -> s.getStatus() == SaleStatus.COMPLETED)
                .flatMap(s -> s.getItems().stream())
                .filter(i -> i.getProduct().getId().equals(productId))
                .mapToInt(SaleItem::getQuantity)
                .sum();

        BigDecimal average = BigDecimal.valueOf(totalSold)
                .divide(BigDecimal.valueOf(30), 2,
                        java.math.RoundingMode.HALF_UP);

        List<ForecastResponse> results = new ArrayList<>();

        for (int i = 1; i <= days; i++) {

            LocalDate forecastDate = end.plusDays(i);

            DemandForecast forecast = new DemandForecast();
            forecast.setProduct(product);
            forecast.setForecastDate(forecastDate);
            forecast.setPredictedDemand(average);
            forecast.setConfidenceScore(
                    totalSold == 0
                            ? BigDecimal.ZERO
                            : new BigDecimal("0.60"));
            forecast.setModelVersion("MOVING_AVERAGE_V1");
            forecast.setGeneratedAt(
                    java.time.LocalDateTime.now());

            DemandForecast saved =
                    forecastRepository.save(forecast);

            results.add(new ForecastResponse(
                    product.getId(),
                    product.getName(),
                    saved.getForecastDate(),
                    saved.getPredictedDemand(),
                    saved.getConfidenceScore(),
                    saved.getModelVersion()));
        }

        return results;
    }

    public List<ForecastResponse> getForecast(Long productId) {

        return forecastRepository
                .findByProductIdOrderByForecastDateAsc(productId)
                .stream()
                .map(f -> new ForecastResponse(
                        f.getProduct().getId(),
                        f.getProduct().getName(),
                        f.getForecastDate(),
                        f.getPredictedDemand(),
                        f.getConfidenceScore(),
                        f.getModelVersion()))
                .toList();
    }
}
