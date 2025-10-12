package com.sprint_project.task_management.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sprint_project.task_management.entity.Task;
import com.sprint_project.task_management.service.TaskService;

import java.util.List;
 
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
 
    @Autowired
    private TaskService taskService;
 
    // 1. Create a new task
    @PostMapping("/post")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }
 
    // 2. Get overdue tasks
    @GetMapping("/overdue")
    public ResponseEntity<List<Task>> getOverdueTasks() {
        return taskService.getOverdueTasks();
    }
 
    // 3. Get tasks by priority and status
    @GetMapping("/priority/{priority}/status/{status}")
    public ResponseEntity<List<Task>> getTasksByPriorityAndStatus(@PathVariable String priority, @PathVariable String status) {
        return taskService.getTasksByPriorityAndStatus(priority, status);
    }
 
    // 4. Get tasks due soon (e.g., within next 3 days)
    @GetMapping("/due-soon")
    public ResponseEntity<List<Task>> getDueSoonTasks() {
        return taskService.getDueSoonTasks();
    }
 
    // 5. Get tasks by user and status
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<Task>> getTasksByUserAndStatus(@PathVariable Integer userId, @PathVariable String status) {
        return taskService.getTasksByUserAndStatus(userId, status);
    }
 
    // 6. Get tasks by category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Task>> getTasksByCategory(@PathVariable Integer categoryId) {
        return taskService.getTasksByCategory(categoryId);
    }
 
    // 7. Update a task
    @PutMapping("/update/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer taskId, @RequestBody Task task) {
        return taskService.updateTask(taskId, task);
    }
 
    // 8. Get task by ID
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Integer taskId) {
        return taskService.getTaskById(taskId);
    }
 
    // 9. Delete a task by ID
    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Integer taskId) {
        try {
            taskService.deleteTask(taskId);
            return ResponseEntity.ok().body(
                java.util.Map.of("code", "DELETESUCCESS", "message", "Task deleted successfully")
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                java.util.Map.of("code", "DLTFAILS", "message", e.getMessage())
            );
        }
    }
}
 
 