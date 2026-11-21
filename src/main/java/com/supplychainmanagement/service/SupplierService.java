package com.supplychainmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.supplychainmanagement.entity.Supplier;
import com.supplychainmanagement.repository.SupplierRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public Supplier createSupplier(Supplier supplier) {

        if (supplier.getEmail() != null
                && supplierRepository.existsByEmailIgnoreCase(supplier.getEmail())) {
            throw new IllegalArgumentException("Supplier email already exists");
        }

        return supplierRepository.save(supplier);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));
    }

    public List<Supplier> searchSuppliers(String name) {
        return supplierRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Supplier> getActiveSuppliers() {
        return supplierRepository.findByActiveTrue();
    }

    public Supplier updateSupplier(Long id, Supplier updatedSupplier) {

        Supplier supplier = getSupplierById(id);

        if (updatedSupplier.getEmail() != null
                && !updatedSupplier.getEmail().equalsIgnoreCase(supplier.getEmail())
                && supplierRepository.existsByEmailIgnoreCase(updatedSupplier.getEmail())) {
            throw new IllegalArgumentException("Supplier email already exists");
        }

        supplier.setName(updatedSupplier.getName());
        supplier.setEmail(updatedSupplier.getEmail());
        supplier.setPhone(updatedSupplier.getPhone());
        supplier.setAddress(updatedSupplier.getAddress());
        supplier.setActive(updatedSupplier.getActive());

        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(Long id) {
        Supplier supplier = getSupplierById(id);
        supplier.setActive(false);
        supplierRepository.save(supplier);
    }
}