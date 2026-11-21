package com.supplychainmanagement.controller;


import com.supplychainmanagement.dto.response.PurchaseRecommendationResponse;
import com.supplychainmanagement.service.PurchaseRecommendationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai/purchase-recommendations")
public class PurchaseRecommendationController {

    private final PurchaseRecommendationService recommendationService;

    public PurchaseRecommendationController(
            PurchaseRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping("/generate")
    public ResponseEntity<List<PurchaseRecommendationResponse>>
    generateRecommendations() {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(recommendationService.generateRecommendations());
    }

    @GetMapping
    public ResponseEntity<List<PurchaseRecommendationResponse>>
    getAllRecommendations() {

        return ResponseEntity.ok(
                recommendationService.getAllRecommendations());
    }

    @PatchMapping("/{id}/accept")
    public ResponseEntity<PurchaseRecommendationResponse> accept(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                recommendationService.accept(id));
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<PurchaseRecommendationResponse> reject(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                recommendationService.reject(id));
    }
}
