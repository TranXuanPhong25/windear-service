package com.windear.app.service;

import com.windear.app.entity.Notification;

import java.util.List;

public interface NotificationService {
    /**
     * Retrieves all notifications.
     *
     * @return a list of all Notifications
     */
    List<Notification> getAllNotifications();

    /**
     * Retrieves all notifications of a specific user.
     *
     * @param userId the ID of the user
     * @return a list of Notifications associated with the specified user ID
     */
    List<Notification> getAllNotificationsOfUser(String userId);

    /**
     * Retrieves a notification by its ID.
     *
     * @param notificationId the ID of the notification
     * @return the Notification with the specified ID
     */
    Notification getNotificationById(Integer notificationId);

    /**
     * Counts the number of unread notifications of a specific user.
     *
     * @param userId the ID of the user
     * @return the number of unread notifications associated with the specified user ID
     */
    int countUnreadNotificationOfUser(String userId);

    /**
     * Sends a notification to a specific user.
     *
     * @param userId the ID of the user
     * @param title the title of the notification
     */
    void sendNotification(String userId, String title);

    /**
     * Sends a return reminder notification.
     */
    void sendReturnReminder();

    /**
     * Rejects a book loan request.
     */
    void rejectBookLoanRequest();

    /**
     * Sends a notification for a subscribe request of a specific book.
     *
     * @param bookId the ID of the book
     */
    void sendNotificationForSubscribeRequest(Integer bookId);

    /**
     * Deletes a notification by its ID.
     *
     * @param notificationId the ID of the notification to delete
     */
    void deleteNotification(Integer notificationId);

    /**
     * Marks a notification as read by its ID.
     *
     * @param notificationId the ID of the notification to mark as read
     * @return the updated Notification marked as read
     */
    Notification markNotificationAsRead(Integer notificationId);

    /**
     * Marks a notification as not read by its ID.
     *
     * @param notificationId the ID of the notification to mark as not read
     * @return the updated Notification marked as not read
     */
    Notification markNotificationAsNotRead(Integer notificationId);
}
