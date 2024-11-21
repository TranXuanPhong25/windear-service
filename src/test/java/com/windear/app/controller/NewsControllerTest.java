package com.windear.app.controller;

import com.windear.app.entity.News;
import com.windear.app.service.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.apache.hc.core5.http.io.support.ClassicRequestBuilder.post;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NewsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsService newsService;

    @Test
    void testNewsCreate() throws Exception {
        News newsResponse = new News();
        LocalDate date = LocalDate.of(2024, 11, 20);
        newsResponse.setNewsId(1);
        newsResponse.setTitle("Test Title");
        newsResponse.setDescription("Test Description");
        newsResponse.setImageUrl("https://example.com/image.png");
        newsResponse.setCreateAt(date);

        Mockito.when(newsService.saveNews(any(News.class))).thenReturn(newsResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/news")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("""
                        {
                            "title": "Test Title",
                            "description": "Test Description",
                            "imageUrl": "https://example.com/image.png"
                        }
                        """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("newsId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("title").value("Test Title"))
                .andExpect(MockMvcResultMatchers.jsonPath("description").value("Test Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("imageUrl").value("https://example.com/image.png"))
                .andExpect(MockMvcResultMatchers.jsonPath("createAt").value("2024-11-20"));
    }

    @Test
    void testGetNewsList() throws Exception {
        List<News> newsList = new ArrayList<>();
        LocalDate date = LocalDate.of(2024, 11, 20);

        News news1 = new News();
        news1.setNewsId(1);
        news1.setTitle("Test Title 1");
        news1.setDescription("Test Description 1");
        news1.setImageUrl("https://example.com/image.png");
        news1.setCreateAt(date);
        newsList.add(news1);

        News news2 = new News();
        news2.setNewsId(2);
        news2.setTitle("Test Title 2");
        news2.setDescription("Test Description 2");
        news2.setImageUrl("https://example.com/image.png");
        news2.setCreateAt(date);
        newsList.add(news2);

        Mockito.when(newsService.findAllNews()).thenReturn(newsList);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/news")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].newsId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Test Title 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Test Description 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].createAt").value("2024-11-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].imageUrl").value("https://example.com/image.png"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].newsId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Test Title 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value("Test Description 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].createAt").value("2024-11-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].imageUrl").value("https://example.com/image.png"));


    }

    @Test
    void testGetNews() throws Exception {
        LocalDate date = LocalDate.of(2024, 11, 20);

        News news = new News();
        news.setNewsId(1);
        news.setTitle("Test Title");
        news.setDescription("Test Description");
        news.setImageUrl("https://example.com/image.png");
        news.setCreateAt(date);

        Mockito.when(newsService.findNewsById(anyInt())).thenReturn(news);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/news/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("newsId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("title").value("Test Title"))
                .andExpect(MockMvcResultMatchers.jsonPath("description").value("Test Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("imageUrl").value("https://example.com/image.png"))
                .andExpect(MockMvcResultMatchers.jsonPath("createAt").value("2024-11-20"));
    }

    @Test
    void testGetNews_NotFound() throws Exception {
        Mockito.when(newsService.findNewsById(anyInt())).thenThrow(new RuntimeException("News with id not found"));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/news/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateNews() throws Exception {
        News newsResponse = new News();
        LocalDate date = LocalDate.of(2024, 11, 20);
        newsResponse.setNewsId(1);
        newsResponse.setTitle("Test Title");
        newsResponse.setDescription("Test Description");
        newsResponse.setImageUrl("https://example.com/image.png");
        newsResponse.setCreateAt(date);

        Mockito.when(newsService.update(any(News.class))).thenReturn(newsResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/news/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                        {
                            "title": "Test Title",
                            "description": "Test Description"
                        }
                        """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("newsId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("title").value("Test Title"))
                .andExpect(MockMvcResultMatchers.jsonPath("description").value("Test Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("imageUrl").value("https://example.com/image.png"))
                .andExpect(MockMvcResultMatchers.jsonPath("createAt").value("2024-11-20"));
    }

    @Test
    void testDeleteNews() throws Exception{
        Mockito.doNothing().when(newsService).deleteNews(1);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/news/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteNews_NotFound() throws Exception {
        Mockito.doThrow(new RuntimeException()).when(newsService).deleteNews(1);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/news/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }
}
