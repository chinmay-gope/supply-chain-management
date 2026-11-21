package com.supplychainmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supplychainmanagement.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByNameIgnoreCase(String name);
}