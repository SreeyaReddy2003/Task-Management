package com.sprint_project.task_management.serviceImpl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
 
import com.sprint_project.task_management.entity.Project;
import com.sprint_project.task_management.entity.UserRoleAssignment;
import com.sprint_project.task_management.repository.ProjectRepository;
import com.sprint_project.task_management.service.ProjectService;
import com.sprint_project.task_management.service.UserRoleAssignmentService;
 
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
 
@Service
public class ProjectServiceImpl implements ProjectService {
 
    @Autowired
    private ProjectRepository projectRepository;
 
    @Autowired
    private UserRoleAssignmentService userRoleAssignmentService;
 
    @Override
    public ResponseEntity<Project> createProject(Project project) {
        Project savedProject = projectRepository.save(project);
        return ResponseEntity.ok(savedProject);
    }
 
    @Override
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        if (projects.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("code", "GETALLFAILS");
            error.put("message", "Project list is empty");
            return new ResponseEntity(error, HttpStatus.NOT_FOUND);
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
        List<UserRoleAssignment> assignments = userRoleAssignmentService.getAllAssignments();
 
        List<Integer> userIds = assignments.stream()
            .filter(a -> a.getUserRole().getRoleName().equalsIgnoreCase(roleName))
            .map(a -> a.getUser().getUserId())
            .distinct()
            .collect(Collectors.toList());
 
        List<Project> projects = projectRepository.findByUser_UserIdIn(userIds);
 
        if (projects.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("code", "GETALLFAILS");
            error.put("message", "Project list is empty");
            return new ResponseEntity(error, HttpStatus.NOT_FOUND);
        }
 
        return ResponseEntity.ok(projects);
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
