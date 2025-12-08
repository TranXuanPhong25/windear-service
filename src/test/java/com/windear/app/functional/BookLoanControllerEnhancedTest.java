package com.windear.app.functional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.windear.app.controller.BookLoanController;
import com.windear.app.dto.SubscribeRequest;
import com.windear.app.entity.BookCopy;
import com.windear.app.entity.BookLoan;
import com.windear.app.entity.InternalBook;
import com.windear.app.enums.Status;
import com.windear.app.primarykey.BookLoanId;
import com.windear.app.repository.BookLoanRepository;
import com.windear.app.service.BookCopyService;
import com.windear.app.service.BookLoanService;
import com.windear.app.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Enhanced functional tests for BookLoanController
 * Focus: Testing business logic including notification service interactions
 * 
 * These tests verify that controller properly coordinates between services:
 * - BookLoanService for core loan operations
 * - NotificationService for user notifications
 * - Cascade actions (e.g., deleting subscribe requests on book return)
 */
@WebMvcTest(BookLoanController.class)
@DisplayName("Use case 3,4,5: Bookloan Tests")
class BookLoanControllerEnhancedTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookLoanService bookLoanService;

    @MockBean
    private BookLoanRepository bookLoanRepository;

    @MockBean
    private NotificationService notificationService;
    
    @MockBean
    private BookCopyService bookCopyService;

    private BookLoanId bookLoanId;
    private BookLoan bookLoan;

    @BeforeEach
    void setUp() {
        bookLoanId = new BookLoanId();
        bookLoanId.setUserId("auth0|user123");
        bookLoanId.setBookId(3);
        bookLoanId.setRequestDate(System.currentTimeMillis());

        bookLoan = new BookLoan();
        bookLoan.setBookLoanId(bookLoanId);
        bookLoan.setTitle("Test Book");
        bookLoan.setAuthorName("Test Author");
        bookLoan.setStatus(Status.PENDING);
    }


    @Test
    @DisplayName("TC3.1: Success flow - Verify notification sent on borrow request")
    void testSendBorrowRequest_VerifyNotification() throws Exception {
        BookCopy bookCopy = new BookCopy();
        bookCopy.setBookId(3);
        bookCopy.setQuantity(12);
        when(bookCopyService.getQuantityOfBookCopy(3))
                .thenReturn(12);
        when(bookLoanService.sendBorrowRequest(any()))
                .thenReturn(bookLoan);
        when(bookLoanRepository.findByUserIdAndBookID("auth0|user123", 3))
                .thenReturn(java.util.Collections.emptyList());
        mockMvc.perform(post("/api/bookloan/borrow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookLoan)))
                .andExpect(status().isOk());

        verify(bookLoanService).sendBorrowRequest(any());
        
        
        verify(notificationService).sendNotification(
                eq("auth0|user123"),
                contains("Test Book")
        );
    }
    @Test
    @DisplayName("TC3.2: Failed flow on borrow request")
    void testSendBorrowRequest_Fail() throws Exception {
        // Tranh chấp: Tại bước 4, nếu yêu cầu được gửi nhưng không còn bản thì thông báo lôi
        // Lúc làm chưa tính đến trường hợp này
        BookCopy bookCopy = new BookCopy();
        bookCopy.setBookId(3);
        bookCopy.setQuantity(0);
        when(bookCopyService.getQuantityOfBookCopy(3))
                .thenReturn(0);
        when(bookLoanService.sendBorrowRequest(bookLoan))
                .thenReturn(bookLoan);

        mockMvc.perform(post("/api/bookloan/borrow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookLoan)))
                .andExpect(status().is4xxClientError());
    }
    
    @Test
    @DisplayName("TC3.3: Subscribe when book unavailable")
    void testSubscribeToBook_Success() throws Exception {
        SubscribeRequest subscribeRequest = new SubscribeRequest();
        subscribeRequest.setUserId("auth0|user123");
        subscribeRequest.setBookId(1);
        
        BookLoan subscribeRecord = new BookLoan();
        subscribeRecord.setBookLoanId(bookLoanId);
        subscribeRecord.setStatus(Status.SUBSCRIBE);

        
        when(bookLoanService.subscribeToBook(any(SubscribeRequest.class)))
                .thenReturn(subscribeRecord);

        mockMvc.perform(post("/api/bookloan/subscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subscribeRequest)))
                .andExpect(status().isOk());

        verify(bookLoanService).subscribeToBook(any(SubscribeRequest.class));
    }

    @Test
    @DisplayName("TC4.1: Verify notification sent when admin accepts")
    void testAcceptBorrowRequest_VerifyNotification() throws Exception {
        bookLoan.setStatus(Status.ACCEPT);
        BookCopy bookCopy = new BookCopy();
        bookCopy.setBookId(3);
        bookCopy.setQuantity(2);
        when(bookLoanService.acceptBorrowRequest(any(BookLoanId.class)))
                .thenReturn(bookLoan);

        mockMvc.perform(put("/api/bookloan/borrow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookLoanId)))
                .andExpect(status().isOk());

        verify(bookLoanService).acceptBorrowRequest(any(BookLoanId.class));
        verify(notificationService).sendNotification(
                eq("auth0|user123"),
                contains("has been accepted")
        );
    }

    @Test
    @DisplayName("TC4.2: Verify notification sent when admin declines")
    void testDeclineBorrowRequest_VerifyNotification() throws Exception {
        bookLoan.setStatus(Status.DECLINE);
        when(bookLoanService.declineBorrowRequest(any(BookLoanId.class)))
                .thenReturn(bookLoan);

        mockMvc.perform(post("/api/bookloan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookLoanId)))
                .andExpect(status().isOk());

        verify(bookLoanService).declineBorrowRequest(any(BookLoanId.class));
        
        verify(notificationService).sendNotification(
                eq("auth0|user123"),
                contains("has been declined")
        );
        verify(notificationService).sendNotification(
                anyString(),
                contains("Test Book")
        );
    }

  
    @Test
    @DisplayName("TC5: Verify subscribers are notified and deleted subcribers")
    void testReturnBook_VerifySubscriberNotification() throws Exception {
        when(bookLoanService.returnBook(any(BookLoanId.class)))
                .thenReturn(bookLoan);

        mockMvc.perform(put("/api/bookloan/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookLoanId)))
                .andExpect(status().isOk());

        InOrder inOrder = inOrder(notificationService, bookLoanService);
        inOrder.verify(bookLoanService).returnBook(any(BookLoanId.class));
        inOrder.verify(notificationService).sendNotificationForSubscribeRequest(3);
        inOrder.verify(bookLoanService).deleteSubscribeRequestOfBook(eq(3));
    }
}
