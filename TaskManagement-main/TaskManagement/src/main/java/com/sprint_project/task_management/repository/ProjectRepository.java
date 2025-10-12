package com.sprint_project.task_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint_project.task_management.entity.Project;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> findByEndDateAfter(LocalDate currentDate);

    List<Project> findByStartDateGreaterThanEqualAndEndDateLessThanEqual(LocalDate startDate, LocalDate endDate);

    List<Project> findByUser_UserId(Integer userId);

    List<Project> findByStatus(String status); // ✅ Now valid

    List<Project> findByTasks_Priority(String priority); // ✅ Assuming Task has a 'priority' field
    
    
}
