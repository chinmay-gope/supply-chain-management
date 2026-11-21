package com.supplychainmanagement.controller;


import com.supplychainmanagement.dto.response.DashboardResponse;
import com.supplychainmanagement.entity.Product;
import com.supplychainmanagement.service.AnalyticsService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard() {

        return ResponseEntity.ok(
                analyticsService.getDashboardAnalytics());
    }

    @GetMapping("/inventory-value")
    public ResponseEntity<BigDecimal> getInventoryValue() {

        return ResponseEntity.ok(
                analyticsService.getInventoryValue());
    }

    @GetMapping("/top-selling-products")
    public ResponseEntity<List<Product>> getTopSellingProducts() {

        return ResponseEntity.ok(
                analyticsService.getTopSellingProducts());
    }
}
