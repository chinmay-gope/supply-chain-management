package com.supplychainmanagement.controller;


import com.supplychainmanagement.dto.response.ForecastResponse;
import com.supplychainmanagement.service.ForecastService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai/forecast")
public class ForecastController {

    private final ForecastService forecastService;

    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'INVENTORY_MANAGER', 'PURCHASE_MANAGER')")
    @PostMapping("/{productId}")
    public ResponseEntity<List<ForecastResponse>> generateForecast(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "7") int days) {

        return ResponseEntity.ok(
                forecastService.forecast(productId, days));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'INVENTORY_MANAGER', 'PURCHASE_MANAGER')")
    @GetMapping("/{productId}")
    public ResponseEntity<List<ForecastResponse>> getForecast(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                forecastService.getForecast(productId));
    }
}
