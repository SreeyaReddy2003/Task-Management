package com.sprint_project.task_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sprint_project.task_management.entity.Project;
import com.sprint_project.task_management.service.ProjectService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/post")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/ongoing")
    public ResponseEntity<List<Project>> getOngoingProjects() {
        return projectService.getOngoingProjects();
    }

    @GetMapping("/daterange/{startDate}/{endDate}")
    public ResponseEntity<List<Project>> getProjectsByDateRange(
            @PathVariable String startDate,
            @PathVariable String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return projectService.getProjectsByDateRange(start, end);
    }

    @GetMapping("/userrole/{roleName}")
    public ResponseEntity<List<Project>> getProjectsByUserRole(@PathVariable String roleName) {
        return projectService.getProjectsByUserRole(roleName);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Project>> getProjectsByStatus(@PathVariable String status) {
        return projectService.getProjectsByStatus(status);
    }

    @GetMapping("/high-prioritytasks")
    public ResponseEntity<List<Project>> getProjectsWithHighPriorityTasks() {
        return projectService.getProjectsWithHighPriorityTasks();
    }

    @PutMapping("/update/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable Integer projectId, @RequestBody Project project) {
        return projectService.updateProject(projectId, project);
    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable Integer projectId) {
        return projectService.deleteProject(projectId);
    }
}