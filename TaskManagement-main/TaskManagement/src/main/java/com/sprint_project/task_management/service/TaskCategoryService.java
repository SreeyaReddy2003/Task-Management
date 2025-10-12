package com.sprint_project.task_management.service;

import java.util.List;

import com.sprint_project.task_management.entity.TaskCategory;

public interface TaskCategoryService {
    TaskCategory addTaskCategory(TaskCategory taskCategory);
    List<TaskCategory> getCategoriesByTaskId(Integer taskId);
    List<TaskCategory> getTasksByCategoryId(Integer categoryId);
}