package com.supplychainmanagement.controller;

import com.supplychainmanagement.dto.request.SaleRequest;
import com.supplychainmanagement.dto.response.SaleResponse;
import com.supplychainmanagement.service.SalesService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PURCHASE_MANAGER', 'STAFF')")
    @PostMapping
    public ResponseEntity<SaleResponse> create(
            @Valid @RequestBody SaleRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(salesService.create(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PURCHASE_MANAGER', 'STAFF')")
    @GetMapping
    public ResponseEntity<List<SaleResponse>> getAll() {
        return ResponseEntity.ok(salesService.getAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PURCHASE_MANAGER', 'STAFF')")
    @GetMapping("/{id}")
    public ResponseEntity<SaleResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(salesService.getById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PURCHASE_MANAGER', 'STAFF')")
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<SaleResponse> cancel(
            @PathVariable Long id) {

        return ResponseEntity.ok(salesService.cancel(id));
    }
}
