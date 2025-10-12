package com.sprint_project.task_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint_project.task_management.entity.TaskCategory;

import java.util.List;

@Repository
public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Integer> {
    List<TaskCategory> findByTask_TaskId(Integer taskId);
    List<TaskCategory> findByCategory_CategoryId(Integer categoryId);
}