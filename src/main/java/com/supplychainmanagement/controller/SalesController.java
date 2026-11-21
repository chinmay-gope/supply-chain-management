package com.supplychainmanagement.controller;

import com.supplychainmanagement.dto.request.SaleRequest;
import com.supplychainmanagement.dto.response.SaleResponse;
import com.supplychainmanagement.service.SalesService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @PostMapping
    public ResponseEntity<SaleResponse> create(
            @Valid @RequestBody SaleRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(salesService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<SaleResponse>> getAll() {
        return ResponseEntity.ok(salesService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(salesService.getById(id));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<SaleResponse> cancel(
            @PathVariable Long id) {

        return ResponseEntity.ok(salesService.cancel(id));
    }
}
