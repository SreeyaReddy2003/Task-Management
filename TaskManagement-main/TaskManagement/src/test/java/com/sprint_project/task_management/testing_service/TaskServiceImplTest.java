package com.sprint_project.task_management.testing_service;

import com.sprint_project.task_management.entity.Task;
import com.sprint_project.task_management.repository.TaskRepository;
import com.sprint_project.task_management.serviceImpl.TaskServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.http.ResponseEntity;

public class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

    private Task sampleTask;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleTask = new Task();
        sampleTask.setTaskId(1);
        sampleTask.setTaskName("Test Task");
        sampleTask.setDescription("Test Description");
        sampleTask.setDueDate(LocalDate.now().plusDays(2));
        sampleTask.setPriority("High");
        sampleTask.setStatus("Pending");
    }

    @Test
    public void testCreateTask() {
        when(taskRepository.save(sampleTask)).thenReturn(sampleTask);
        ResponseEntity<Task> response = taskService.createTask(sampleTask);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test Task", response.getBody().getTaskName());
    }

    @Test
    public void testGetOverdueTasks() {
        when(taskRepository.findByDueDateBefore(any(LocalDate.class))).thenReturn(List.of(sampleTask));
        ResponseEntity<List<Task>> response = taskService.getOverdueTasks();
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void testGetTasksByPriorityAndStatus() {
        when(taskRepository.findByPriorityAndStatus("High", "Pending")).thenReturn(List.of(sampleTask));
        ResponseEntity<List<Task>> response = taskService.getTasksByPriorityAndStatus("High", "Pending");
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetDueSoonTasks() {
        when(taskRepository.findByDueDateBetween(any(), any())).thenReturn(List.of(sampleTask));
        ResponseEntity<List<Task>> response = taskService.getDueSoonTasks();
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetTasksByUserAndStatus() {
        when(taskRepository.findByUserUserIdAndStatus(1, "Pending")).thenReturn(List.of(sampleTask));
        ResponseEntity<List<Task>> response = taskService.getTasksByUserAndStatus(1, "Pending");
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetTasksByCategory() {
        when(taskRepository.findByCategory_CategoryId(1)).thenReturn(List.of(sampleTask));
        ResponseEntity<List<Task>> response = taskService.getTasksByCategory(1);
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testUpdateTask_Found() {
        Task updated = new Task();
        updated.setTaskName("Updated Task");
        updated.setDescription("Updated Description");
        updated.setDueDate(LocalDate.now().plusDays(5));
        updated.setPriority("Medium");
        updated.setStatus("Completed");

        when(taskRepository.findById(1)).thenReturn(Optional.of(sampleTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updated);

        ResponseEntity<Task> response = taskService.updateTask(1, updated);
        assertEquals("Updated Task", response.getBody().getTaskName());
    }

    @Test
    public void testUpdateTask_NotFound() {
        when(taskRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            taskService.updateTask(1, sampleTask);
        });
        assertEquals("Task not found", exception.getMessage());
    }

    @Test
    public void testGetTaskById_Found() {
        when(taskRepository.findById(1)).thenReturn(Optional.of(sampleTask));
        ResponseEntity<Task> response = taskService.getTaskById(1);
        assertEquals("Test Task", response.getBody().getTaskName());
    }

    @Test
    public void testGetTaskById_NotFound() {
        when(taskRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            taskService.getTaskById(1);
        });
        assertEquals("Task not found", exception.getMessage());
    }

    @Test
    public void testDeleteTask_Found() {
        when(taskRepository.findById(1)).thenReturn(Optional.of(sampleTask));
        doNothing().when(taskRepository).delete(sampleTask);
        assertDoesNotThrow(() -> taskService.deleteTask(1));
    }

    @Test
    public void testDeleteTask_NotFound() {
        when(taskRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            taskService.deleteTask(1);
        });
        assertEquals("Task not found", exception.getMessage());
    }
}