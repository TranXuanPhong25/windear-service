package com.windear.app.service;

import com.windear.app.entity.BookLoan;
import com.windear.app.entity.Notification;
import com.windear.app.enums.Status;
import com.windear.app.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

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
}
