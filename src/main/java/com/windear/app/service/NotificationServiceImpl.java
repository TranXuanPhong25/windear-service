package com.windear.app.service;

import com.okta.commons.lang.Collections;
import com.windear.app.entity.BookLoan;
import com.windear.app.entity.Notification;
import com.windear.app.enums.Status;
import com.windear.app.exception.NotificationNotFoundException;
import com.windear.app.repository.NotificationRepository;
import kotlin.OptIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final BookLoanService bookLoanService;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   BookLoanService bookLoanService) {
        this.notificationRepository = notificationRepository;
        this.bookLoanService = bookLoanService;
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public List<Notification> getAllNotificationsOfUser(String userId) {
        return notificationRepository.findAllByUserId(userId);
    }

    @Override
    public Notification getNotificationById(Integer notificationId) {
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        if (notification.isPresent()) {
            return notification.get();
        } else {
            throw new NotificationNotFoundException("Notification with id: " + notificationId + " not found.");
        }
    }

    @Override
    public int countUnreadNotificationOfUser(String userId) {
        List<Notification> notifications = getAllNotificationsOfUser(userId);
        return notifications.stream().filter(notification -> !notification.isRead()).toList().size();
    }

    @Override
    public void sendNotification(String userId, String title) {
        notificationRepository.save(new Notification(userId, title, new Timestamp(System.currentTimeMillis()), false));
    }

    @Scheduled(cron = "0 0 12 * * ?")
    @Override
    public void sendReturnReminder() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<BookLoan> bookLoans = bookLoanService.findAllActiveBookLoan();
        for (BookLoan bookLoan : bookLoans) {
            LocalDate dueDate = bookLoan.getBorrowDate().plusDays(bookLoan.getBorrowTime());
            if (dueDate.equals(tomorrow)) {
                sendNotification(bookLoan.getBookLoanId().getUserId(), "Reminder: Your book with title: " + bookLoan.getTitle() + " is due tomorrow.");
            }
        }
    }

    @Scheduled(cron = "0 0 12 * * ?")
    @Override
    public void rejectBookLoanRequest() {
        List<BookLoan> bookLoans = bookLoanService.findAllBorrowRequest();
        for (BookLoan bookLoan : bookLoans) {
            LocalDate requestDate = bookLoan.getBookLoanId().getRequestDate();
            if (requestDate != null && LocalDate.now().minusDays(3).equals(requestDate)) {
                String userId = bookLoan.getBookLoanId().getUserId();
                bookLoan.setStatus(Status.DECLINE);
                sendNotification(userId, "Your book loan request for the book titled '" + bookLoan.getTitle() + "' has been rejected.");
            }
        }
    }

    @Override
    public void sendNotificationForSubscribeRequest(Integer bookId) {
        List<BookLoan> bookLoans = bookLoanService.getSubscribeRequestOfBook(bookId);
        for (BookLoan bookLoan : bookLoans) {
            String userId = bookLoan.getBookLoanId().getUserId();
            sendNotification(userId, "The book with title '" + bookLoan.getTitle() + "' is now available for borrowing.");
        }
    }

    @Override
    public void deleteNotification(Integer notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    @Override
    public Notification markNotificationAsRead(Integer notificationId) {
        Notification notification = getNotificationById(notificationId);
        notification.setRead(true);
        return notification;
    }

    @Override
    public Notification markNotificationAsNotRead(Integer notificationId) {
        Notification notification = getNotificationById(notificationId);
        notification.setRead(false);
        return notification;
    }
}
