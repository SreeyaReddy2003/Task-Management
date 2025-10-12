package com.sprint_project.task_management.service;

import java.util.List;

import com.sprint_project.task_management.entity.UserRoleAssignment;

public interface UserRoleAssignmentService {
    UserRoleAssignment assignRole(UserRoleAssignment assignment);
    List<UserRoleAssignment> getAllAssignments();
    List<UserRoleAssignment> getRolesByUserId(Integer userId);
    void revokeRole(Integer userRoleId, Integer userId);
}