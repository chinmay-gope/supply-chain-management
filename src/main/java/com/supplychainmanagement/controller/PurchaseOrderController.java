package com.supplychainmanagement.controller;

import com.supplychainmanagement.dto.request.PurchaseOrderRequest;
import com.supplychainmanagement.dto.response.PurchaseOrderResponse;
import com.supplychainmanagement.service.PurchaseOrderService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderResponse> create(
            @Valid @RequestBody PurchaseOrderRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchaseOrderService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrderResponse>> getAll() {
        return ResponseEntity.ok(purchaseOrderService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(purchaseOrderService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody PurchaseOrderRequest request) {

        return ResponseEntity.ok(
                purchaseOrderService.update(id, request));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<PurchaseOrderResponse> approve(
            @PathVariable Long id) {

        return ResponseEntity.ok(purchaseOrderService.approve(id));
    }

    @PatchMapping("/{id}/receive")
    public ResponseEntity<PurchaseOrderResponse> receive(
            @PathVariable Long id) {

        return ResponseEntity.ok(purchaseOrderService.receive(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        purchaseOrderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
