package com.supplychainmanagement.service;


import com.supplychainmanagement.dto.request.SaleItemRequest;
import com.supplychainmanagement.dto.request.SaleRequest;
import com.supplychainmanagement.dto.response.SaleItemResponse;
import com.supplychainmanagement.dto.response.SaleResponse;
import com.supplychainmanagement.entity.*;
import com.supplychainmanagement.repository.ProductRepository;
import com.supplychainmanagement.repository.SaleItemRepository;
import com.supplychainmanagement.repository.SaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SalesService {

    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public SalesService(
            SaleRepository saleRepository,
            SaleItemRepository saleItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository) {
        this.saleRepository = saleRepository;
        this.saleItemRepository = saleItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public SaleResponse create(SaleRequest request) {

        User user = userRepository.findById(request.soldById())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Sale sale = new Sale();
        sale.setSaleNumber("SALE-" + System.currentTimeMillis());
        sale.setSoldBy(user);
        sale.setSaleDate(LocalDateTime.now());
        sale.setStatus(SaleStatus.COMPLETED);

        BigDecimal total = BigDecimal.ZERO;

        for (SaleItemRequest itemRequest : request.items()) {

            Product product = productRepository.findById(
                            itemRequest.productId())
                    .orElseThrow(() ->
                            new RuntimeException("Product not found"));

            // Assumption: Product has getSellingPrice()
            // and getQuantity() methods.
            if (product.getCurrentStock() < itemRequest.quantity()) {
                throw new RuntimeException(
                        "Insufficient stock for " + product.getName());
            }

            BigDecimal price = product.getUnitPrice();

            BigDecimal subtotal = price.multiply(
                    BigDecimal.valueOf(itemRequest.quantity()));

            SaleItem item = new SaleItem();
            item.setSale(sale);
            item.setProduct(product);
            item.setQuantity(itemRequest.quantity());
            item.setUnitPrice(price);
            item.setSubtotal(subtotal);

            sale.getItems().add(item);

            product.setCurrentStock(
                    product.getCurrentStock() - itemRequest.quantity());

            productRepository.save(product);

            total = total.add(subtotal);
        }

        sale.setTotalAmount(total);

        return mapToResponse(saleRepository.save(sale));
    }

    public List<SaleResponse> getAll() {
        return saleRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public SaleResponse getById(Long id) {
        Sale sale = getSale(id);
        return mapToResponse(sale);
    }

    public SaleResponse cancel(Long id) {

        Sale sale = getSale(id);

        if (sale.getStatus() != SaleStatus.COMPLETED) {
            throw new RuntimeException("Sale cannot be cancelled");
        }

        // Restore stock when cancelling a completed sale.
        for (SaleItem item : sale.getItems()) {
            Product product = item.getProduct();
            product.setCurrentStock(
                    product.getCurrentStock() + item.getQuantity());
            productRepository.save(product);
        }

        sale.setStatus(SaleStatus.CANCELLED);

        return mapToResponse(saleRepository.save(sale));
    }

    private Sale getSale(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found"));
    }

    private SaleResponse mapToResponse(Sale sale) {

        List<SaleItemResponse> items = sale.getItems()
                .stream()
                .map(item -> new SaleItemResponse(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubtotal()))
                .toList();

        return new SaleResponse(
                sale.getId(),
                sale.getSaleNumber(),
                sale.getSoldBy().getId(),
                sale.getSoldBy().getFullName(),
                items,
                sale.getTotalAmount(),
                sale.getStatus(),
                sale.getSaleDate());
    }
}
