package com.sprint_project.task_management.service;

import java.util.List;

import com.sprint_project.task_management.entity.UserRole;

public interface UserRoleService {
    UserRole addUserRole(UserRole userRole);
    List<UserRole> getAllRoles();
    UserRole getRoleById(Integer id);
    UserRole updateRole(Integer id, UserRole userRole);
    void deleteRole(Integer id);
}