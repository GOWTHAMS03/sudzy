package com.sudzey.sudzey.service.Impl;

import com.sudzey.sudzey.dto.CategoryDTO;
import com.sudzey.sudzey.model.Category;
import com.sudzey.sudzey.repository.CategoryRepository;
import com.sudzey.sudzey.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(String categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    @Override
    public Category updateCategory(String categoryId, Category category) {
        Category existingCategory = getCategoryById(categoryId);
        if (existingCategory != null) {
            existingCategory.setName(category.getName());
            existingCategory.setDescription(category.getDescription());
            return categoryRepository.save(existingCategory);
        }
        return null;
    }

    @Override
    public void deleteCategory(String categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
