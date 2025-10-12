package com.sprint_project.task_management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sprint_project.task_management.entity.User;
import com.sprint_project.task_management.repository.TaskRepository;
import com.sprint_project.task_management.repository.UserRepository;
import com.sprint_project.task_management.security.JwtUtil;
import com.sprint_project.task_management.service.UserService;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public ResponseEntity<User> createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new RuntimeException("User list is empty");
        }
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<User> getUserById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<List<User>> getUsersByEmailDomain(String domain) {
        List<User> users = userRepository.findAll().stream()
                .filter(u -> u.getEmail().endsWith("@" + domain))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<List<User>> searchUsersByName(String name) {
        List<User> users = userRepository.findByFullNameContainingIgnoreCase(name);
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<User> getUsersWithMostTasks() {
        List<Object[]> results = taskRepository.findUsersWithTaskCounts();
        if (!results.isEmpty()) {
            Integer userId = (Integer) results.get(0)[0];
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return ResponseEntity.ok(user);
        }
        throw new RuntimeException("No users with tasks found");
    }

//    @Override
//    public ResponseEntity<User> authenticateUser(String username, String password) {
//        User user = userRepository.findByUsernameAndPassword(username, password)
//                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
//        return ResponseEntity.ok(user);
//    }
//    
    
    
    
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<Map<String, Object>> authenticateUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        String token = jwtUtil.generateToken(user.getUsername());

        return ResponseEntity.ok(Map.of(
            "code", "AUTH_SUCCESS",
            "message", "Authentication successful",
            "token", token,
            "user", user
        ));
    }
    
    
    
    

    @Override
    public ResponseEntity<List<User>> getUsersWithCompletedTasks() {
        List<User> users = taskRepository.findUsersWithCompletedTasks();
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<User> updateUser(Integer userId, User updatedUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFullName(updatedUser.getFullName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<User> deleteUser(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
        return ResponseEntity.ok().build();
    }
}