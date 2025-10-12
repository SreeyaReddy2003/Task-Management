package com.sprint_project.task_management.testing_service;


import com.sprint_project.task_management.entity.TaskCategory;
import com.sprint_project.task_management.repository.TaskCategoryRepository;
import com.sprint_project.task_management.serviceImpl.TaskCategoryServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskCategoryServiceImplTest {

    @InjectMocks
    private TaskCategoryServiceImpl taskCategoryService;

    @Mock
    private TaskCategoryRepository taskCategoryRepository;

    private TaskCategory sampleTaskCategory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleTaskCategory = new TaskCategory();
        sampleTaskCategory.setId(1);
    }

    @Test
    public void testAddTaskCategory() {
        when(taskCategoryRepository.save(sampleTaskCategory)).thenReturn(sampleTaskCategory);
        TaskCategory result = taskCategoryService.addTaskCategory(sampleTaskCategory);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testGetCategoriesByTaskId() {
        when(taskCategoryRepository.findByTask_TaskId(1)).thenReturn(List.of(sampleTaskCategory));
        List<TaskCategory> result = taskCategoryService.getCategoriesByTaskId(1);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetTasksByCategoryId() {
        when(taskCategoryRepository.findByCategory_CategoryId(1)).thenReturn(List.of(sampleTaskCategory));
        List<TaskCategory> result = taskCategoryService.getTasksByCategoryId(1);
        assertEquals(1, result.size());
    }
}
