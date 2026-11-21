
package com.supplychainmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.supplychainmanagement.dto.request.ProductSupplierRequest;
import com.supplychainmanagement.dto.response.ProductSupplierResponse;
import com.supplychainmanagement.entity.ProductSupplier;
import com.supplychainmanagement.mapper.EntityMapper;
import com.supplychainmanagement.service.ProductSupplierService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product-suppliers")
@RequiredArgsConstructor
public class ProductSupplierController {

    private final ProductSupplierService productSupplierService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductSupplierResponse assignSupplier(
            @Valid @RequestBody ProductSupplierRequest request) {

        ProductSupplier details = new ProductSupplier();
        details.setSupplierPrice(request.getSupplierPrice());
        details.setLeadTimeDays(request.getLeadTimeDays());
        details.setPreferred(
                request.getPreferred() != null && request.getPreferred());

        return EntityMapper.toProductSupplierResponse(
                productSupplierService.assignSupplier(
                        request.getProductId(),
                        request.getSupplierId(),
                        details));
    }

    @GetMapping("/product/{productId}")
    public List<ProductSupplierResponse> getSuppliersByProduct(
            @PathVariable Long productId) {

        return productSupplierService.getSuppliersByProduct(productId)
                .stream()
                .map(EntityMapper::toProductSupplierResponse)
                .toList();
    }

    @GetMapping("/supplier/{supplierId}")
    public List<ProductSupplierResponse> getProductsBySupplier(
            @PathVariable Long supplierId) {

        return productSupplierService.getProductsBySupplier(supplierId)
                .stream()
                .map(EntityMapper::toProductSupplierResponse)
                .toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeSupplierMapping(@PathVariable Long id) {
        productSupplierService.removeSupplierMapping(id);
    }
}
