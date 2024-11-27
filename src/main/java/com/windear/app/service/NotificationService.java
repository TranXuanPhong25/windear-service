package com.windear.app.service;

import com.windear.app.entity.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getAllNotifications();
    List<Notification> getAllNotificationsOfUser(String userId);
    Notification getNotificationById(Integer notificationId);
    int countUnreadNotificationOfUser(String userId);
    void sendNotification(String userId, String title);
    void sendReturnReminder();
    void rejectBookLoanRequest();
    void sendNotificationForSubscribeRequest(Integer bookId);
    void deleteNotification(Integer notificationId);
    Notification markNotificationAsRead(Integer notificationId);
    Notification markNotificationAsNotRead(Integer notificationId);
}
