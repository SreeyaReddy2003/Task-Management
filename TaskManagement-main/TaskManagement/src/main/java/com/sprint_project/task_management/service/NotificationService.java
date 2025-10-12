package com.sprint_project.task_management.service;

import java.util.List;

import com.sprint_project.task_management.entity.Notification;

public interface NotificationService {
    Notification addNotification(Notification notification);
    List<Notification> getAllNotifications();
    Notification getNotificationById(Integer id);
    Notification updateNotification(Integer id, Notification notification);
    void deleteNotification(Integer id);
}