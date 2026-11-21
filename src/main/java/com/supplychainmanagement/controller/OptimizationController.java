package com.supplychainmanagement.controller;


import com.supplychainmanagement.dto.response.OptimizationResponse;
import com.supplychainmanagement.service.OptimizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ai/optimization")
public class OptimizationController {

    private final OptimizationService optimizationService;

    public OptimizationController(
            OptimizationService optimizationService) {
        this.optimizationService = optimizationService;
    }

    @GetMapping
    public ResponseEntity<List<OptimizationResponse>> optimizeAll() {

        return ResponseEntity.ok(
                optimizationService.optimizeAll());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<OptimizationResponse> optimizeProduct(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                optimizationService.optimizeProduct(productId));
    }
}
