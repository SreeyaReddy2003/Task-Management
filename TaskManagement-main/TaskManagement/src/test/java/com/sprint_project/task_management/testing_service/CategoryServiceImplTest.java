package com.sprint_project.task_management.testing_service;

import com.sprint_project.task_management.entity.Category;
import com.sprint_project.task_management.repository.CategoryRepository;
import com.sprint_project.task_management.repository.TaskRepository;
import com.sprint_project.task_management.serviceImpl.CategoryServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private TaskRepository taskRepository;

    private Category sampleCategory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleCategory = new Category();
        sampleCategory.setCategoryId(1);
        sampleCategory.setName("Development");
    }

    @Test
    public void testAddCategory_Success() {
        when(categoryRepository.findByName("Development")).thenReturn(Optional.empty());
        when(categoryRepository.save(sampleCategory)).thenReturn(sampleCategory);

        Category result = categoryService.addCategory(sampleCategory);
        assertNotNull(result);
        assertEquals("Development", result.getName());
    }

    @Test
    public void testAddCategory_AlreadyExists() {
        when(categoryRepository.findByName("Development")).thenReturn(Optional.of(sampleCategory));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.addCategory(sampleCategory);
        });

        assertEquals("Category already exists", exception.getMessage());
    }

    @Test
    public void testGetAllCategories() {
        when(categoryRepository.findAll()).thenReturn(List.of(sampleCategory));
        List<Category> result = categoryService.getAllCategories();
        assertEquals(1, result.size());
    }

    @Test
    public void testGetCategoryById_Found() {
        when(categoryRepository.findById(1)).thenReturn(Optional.of(sampleCategory));
        Category result = categoryService.getCategoryById(1);
        assertNotNull(result);
    }

    @Test
    public void testGetCategoryById_NotFound() {
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.getCategoryById(1);
        });

        assertEquals("Category doesn't exist", exception.getMessage());
    }

    @Test
    public void testUpdateCategory() {
        Category updated = new Category();
        updated.setName("Testing");

        when(categoryRepository.findById(1)).thenReturn(Optional.of(sampleCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(updated);

        Category result = categoryService.updateCategory(1, updated);
        assertEquals("Testing", result.getName());
    }

    @Test
    public void testDeleteCategory() {
        when(categoryRepository.findById(1)).thenReturn(Optional.of(sampleCategory));
        doNothing().when(categoryRepository).delete(sampleCategory);

        assertDoesNotThrow(() -> categoryService.deleteCategory(1));
    }

    @Test
    public void testGetCategoryTaskCounts() {
        when(categoryRepository.findAll()).thenReturn(List.of(sampleCategory));
        when(taskRepository.countByCategory_CategoryId(1)).thenReturn(5L);

        Map<String, Long> result = categoryService.getCategoryTaskCounts();
        assertEquals(1, result.size());
        assertEquals(5L, result.get("Development"));
    }
}

