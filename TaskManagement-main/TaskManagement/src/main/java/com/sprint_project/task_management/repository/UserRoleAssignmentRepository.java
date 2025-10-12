package com.sprint_project.task_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint_project.task_management.entity.UserRoleAssignment;

import java.util.List;

@Repository
public interface UserRoleAssignmentRepository extends JpaRepository<UserRoleAssignment, Integer> {
    List<UserRoleAssignment> findByUserUserId(Integer userId);
}
