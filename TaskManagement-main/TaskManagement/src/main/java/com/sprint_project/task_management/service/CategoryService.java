package com.sprint_project.task_management.service;

import java.util.List;
import java.util.Map;

import com.sprint_project.task_management.entity.Category;

public interface CategoryService {

    Category addCategory(Category category);

    List<Category> getAllCategories();

    Category getCategoryById(Integer id);

    Category updateCategory(Integer id, Category category);

    void deleteCategory(Integer id);

    Map<String, Long> getCategoryTaskCounts();
}