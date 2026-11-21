package com.supplychainmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.supplychainmanagement.entity.Category;
import com.supplychainmanagement.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(Category category) {

        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new IllegalArgumentException("Category already exists");
        }

        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    public Category updateCategory(Long id, Category updatedCategory) {

        Category category = getCategoryById(id);

        if (!category.getName().equalsIgnoreCase(updatedCategory.getName())
                && categoryRepository.existsByNameIgnoreCase(updatedCategory.getName())) {
            throw new IllegalArgumentException("Category name already exists");
        }

        category.setName(updatedCategory.getName());
        category.setDescription(updatedCategory.getDescription());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }
}