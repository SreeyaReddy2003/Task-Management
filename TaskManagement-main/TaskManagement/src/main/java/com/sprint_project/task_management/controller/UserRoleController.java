package com.sprint_project.task_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sprint_project.task_management.entity.UserRole;
import com.sprint_project.task_management.entity.UserRoleAssignment;
import com.sprint_project.task_management.service.UserRoleAssignmentService;
import com.sprint_project.task_management.service.UserRoleService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/userroles")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserRoleAssignmentService assignmentService;

    @PostMapping("/post")
    public ResponseEntity<?> addUserRole(@RequestBody UserRole userRole) {
        UserRole saved = userRoleService.addUserRole(userRole);
        return ResponseEntity.ok(Map.of("code", "POSTSUCCESS", "message", "UserRoles added successfully", "data", saved));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllRoles() {
        List<UserRole> roles = userRoleService.getAllRoles();
        if (roles.isEmpty()) {
            throw new RuntimeException("UserRoles list is empty");
        }
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Integer id) {
        return ResponseEntity.ok(userRoleService.getRoleById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Integer id, @RequestBody UserRole userRole) {
        UserRole updated = userRoleService.updateRole(id, userRole);
        return ResponseEntity.ok(Map.of("code", "UPDATESUCCESS", "message", "UserRoles updated successfully", "data", updated));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Integer id) {
        userRoleService.deleteRole(id);
        return ResponseEntity.ok(Map.of("code", "DELETESUCCESS", "message", "UserRoles deleted successfully"));
    }

    @PostMapping("/assign/assign")
    public ResponseEntity<?> assignRole(@RequestBody UserRoleAssignment assignment) {
        UserRoleAssignment saved = assignmentService.assignRole(assignment);
        return ResponseEntity.ok(Map.of("code", "POSTSUCCESS", "message", "Userrole added successfully", "data", saved));
    }

    @GetMapping("/assignments")
    public ResponseEntity<?> getAllAssignments() {
        List<UserRoleAssignment> list = assignmentService.getAllAssignments();
        if (list.isEmpty()) {
            throw new RuntimeException("UserRole list is empty");
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getRolesByUser(@PathVariable Integer userId) {
        List<UserRoleAssignment> list = assignmentService.getRolesByUserId(userId);
        if (list.isEmpty()) {
            throw new RuntimeException("UserRole doesn't exist");
        }
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/revoke/{userRoleId}/{userId}")
    public ResponseEntity<?> revokeRole(@PathVariable Integer userRoleId, @PathVariable Integer userId) {
        assignmentService.revokeRole(userRoleId, userId);
        return ResponseEntity.ok(Map.of("code", "DELETESUCCESS", "message", "UserRole deleted successfully"));
    }
}