package com.sprint_project.task_management.testing_service;

import com.sprint_project.task_management.entity.Notification;
import com.sprint_project.task_management.repository.NotificationRepository;
import com.sprint_project.task_management.serviceImpl.NotificationServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificationServiceImplTest {

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    private Notification sampleNotification;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleNotification = new Notification();
        sampleNotification.setNotificationId(1);
        sampleNotification.setMessage("Test Notification");
        sampleNotification.setType("INFO");
        sampleNotification.setTaskId(101);
        sampleNotification.setRead(false);
        sampleNotification.setTimestamp(LocalDateTime.now());
    }

    @Test
    public void testAddNotification() {
        when(notificationRepository.save(any(Notification.class))).thenReturn(sampleNotification);
        Notification result = notificationService.addNotification(sampleNotification);
        assertNotNull(result);
        assertEquals("Test Notification", result.getMessage());
        assertFalse(result.isRead());
    }

    @Test
    public void testGetAllNotifications() {
        when(notificationRepository.findAll()).thenReturn(List.of(sampleNotification));
        List<Notification> result = notificationService.getAllNotifications();
        assertEquals(1, result.size());
    }

    @Test
    public void testGetNotificationById_Found() {
        when(notificationRepository.findById(1)).thenReturn(Optional.of(sampleNotification));
        Notification result = notificationService.getNotificationById(1);
        assertNotNull(result);
        assertEquals(1, result.getNotificationId());
    }

    @Test
    public void testGetNotificationById_NotFound() {
        when(notificationRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            notificationService.getNotificationById(1);
        });
        assertEquals("Notification doesn't exist", exception.getMessage());
    }

    @Test
    public void testUpdateNotification() {
        Notification updated = new Notification();
        updated.setMessage("Updated Message");
        updated.setType("ALERT");
        updated.setTaskId(202);
        updated.setRead(true);

        when(notificationRepository.findById(1)).thenReturn(Optional.of(sampleNotification));
        when(notificationRepository.save(any(Notification.class))).thenReturn(updated);

        Notification result = notificationService.updateNotification(1, updated);
        assertEquals("Updated Message", result.getMessage());
        assertTrue(result.isRead());
    }

    @Test
    public void testDeleteNotification() {
        when(notificationRepository.findById(1)).thenReturn(Optional.of(sampleNotification));
        doNothing().when(notificationRepository).delete(sampleNotification);
        assertDoesNotThrow(() -> notificationService.deleteNotification(1));
    }
}

