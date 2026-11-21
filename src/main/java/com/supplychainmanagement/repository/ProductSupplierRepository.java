package com.supplychainmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supplychainmanagement.entity.ProductSupplier;

public interface ProductSupplierRepository
        extends JpaRepository<ProductSupplier, Long> {

    List<ProductSupplier> findByProduct_Id(Long productId);

    List<ProductSupplier> findBySupplier_Id(Long supplierId);

    Optional<ProductSupplier> findByProduct_IdAndSupplier_Id(
            Long productId, Long supplierId);

    boolean existsByProduct_IdAndSupplier_Id(
            Long productId, Long supplierId);
}