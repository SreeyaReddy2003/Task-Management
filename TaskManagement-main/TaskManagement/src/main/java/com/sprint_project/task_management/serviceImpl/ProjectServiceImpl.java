package com.sprint_project.task_management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sprint_project.task_management.entity.Project;
import com.sprint_project.task_management.repository.ProjectRepository;
import com.sprint_project.task_management.service.ProjectService;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public ResponseEntity<Project> createProject(Project project) {
        Project savedProject = projectRepository.save(project);
        return ResponseEntity.ok(savedProject);
    }

    @Override
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        if (projects.isEmpty()) {
            throw new RuntimeException("Project list is empty");
        }
        return ResponseEntity.ok(projects);
    }

    @Override
    public ResponseEntity<List<Project>> getOngoingProjects() {
        LocalDate today = LocalDate.now();
        List<Project> ongoingProjects = projectRepository.findByEndDateAfter(today);
        return ResponseEntity.ok(ongoingProjects);
    }

    @Override
    public ResponseEntity<List<Project>> getProjectsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Project> projects = projectRepository.findByStartDateGreaterThanEqualAndEndDateLessThanEqual(startDate, endDate);
        return ResponseEntity.ok(projects);
    }

    @Override
    public ResponseEntity<List<Project>> getProjectsByUserRole(String roleName) {
        try {
            Integer userId = Integer.parseInt(roleName); // Replace with actual logic if needed
            List<Project> projects = projectRepository.findByUser_UserId(userId);
            return ResponseEntity.ok(projects);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid role identifier: " + roleName);
        }
    }

    @Override
    public ResponseEntity<List<Project>> getProjectsByStatus(String status) {
        List<Project> projects = projectRepository.findByStatus(status);
        return ResponseEntity.ok(projects);
    }

    @Override
    public ResponseEntity<List<Project>> getProjectsWithHighPriorityTasks() {
        List<Project> projects = projectRepository.findByTasks_Priority("High");
        return ResponseEntity.ok(projects);
    }

    @Override
    public ResponseEntity<Project> updateProject(Integer projectId, Project updatedProject) {
        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        existingProject.setName(updatedProject.getName());
        existingProject.setStartDate(updatedProject.getStartDate());
        existingProject.setEndDate(updatedProject.getEndDate());
        existingProject.setStatus(updatedProject.getStatus());
        existingProject.setUser(updatedProject.getUser());
        existingProject.setTasks(updatedProject.getTasks());
        projectRepository.save(existingProject);
        return ResponseEntity.ok(existingProject);
    }

    @Override
    public ResponseEntity<String> deleteProject(Integer projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new RuntimeException("Project not found");
        }
        projectRepository.deleteById(projectId);
        return ResponseEntity.ok("Project deleted successfully.");
    }
}