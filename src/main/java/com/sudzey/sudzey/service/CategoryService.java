package com.sudzey.sudzey.service;

import com.sudzey.sudzey.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    List<Category> getAllCategories();
    Category getCategoryById(String categoryId);
    Category updateCategory(String categoryId, Category category);
    void deleteCategory(String categoryId);
}
