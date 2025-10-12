package com.sprint_project.task_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sprint_project.task_management.entity.TaskCategory;
import com.sprint_project.task_management.service.TaskCategoryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/taskcategories")
public class TaskCategoryController {

    @Autowired
    private TaskCategoryService taskCategoryService;

    @PostMapping("/post")
    public ResponseEntity<?> addTaskCategory(@RequestBody TaskCategory taskCategory) {
        TaskCategory saved = taskCategoryService.addTaskCategory(taskCategory);
        return ResponseEntity.ok(Map.of("code", "POSTSUCCESS", "message", "Task-category added successfully", "data", saved));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getCategoriesByTask(@PathVariable Integer taskId) {
        List<TaskCategory> list = taskCategoryService.getCategoriesByTaskId(taskId);
        if (list.isEmpty()) {
            throw new RuntimeException("No category found for a particular task");
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getTasksByCategory(@PathVariable Integer categoryId) {
        List<TaskCategory> list = taskCategoryService.getTasksByCategoryId(categoryId);
        if (list.isEmpty()) {
            throw new RuntimeException("No task found for a particular category");
        }
        return ResponseEntity.ok(list);
    }
}
