package com.sprint_project.task_management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint_project.task_management.entity.Category;
import com.sprint_project.task_management.repository.CategoryRepository;
import com.sprint_project.task_management.repository.TaskRepository;
import com.sprint_project.task_management.service.CategoryService;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Category addCategory(Category category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new RuntimeException("Category already exists");
        }
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category doesn't exist"));
    }

    @Override
    public Category updateCategory(Integer id, Category category) {
        Category existing = getCategoryById(id);
        existing.setName(category.getName());
        return categoryRepository.save(existing);
    }

    @Override
    public void deleteCategory(Integer id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }

    @Override
    public Map<String, Long> getCategoryTaskCounts() {
        List<Category> categories = categoryRepository.findAll();
        Map<String, Long> result = new HashMap<>();
        for (Category category : categories) {
            Long count = taskRepository.countByCategory_CategoryId(category.getCategoryId());
            result.put(category.getName(), count);
        }
        return result;
    }
}