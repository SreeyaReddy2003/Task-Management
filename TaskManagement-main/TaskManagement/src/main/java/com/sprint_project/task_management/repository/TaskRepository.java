package com.sprint_project.task_management.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sprint_project.task_management.entity.Task;
import com.sprint_project.task_management.entity.User;

import java.time.LocalDate;
import java.util.List;
 
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
 
    List<Task> findByUserUserId(Integer userId);
    List<Task> findByUserUserIdAndStatus(Integer userId, String status);
    List<Task> findByStatus(String status);
    List<Task> findByPriority(String priority);
    List<Task> findByPriorityAndStatus(String priority, String status);
    List<Task> findByDueDateBefore(LocalDate date);
    Long countByCategory_CategoryId(Integer categoryId);
    List<Task> findByDueDateBetween(LocalDate start, LocalDate end);
    List<Task> findByCategory_CategoryId(Integer categoryId); // ✅ Correct
    @Query("SELECT t.user.userId, COUNT(t) as taskCount FROM Task t GROUP BY t.user.userId ORDER BY taskCount DESC")
    List<Object[]> findUsersWithTaskCounts();
    @Query("SELECT DISTINCT t.user FROM Task t WHERE t.status = 'Completed'")
    List<User> findUsersWithCompletedTasks();
}