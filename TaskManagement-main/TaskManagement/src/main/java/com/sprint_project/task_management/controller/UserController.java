package com.sprint_project.task_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sprint_project.task_management.entity.User;
import com.sprint_project.task_management.service.UserService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/post")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/email-domain/{domain}")
    public ResponseEntity<List<User>> getUsersByEmailDomain(@PathVariable String domain) {
        return userService.getUsersByEmailDomain(domain);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<User>> searchUsersByName(@PathVariable String name) {
        return userService.searchUsersByName(name);
    }

    @GetMapping("/most-tasks")
    public ResponseEntity<User> getUsersWithMostTasks() {
        return userService.getUsersWithMostTasks();
    }

    @GetMapping("/authenticate")
    public ResponseEntity<Map<String, Object>> authenticateUser(@RequestParam String username, @RequestParam String password) {
        return userService.authenticateUser(username, password);
    }

    @GetMapping("/completed-tasks")
    public ResponseEntity<List<User>> getUsersWithCompletedTasks() {
        return userService.getUsersWithCompletedTasks();
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId, @Valid @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable Integer userId) {
        return userService.deleteUser(userId);
    }
}