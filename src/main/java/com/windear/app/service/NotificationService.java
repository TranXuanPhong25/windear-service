package com.windear.app.service;

import com.windear.app.entity.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getAllNotifications();
    List<Notification> getAllNotificationsOfUser(String userId);
    void sendNotification(String userId, String title);
}
