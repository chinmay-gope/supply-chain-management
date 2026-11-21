
package com.supplychainmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.supplychainmanagement.dto.request.StockRequest;
import com.supplychainmanagement.dto.response.ProductResponse;
import com.supplychainmanagement.dto.response.StockTransactionResponse;
import com.supplychainmanagement.mapper.EntityMapper;
import com.supplychainmanagement.service.StockService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping("/in")
    @ResponseStatus(HttpStatus.CREATED)
    public StockTransactionResponse stockIn(
            @Valid @RequestBody StockRequest request) {

        return EntityMapper.toStockTransactionResponse(
                stockService.stockIn(
                        request.getProductId(),
                        request.getQuantity(),
                        request.getUnitPrice(),
                        null,
                        request.getNotes()));
    }

    @PostMapping("/out")
    @ResponseStatus(HttpStatus.CREATED)
    public StockTransactionResponse stockOut(
            @Valid @RequestBody StockRequest request) {

        return EntityMapper.toStockTransactionResponse(
                stockService.stockOut(
                        request.getProductId(),
                        request.getQuantity(),
                        request.getUnitPrice(),
                        null,
                        request.getNotes()));
    }

    @GetMapping("/transactions")
    public List<StockTransactionResponse> getAllTransactions() {

        return stockService.getAllTransactions()
                .stream()
                .map(EntityMapper::toStockTransactionResponse)
                .toList();
    }

    @GetMapping("/transactions/product/{productId}")
    public List<StockTransactionResponse> getProductTransactions(
            @PathVariable Long productId) {

        return stockService.getProductTransactions(productId)
                .stream()
                .map(EntityMapper::toStockTransactionResponse)
                .toList();
    }

    @GetMapping("/low-stock")
    public List<ProductResponse> getLowStockProducts() {

        return stockService.getLowStockProducts()
                .stream()
                .map(EntityMapper::toProductResponse)
                .toList();
    }
}
