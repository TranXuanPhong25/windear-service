package com.windear.app.controller;

import com.windear.app.entity.Review;
import com.windear.app.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.print.attribute.standard.Media;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    private Review response;
    private Review response2;
    private List<Review> responseList;
    LocalDate date = LocalDate.of(2024, 11, 20);
    @BeforeEach
    void InItData() {
        response = new Review();
        response.setReviewId(1);
        response.setUserId("user123");
        response.setBookId(101);
        response.setContent("Great book!");
        response.setRating(4.5);
        response.setCreateAt(date);
        response.setUserImageUrl("http://example.com/image.jpg");
        response.setUserName("John Doe");

        response2 = new Review();
        response2.setReviewId(2);
        response2.setUserId("user123");
        response2.setBookId(202);
        response2.setContent("Great book!");
        response2.setRating(5);
        response2.setCreateAt(date);
        response2.setUserImageUrl("http://example.com/image.jpg");
        response2.setUserName("Mono");

        responseList = new ArrayList<>();
        responseList.add(response);
        responseList.add(response2);

    }


    @Test
    void createReviewTest() throws Exception {
        Mockito.when(reviewService.save(any(Review.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/review")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                          "userId": "user123",
                                          "bookId": 101,
                                          "content": "Great book!",
                                          "rating": 4.5,
                                          "createAt": "2024-11-20",
                                          "userImageUrl": "http://example.com/image.jpg",
                                          "userName": "John Doe"
                                        }
                                """))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("reviewId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("userId").value("user123"))
                .andExpect(MockMvcResultMatchers.jsonPath("bookId").value(101))
                .andExpect(MockMvcResultMatchers.jsonPath("content").value("Great book!"))
                .andExpect(MockMvcResultMatchers.jsonPath("rating").value(4.5))
                .andExpect(MockMvcResultMatchers.jsonPath("createAt").value("2024-11-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("userImageUrl").value("http://example.com/image.jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("userName").value("John Doe"));
    }

    @Test
    void createReviewTest_ReviewExist() throws Exception {
        Mockito.when(reviewService.save(any(Review.class))).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/review")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getReviewTest() throws Exception {
        Mockito.when(reviewService.findReviewById(anyInt())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/review/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("reviewId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("userId").value("user123"))
                .andExpect(MockMvcResultMatchers.jsonPath("bookId").value(101))
                .andExpect(MockMvcResultMatchers.jsonPath("content").value("Great book!"))
                .andExpect(MockMvcResultMatchers.jsonPath("rating").value(4.5))
                .andExpect(MockMvcResultMatchers.jsonPath("createAt").value("2024-11-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("userImageUrl").value("http://example.com/image.jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("userName").value("John Doe"));
    }

    @Test
    void getReviewTest_NotFound() throws Exception{
        Mockito.when(reviewService.findReviewById(anyInt())).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/review/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getReviewByBookIdTest() throws Exception {
        responseList.remove(response2);
        Mockito.when(reviewService.findReviewByBookId(anyInt())).thenReturn(responseList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/review/book/101")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].reviewId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value("user123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bookId").value(101))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("Great book!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rating").value(4.5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].createAt").value("2024-11-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userImageUrl").value("http://example.com/image.jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userName").value("John Doe"));
    }

    @Test
    void findReviewByBookIdAndUserIdTest() throws Exception {
        Mockito.when(reviewService.findReviewByBookIdAndUserId(anyInt(), anyString())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/review/101/user123")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("reviewId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("userId").value("user123"))
                .andExpect(MockMvcResultMatchers.jsonPath("bookId").value(101))
                .andExpect(MockMvcResultMatchers.jsonPath("content").value("Great book!"))
                .andExpect(MockMvcResultMatchers.jsonPath("rating").value(4.5))
                .andExpect(MockMvcResultMatchers.jsonPath("createAt").value("2024-11-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("userImageUrl").value("http://example.com/image.jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("userName").value("John Doe"));

    }

    @Test
    void findReviewByBookIdAndUserIdTest_NotFound() throws Exception{
        Mockito.when(reviewService.findReviewByBookIdAndUserId(anyInt(), anyString())).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/review/101/user123")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }


    @Test
    void getRateTest() throws Exception {
        Mockito.when(reviewService.findRateByBookIdAndUserId(anyInt(), anyString())).thenReturn(response.getRating());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/review/rate/101/user123")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf(response.getRating())));
    }

    @Test
    void updateReviewTest() throws Exception {
        response2.setReviewId(1);
        response2.setUserName("John Doe");
        Mockito.when(reviewService.update(any(Review.class))).thenReturn(response2);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/review/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                          "bookId": 202,
                                          "rating": 5
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("reviewId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("userId").value("user123"))
                .andExpect(MockMvcResultMatchers.jsonPath("bookId").value(202))
                .andExpect(MockMvcResultMatchers.jsonPath("content").value("Great book!"))
                .andExpect(MockMvcResultMatchers.jsonPath("rating").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("createAt").value("2024-11-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("userImageUrl").value("http://example.com/image.jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("userName").value("John Doe"));
    }

    @Test
    void updateRateTest() throws Exception {
        response.setRating(5.0);
        Mockito.when(reviewService.updateRate(anyInt(), anyString(), anyDouble(), anyString(), anyString())).thenReturn(response);
        Mockito.when(reviewService.findReviewByBookIdAndUserId(anyInt(), anyString())).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/review/rate")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(""" 
                               {
                                                          "userId": "user123",
                                                          "bookId": 101,
                                                          "rating": 4.8,
                                                          "userImageUrl": "http://example.com/image.jpg",
                                                          "userName": "John Doe"
                               }
                                """))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("reviewId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("userId").value("user123"))
                .andExpect(MockMvcResultMatchers.jsonPath("bookId").value(101))
                .andExpect(MockMvcResultMatchers.jsonPath("content").value("Great book!"))
                .andExpect(MockMvcResultMatchers.jsonPath("rating").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("createAt").value("2024-11-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("userImageUrl").value("http://example.com/image.jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("userName").value("John Doe"));

    }

    @Test
    void deleteReviewTest() throws Exception {
        Mockito.doNothing().when(reviewService).delete(anyInt());

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/review/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }


    @Test
    void deleteReviewTest_NotFound() throws Exception {
        Mockito.doThrow(new RuntimeException()).when(reviewService).delete(anyInt());

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/review/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }











}
