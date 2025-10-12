package com.sprint_project.task_management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint_project.task_management.entity.UserRoleAssignment;
import com.sprint_project.task_management.repository.UserRoleAssignmentRepository;
import com.sprint_project.task_management.service.UserRoleAssignmentService;

import java.util.List;

@Service
public class UserRoleAssignmentServiceImpl implements UserRoleAssignmentService {

    @Autowired
    private UserRoleAssignmentRepository assignmentRepository;

    @Override
    public UserRoleAssignment assignRole(UserRoleAssignment assignment) {
        return assignmentRepository.save(assignment);
    }

    @Override
    public List<UserRoleAssignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    @Override
    public List<UserRoleAssignment> getRolesByUserId(Integer userId) {
        return assignmentRepository.findByUserUserId(userId);
    }

    @Override
    public void revokeRole(Integer userRoleId, Integer userId) {
        List<UserRoleAssignment> assignments = assignmentRepository.findByUserUserId(userId);
        for (UserRoleAssignment assignment : assignments) {
            if (assignment.getUserRole().getUserRoleId().equals(userRoleId)) {
                assignmentRepository.delete(assignment);
                return;
            }
        }
        throw new RuntimeException("UserRole doesn't exist");
    }
}