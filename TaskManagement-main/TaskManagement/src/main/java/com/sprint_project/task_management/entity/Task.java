package com.sprint_project.task_management.entity;
 
import jakarta.persistence.*;
import java.time.LocalDate;
 
@Entity
public class Task {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskId;
 
    private String taskName;
 
    private String description;
 
    private LocalDate dueDate;
 
    private String priority; // e.g., High, Medium, Low
 
    private String status; // e.g., Pending, Completed
 
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
 
   
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
 
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category; // Optional: if you have a Category entity
 
    // Getters and Setters
 
    public Integer getTaskId() {
        return taskId;
    }
 
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
 
    public String getTaskName() {
        return taskName;
    }
 
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
 
    public String getDescription() {
        return description;
    }
 
    public void setDescription(String description) {
        this.description = description;
    }
 
    public LocalDate getDueDate() {
        return dueDate;
    }
 
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
 
    public String getPriority() {
        return priority;
    }
 
    public void setPriority(String priority) {
        this.priority = priority;
    }
 
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
 
    public Project getProject() {
        return project;
    }
 
    public void setProject(Project project) {
        this.project = project;
    }
 
    public User getUser() {
        return user;
    }
 
    public void setUser(User user) {
        this.user = user;
    }
 
    public Category getCategory() {
        return category;
    }
 
    public void setCategory(Category category) {
        this.category = category;
    }
}