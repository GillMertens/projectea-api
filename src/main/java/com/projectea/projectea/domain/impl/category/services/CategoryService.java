package com.projectea.projectea.domain.impl.category.services;

import com.projectea.projectea.domain.impl.category.entities.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category createCategory(Category category);
    void deleteCategory(Long id);
}
