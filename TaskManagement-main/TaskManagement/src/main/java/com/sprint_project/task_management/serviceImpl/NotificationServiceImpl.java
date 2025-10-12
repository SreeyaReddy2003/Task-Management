package com.sprint_project.task_management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint_project.task_management.entity.Notification;
import com.sprint_project.task_management.repository.NotificationRepository;
import com.sprint_project.task_management.service.NotificationService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification addNotification(Notification notification) {
        notification.setTimestamp(LocalDateTime.now());
        notification.setRead(false);
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification getNotificationById(Integer id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification doesn't exist"));
    }

    @Override
    public Notification updateNotification(Integer id, Notification notification) {
        Notification existing = getNotificationById(id);
        existing.setMessage(notification.getMessage());
        existing.setType(notification.getType());
        existing.setTaskId(notification.getTaskId());
        existing.setRead(notification.isRead());
        return notificationRepository.save(existing);
    }

    @Override
    public void deleteNotification(Integer id) {
        Notification notification = getNotificationById(id);
        notificationRepository.delete(notification);
    }
}