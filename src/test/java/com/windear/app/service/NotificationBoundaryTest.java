package com.windear.app.service;

import com.windear.app.entity.Notification;
import com.windear.app.exception.NotificationNotFoundException;
import com.windear.app.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationBoundaryTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    void sendNotification_ShouldReject_InvalidInputs() {
        when(notificationRepository.save(any(Notification.class))).thenAnswer(inv -> inv.getArgument(0));

        assertThrows(IllegalArgumentException.class, () -> {
            notificationService.sendNotification(null, "Title");
        }, "UserId must not be null");

        assertThrows(IllegalArgumentException.class, () -> {
            notificationService.sendNotification("", "Title");
        }, "UserId must not be empty");

        assertThrows(IllegalArgumentException.class, () -> {
            notificationService.sendNotification("user1", null);
        }, "Title must not be null");

        assertThrows(IllegalArgumentException.class, () -> {
            notificationService.sendNotification("user1", "");
        }, "Title must not be empty");
    }

    @Test
    void getNotificationsOfUser_ShouldReject_WhenInvalidUserId() {
        assertThrows(IllegalArgumentException.class, () -> {
            notificationService.getAllNotificationsOfUser(null);
        }, "UserId must not be null");

        assertThrows(IllegalArgumentException.class, () -> {
            notificationService.getAllNotificationsOfUser("");
        }, "UserId must not be empty");

        assertThrows(IllegalArgumentException.class, () -> {
            notificationService.countUnreadNotificationOfUser(null);
        }, "UserId must not be null");

        assertThrows(IllegalArgumentException.class, () -> {
            notificationService.countUnreadNotificationOfUser("");
        }, "UserId must not be empty");
    }

    @Test
    void modifyNotification_ShouldReject_WhenNotificationIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            notificationService.markNotificationAsRead(null);
        }, "NotificationId must not be null");
        assertThrows(IllegalArgumentException.class, () -> {
            notificationService.markNotificationAsNotRead(null);
        }, "NotificationId must not be null");
        assertThrows(IllegalArgumentException.class, () -> {
            notificationService.deleteNotification(null);
        }, "NotificationId must not be null");
    }

    @Test
    void getNotificationById_ShouldThrowNotFound_WhenIdIsNegative() {
        when(notificationRepository.findById(-1)).thenReturn(Optional.empty());

        assertThrows(NotificationNotFoundException.class, () -> {
            notificationService.getNotificationById(-1);
        }, "Should reject negative ID");
        assertThrows(NotificationNotFoundException.class, () -> {
            notificationService.markNotificationAsRead(-1);
        }, "Should reject negative ID");
        assertThrows(NotificationNotFoundException.class, () -> {
            notificationService.markNotificationAsNotRead(-1);
        }, "Should reject negative ID");
    }

}
