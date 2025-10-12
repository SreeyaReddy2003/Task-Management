package com.sprint_project.task_management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint_project.task_management.entity.UserRole;
import com.sprint_project.task_management.repository.UserRoleRepository;
import com.sprint_project.task_management.service.UserRoleService;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserRole addUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public List<UserRole> getAllRoles() {
        return userRoleRepository.findAll();
    }

    @Override
    public UserRole getRoleById(Integer id) {
        return userRoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserRole doesn't exist"));
    }

    @Override
    public UserRole updateRole(Integer id, UserRole userRole) {
        UserRole existing = getRoleById(id);
        existing.setRoleName(userRole.getRoleName());
        return userRoleRepository.save(existing);
    }

    @Override
    public void deleteRole(Integer id) {
        UserRole role = getRoleById(id);
        userRoleRepository.delete(role);
    }
}
