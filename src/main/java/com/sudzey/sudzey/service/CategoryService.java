package com.sudzey.sudzey.service;

import com.sudzey.sudzey.dto.CategoryDTO;
import com.sudzey.sudzey.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDTO categoryDTO);
    List<Category> getAllCategories();
    Category getCategoryById(String categoryId);
    Category updateCategory(String categoryId, Category category);
    void deleteCategory(String categoryId);
}
