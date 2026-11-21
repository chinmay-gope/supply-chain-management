package com.supplychainmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.supplychainmanagement.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsBySku(String sku);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategory_Id(Long categoryId);

    List<Product> findByActiveTrue();

    @Query("SELECT p FROM Product p WHERE p.currentStock <= p.reorderLevel")
    List<Product> findLowStockProducts();
}
