package com.sprint_project.task_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sprint_project.task_management.entity.Category;
import com.sprint_project.task_management.service.CategoryService;

import java.util.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/post")
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        Category saved = categoryService.addCategory(category);
        return ResponseEntity.ok(Map.of("code", "POSTSUCCESS", "message", "Category added successfully", "data", saved));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            throw new RuntimeException("Category list is empty");
        }
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        Category updated = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(Map.of("code", "UPDATESUCCESS", "message", "Category updated successfully", "data", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(Map.of("code", "DELETESUCCESS", "message", "Category deleted successfully"));
    }

    @GetMapping("/task-count")
    public ResponseEntity<?> getCategoryTaskCounts() {
        Map<String, Long> counts = categoryService.getCategoryTaskCounts();
        return ResponseEntity.ok(counts);
    }
}