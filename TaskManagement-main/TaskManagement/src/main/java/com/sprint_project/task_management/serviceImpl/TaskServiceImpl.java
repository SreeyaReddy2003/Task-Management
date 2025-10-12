package com.sprint_project.task_management.serviceImpl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sprint_project.task_management.entity.Task;
import com.sprint_project.task_management.repository.TaskRepository;
import com.sprint_project.task_management.service.TaskService;

import java.time.LocalDate;
import java.util.List;
 
@Service
public class TaskServiceImpl implements TaskService {
 
    @Autowired
    private TaskRepository taskRepository;
 
    @Override
    public ResponseEntity<Task> createTask(Task task) {
        Task saved = taskRepository.save(task);
        return ResponseEntity.ok(saved);
    }
 
    @Override
    public ResponseEntity<List<Task>> getOverdueTasks() {
        List<Task> tasks = taskRepository.findByDueDateBefore(LocalDate.now());
        return ResponseEntity.ok(tasks);
    }
 
    @Override
    public ResponseEntity<List<Task>> getTasksByPriorityAndStatus(String priority, String status) {
        List<Task> tasks = taskRepository.findByPriorityAndStatus(priority, status);
        return ResponseEntity.ok(tasks);
    }
 
    @Override
    public ResponseEntity<List<Task>> getDueSoonTasks() {
        LocalDate today = LocalDate.now();
        LocalDate threeDaysLater = today.plusDays(3);
        List<Task> tasks = taskRepository.findByDueDateBetween(today, threeDaysLater);
        return ResponseEntity.ok(tasks);
    }
 
    @Override
    public ResponseEntity<List<Task>> getTasksByUserAndStatus(Integer userId, String status) {
        List<Task> tasks = taskRepository.findByUserUserIdAndStatus(userId, status);
        return ResponseEntity.ok(tasks);
    }
 
    @Override
    public ResponseEntity<List<Task>> getTasksByCategory(Integer categoryId) {
        List<Task> tasks = taskRepository.findByCategory_CategoryId(categoryId);
        return ResponseEntity.ok(tasks);
    }
 
    @Override
    public ResponseEntity<Task> updateTask(Integer taskId, Task task) {
        Task existing = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        existing.setTaskName(task.getTaskName());
        existing.setDescription(task.getDescription());
        existing.setDueDate(task.getDueDate());
        existing.setPriority(task.getPriority());
        existing.setStatus(task.getStatus());
        existing.setProject(task.getProject());
        existing.setUser(task.getUser());
        existing.setCategory(task.getCategory());
        Task updated = taskRepository.save(existing);
        return ResponseEntity.ok(updated);
    }
 
    @Override
    public ResponseEntity<Task> getTaskById(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return ResponseEntity.ok(task);
    }
 
    @Override
    public ResponseEntity<Task> deleteTask(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(task);
        return null;
    }
}