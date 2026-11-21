package com.supplychainmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.supplychainmanagement.entity.Category;
import com.supplychainmanagement.entity.Product;
import com.supplychainmanagement.repository.CategoryRepository;
import com.supplychainmanagement.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Product createProduct(Product product) {

        if (productRepository.existsBySku(product.getSku())) {
            throw new IllegalArgumentException("SKU already exists");
        }

        Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        product.setCategory(category);
        product.setCurrentStock(0);

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategory_Id(categoryId);
    }

    public List<Product> getActiveProducts() {
        return productRepository.findByActiveTrue();
    }

    public Product updateProduct(Long id, Product updatedProduct) {

        Product product = getProductById(id);

        if (!product.getSku().equals(updatedProduct.getSku())
                && productRepository.existsBySku(updatedProduct.getSku())) {
            throw new IllegalArgumentException("SKU already exists");
        }

        Category category = categoryRepository.findById(updatedProduct.getCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        product.setName(updatedProduct.getName());
        product.setSku(updatedProduct.getSku());
        product.setDescription(updatedProduct.getDescription());
        product.setCategory(category);
        product.setUnitPrice(updatedProduct.getUnitPrice());
        product.setCostPrice(updatedProduct.getCostPrice());
        product.setReorderLevel(updatedProduct.getReorderLevel());
        product.setUnit(updatedProduct.getUnit());
        product.setActive(updatedProduct.getActive());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        product.setActive(false);
        productRepository.save(product);
    }

    public List<Product> getLowStockProducts() {
        return productRepository.findLowStockProducts();
    }
}