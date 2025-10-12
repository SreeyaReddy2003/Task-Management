package com.sprint_project.task_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sprint_project.task_management.entity.Notification;
import com.sprint_project.task_management.service.NotificationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/post")
    public ResponseEntity<?> addNotification(@RequestBody Notification notification) {
        Notification saved = notificationService.addNotification(notification);
        return ResponseEntity.ok(Map.of(
            "code", "POSTSUCCESS",
            "message", "Notification added successfully",
            "data", saved
        ));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        if (notifications.isEmpty()) {
            throw new RuntimeException("Notification list is empty");
        }
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNotificationById(@PathVariable Integer id) {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNotification(@PathVariable Integer id, @RequestBody Notification notification) {
        Notification updated = notificationService.updateNotification(id, notification);
        return ResponseEntity.ok(Map.of(
            "code", "UPDATESUCCESS",
            "message", "Notification updated successfully",
            "data", updated
        ));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Integer id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok(Map.of(
            "code", "DELETESUCCESS",
            "message", "Notification deleted successfully"
        ));
    }
}