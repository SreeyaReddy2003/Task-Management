package com.sprint_project.task_management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint_project.task_management.entity.TaskCategory;
import com.sprint_project.task_management.repository.TaskCategoryRepository;
import com.sprint_project.task_management.service.TaskCategoryService;

import java.util.List;

@Service
public class TaskCategoryServiceImpl implements TaskCategoryService {

    @Autowired
    private TaskCategoryRepository taskCategoryRepository;

    @Override
    public TaskCategory addTaskCategory(TaskCategory taskCategory) {
        return taskCategoryRepository.save(taskCategory);
    }

    @Override
    public List<TaskCategory> getCategoriesByTaskId(Integer taskId) {
        return taskCategoryRepository.findByTask_TaskId(taskId);
    }

    @Override
    public List<TaskCategory> getTasksByCategoryId(Integer categoryId) {
        return taskCategoryRepository.findByCategory_CategoryId(categoryId);
    }
}