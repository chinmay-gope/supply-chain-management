package com.supplychainmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supplychainmanagement.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    List<Supplier> findByNameContainingIgnoreCase(String name);

    List<Supplier> findByActiveTrue();

    boolean existsByEmailIgnoreCase(String email);
}