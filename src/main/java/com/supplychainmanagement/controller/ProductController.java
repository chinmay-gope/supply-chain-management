
package com.supplychainmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.supplychainmanagement.dto.request.ProductRequest;
import com.supplychainmanagement.dto.response.ProductResponse;
import com.supplychainmanagement.entity.Category;
import com.supplychainmanagement.entity.Product;
import com.supplychainmanagement.mapper.EntityMapper;
import com.supplychainmanagement.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(
            @Valid @RequestBody ProductRequest request) {

        Product product = mapRequest(request);

        return EntityMapper.toProductResponse(
                productService.createProduct(product));
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts()
                .stream()
                .map(EntityMapper::toProductResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return EntityMapper.toProductResponse(
                productService.getProductById(id));
    }

    @GetMapping("/search")
    public List<ProductResponse> searchProducts(
            @RequestParam String name) {

        return productService.searchProducts(name)
                .stream()
                .map(EntityMapper::toProductResponse)
                .toList();
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductResponse> getProductsByCategory(
            @PathVariable Long categoryId) {

        return productService.getProductsByCategory(categoryId)
                .stream()
                .map(EntityMapper::toProductResponse)
                .toList();
    }

    @GetMapping("/active")
    public List<ProductResponse> getActiveProducts() {
        return productService.getActiveProducts()
                .stream()
                .map(EntityMapper::toProductResponse)
                .toList();
    }

    @GetMapping("/low-stock")
    public List<ProductResponse> getLowStockProducts() {
        return productService.getLowStockProducts()
                .stream()
                .map(EntityMapper::toProductResponse)
                .toList();
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        Product product = mapRequest(request);

        return EntityMapper.toProductResponse(
                productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    private Product mapRequest(ProductRequest request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setDescription(request.getDescription());

        Category category = new Category();
        category.setId(request.getCategoryId());
        product.setCategory(category);

        product.setUnitPrice(request.getUnitPrice());
        product.setCostPrice(request.getCostPrice());
        product.setReorderLevel(request.getReorderLevel());
        product.setUnit(request.getUnit());

        return product;
    }
}
