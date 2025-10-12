package com.sprint_project.task_management.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sprint_project.task_management.entity.User;
import java.util.Map;
import java.util.List;
import java.util.HashMap;

@Service
public interface UserService {
    ResponseEntity<User> createUser(User user);
    ResponseEntity<List<User>> getAllUsers();
    ResponseEntity<User> getUserById(Integer userId);
    ResponseEntity<List<User>> getUsersByEmailDomain(String domain);
    ResponseEntity<List<User>> searchUsersByName(String name);
    ResponseEntity<User> getUsersWithMostTasks();
    
    
    ResponseEntity<Map<String, Object>> authenticateUser(String username, String password);
    ResponseEntity<List<User>> getUsersWithCompletedTasks();
    ResponseEntity<User> updateUser(Integer userId, User user);
    ResponseEntity<User> deleteUser(Integer userId);
}