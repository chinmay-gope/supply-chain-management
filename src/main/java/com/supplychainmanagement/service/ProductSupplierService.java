package com.supplychainmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.supplychainmanagement.entity.Product;
import com.supplychainmanagement.entity.ProductSupplier;
import com.supplychainmanagement.entity.Supplier;
import com.supplychainmanagement.repository.ProductRepository;
import com.supplychainmanagement.repository.ProductSupplierRepository;
import com.supplychainmanagement.repository.SupplierRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductSupplierService {

    private final ProductSupplierRepository productSupplierRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    public ProductSupplier assignSupplier(Long productId, Long supplierId,
            ProductSupplier details) {

        if (productSupplierRepository.existsByProduct_IdAndSupplier_Id(productId, supplierId)) {
            throw new IllegalArgumentException("Supplier already assigned to this product");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        details.setProduct(product);
        details.setSupplier(supplier);

        return productSupplierRepository.save(details);
    }

    public List<ProductSupplier> getSuppliersByProduct(Long productId) {
        return productSupplierRepository.findByProduct_Id(productId);
    }

    public List<ProductSupplier> getProductsBySupplier(Long supplierId) {
        return productSupplierRepository.findBySupplier_Id(supplierId);
    }

    public void removeSupplierMapping(Long id) {

        ProductSupplier mapping = productSupplierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mapping not found"));

        productSupplierRepository.delete(mapping);
    }
}