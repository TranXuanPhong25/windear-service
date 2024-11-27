package com.windear.app.controller;

import com.windear.app.entity.Notification;
import com.windear.app.service.NotificationService;
import com.windear.app.service.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationServiceImpl notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping()
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/unread/{userId}")
    public int countUnreadNotificationOfUser(@PathVariable String userId) {
        return notificationService.countUnreadNotificationOfUser(userId);
    }

    @GetMapping("/{userId}")
    public List<Notification> getAllNotificationsOfUser(@PathVariable String userId) {
        return notificationService.getAllNotificationsOfUser(userId);
    }
}
