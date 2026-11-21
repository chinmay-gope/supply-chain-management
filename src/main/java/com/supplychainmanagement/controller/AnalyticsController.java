package com.supplychainmanagement.controller;


import com.supplychainmanagement.dto.response.DashboardResponse;
import com.supplychainmanagement.entity.Product;
import com.supplychainmanagement.service.AnalyticsService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'INVENTORY_MANAGER', 'PURCHASE_MANAGER', 'SALES_MANAGER')")
    public ResponseEntity<DashboardResponse> getDashboard() {

        return ResponseEntity.ok(
                analyticsService.getDashboardAnalytics());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'INVENTORY_MANAGER', 'PURCHASE_MANAGER', 'SALES_MANAGER')")
    @GetMapping("/inventory-value")
    public ResponseEntity<BigDecimal> getInventoryValue() {

        return ResponseEntity.ok(
                analyticsService.getInventoryValue());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'INVENTORY_MANAGER', 'PURCHASE_MANAGER', 'SALES_MANAGER')")
    @GetMapping("/top-selling-products")
    public ResponseEntity<List<Product>> getTopSellingProducts() {

        return ResponseEntity.ok(
                analyticsService.getTopSellingProducts());
    }
}
