package com.sprint_project.task_management.testing_service;


import com.sprint_project.task_management.entity.Project;
import com.sprint_project.task_management.repository.ProjectRepository;
import com.sprint_project.task_management.serviceImpl.ProjectServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.http.ResponseEntity;

public class ProjectServiceImplTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    private Project sampleProject;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleProject = new Project();
        sampleProject.setProjectId(1);
        sampleProject.setName("Test Project");
        sampleProject.setStartDate(LocalDate.of(2025, 1, 1));
        sampleProject.setEndDate(LocalDate.of(2025, 12, 31));
        sampleProject.setStatus("Ongoing");
    }

    @Test
    public void testCreateProject() {
        when(projectRepository.save(sampleProject)).thenReturn(sampleProject);
        ResponseEntity<Project> response = projectService.createProject(sampleProject);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test Project", response.getBody().getName());
    }

    @Test
    public void testGetAllProjects() {
        when(projectRepository.findAll()).thenReturn(List.of(sampleProject));
        ResponseEntity<List<Project>> response = projectService.getAllProjects();
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetOngoingProjects() {
        when(projectRepository.findByEndDateAfter(any(LocalDate.class))).thenReturn(List.of(sampleProject));
        ResponseEntity<List<Project>> response = projectService.getOngoingProjects();
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void testGetProjectsByDateRange() {
        when(projectRepository.findByStartDateGreaterThanEqualAndEndDateLessThanEqual(any(), any()))
                .thenReturn(List.of(sampleProject));
        ResponseEntity<List<Project>> response = projectService.getProjectsByDateRange(
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31));
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetProjectsByUserRole_Valid() {
        when(projectRepository.findByUser_UserId(1)).thenReturn(List.of(sampleProject));
        ResponseEntity<List<Project>> response = projectService.getProjectsByUserRole("1");
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetProjectsByUserRole_Invalid() {
        ResponseEntity<List<Project>> response = projectService.getProjectsByUserRole("invalid");
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testGetProjectsByStatus() {
        when(projectRepository.findByStatus("Ongoing")).thenReturn(List.of(sampleProject));
        ResponseEntity<List<Project>> response = projectService.getProjectsByStatus("Ongoing");
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetProjectsWithHighPriorityTasks() {
        when(projectRepository.findByTasks_Priority("High")).thenReturn(List.of(sampleProject));
        ResponseEntity<List<Project>> response = projectService.getProjectsWithHighPriorityTasks();
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testUpdateProject_Found() {
        Project updated = new Project();
        updated.setName("Updated Project");
        updated.setStartDate(LocalDate.of(2025, 2, 1));
        updated.setEndDate(LocalDate.of(2025, 11, 30));
        updated.setStatus("Completed");

        when(projectRepository.findById(1)).thenReturn(Optional.of(sampleProject));
        when(projectRepository.save(any(Project.class))).thenReturn(updated);

        ResponseEntity<Project> response = projectService.updateProject(1, updated);
        assertEquals("Updated Project", response.getBody().getName());
    }

    @Test
    public void testUpdateProject_NotFound() {
        when(projectRepository.findById(1)).thenReturn(Optional.empty());
        ResponseEntity<Project> response = projectService.updateProject(1, sampleProject);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testDeleteProject_Found() {
        when(projectRepository.existsById(1)).thenReturn(true);
        doNothing().when(projectRepository).deleteById(1);
        ResponseEntity<String> response = projectService.deleteProject(1);
        assertEquals("Project deleted successfully.", response.getBody());
    }

    @Test
    public void testDeleteProject_NotFound() {
        when(projectRepository.existsById(1)).thenReturn(false);
        ResponseEntity<String> response = projectService.deleteProject(1);
        assertEquals(404, response.getStatusCodeValue());
    }
}
