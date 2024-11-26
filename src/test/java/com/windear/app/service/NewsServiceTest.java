package com.windear.app.service;

import com.windear.app.entity.News;
import com.windear.app.repository.NewsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Executable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
@AutoConfigureMockMvc
public class NewsServiceTest {
    @Autowired
    private NewsService newsService;

    @MockBean
    private NewsRepository newsRepository;

    private News news;
    private News newsRequest;
    private LocalDate date = LocalDate.of(2024, 11, 20);

    @BeforeEach
    void initDAta() {
        newsRequest = new News();
        newsRequest.setTitle("Test Title");
        newsRequest.setDescription("Test Description");
        newsRequest.setImageUrl("https://example.com/image.png");

        news = new News();
        news.setNewsId(1);
        news.setTitle("Test Title");
        news.setDescription("Test Description");
        news.setImageUrl("https://example.com/image.png");
        news.setCreateAt(date);
    }



    @Test
    void saveNewsTest() {
        Mockito.when(newsRepository.save(any(News.class))).thenReturn(news);

        News response = newsService.saveNews(newsRequest);

        Assertions.assertEquals(response.getNewsId(), 1);
        Assertions.assertEquals(response.getTitle(), "Test Title");
        Assertions.assertEquals(response.getDescription(), "Test Description");
        Assertions.assertEquals(response.getImageUrl(), "https://example.com/image.png");
        Assertions.assertEquals(response.getCreateAt(), date);
    }

    @Test
    void findNewsByIdTest_Success() {
        Mockito.when(newsRepository.findById(1)).thenReturn(Optional.of(news));

        News respone = newsService.findNewsById(1);

        Assertions.assertEquals(respone.getNewsId(), 1);
        Assertions.assertEquals(respone.getTitle(), "Test Title");
        Assertions.assertEquals(respone.getDescription(), "Test Description");
        Assertions.assertEquals(respone.getImageUrl(), "https://example.com/image.png");
        Assertions.assertEquals(respone.getCreateAt(), date);
    }

    @Test
    void findNewsByIdTest_NotFound() {
        Mockito.doThrow(new RuntimeException("News with ID not found: 1")).when(newsRepository).findById(1);

        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            newsService.findNewsById(1);
        });

        Assertions.assertEquals("News with ID not found: 1", exception.getMessage());
    }

    @Test
    void findAllNewsTest() {
        List<News> newsList = new ArrayList<>();

        News news2;
        news2 = new News();
        news2.setNewsId(2);
        news2.setTitle("Test Title 2");
        news2.setDescription("Test Description 2");
        news2.setImageUrl("https://example.com/image.png");
        news2.setCreateAt(date);

        newsList.add(news);
        newsList.add(news2);

        Mockito.when(newsRepository.findAll()).thenReturn(newsList);

        List<News> response = newsService.findAllNews();

        Assertions.assertEquals(2, response.size());
        Assertions.assertEquals(response.get(0).getNewsId(), 1);
        Assertions.assertEquals(response.get(0).getTitle(), "Test Title");
        Assertions.assertEquals(response.get(0).getDescription(), "Test Description");
        Assertions.assertEquals(response.get(0).getImageUrl(), "https://example.com/image.png");
        Assertions.assertEquals(response.get(0).getCreateAt(), date);
        Assertions.assertEquals(response.get(1).getNewsId(), 2);
        Assertions.assertEquals(response.get(1).getTitle(), "Test Title 2");
        Assertions.assertEquals(response.get(1).getDescription(), "Test Description 2");
        Assertions.assertEquals(response.get(1).getImageUrl(), "https://example.com/image.png");
        Assertions.assertEquals(response.get(1).getCreateAt(), date);

    }

    @Test
    void updateNewsTest() {
        newsRequest.setNewsId(1);
        newsRequest.setTitle("Test Title update");
        newsRequest.setDescription(null);
        newsRequest.setImageUrl(null);

        News newsPre;
        newsPre = new News();
        newsPre.setNewsId(1);
        newsPre.setTitle("Test Title update");
        newsPre.setDescription("Test Description");
        newsPre.setImageUrl("https://example.com/image.png");
        newsPre.setCreateAt(date);

        news.setNewsId(1);
        news.setTitle("Test Title update");
        news.setDescription("Test Description");
        news.setImageUrl("https://example.com/image.png");
        news.setCreateAt(date);

        Mockito.when(newsRepository.save(any(News.class))).thenReturn(news);
        Mockito.when(newsRepository.findById(1)).thenReturn(Optional.of(newsPre));
        News response = newsService.update(newsRequest);

        Assertions.assertEquals(response.getNewsId(), 1);
        Assertions.assertEquals(response.getTitle(), "Test Title update");
        Assertions.assertEquals(response.getDescription(), "Test Description");
        Assertions.assertEquals(response.getImageUrl(), "https://example.com/image.png");
        Assertions.assertEquals(response.getCreateAt(), date);

    }

    @Test
    void updateNewsTest_NotFound() {
        newsRequest.setNewsId(1);
        newsRequest.setTitle("Test Title update");
        newsRequest.setDescription(null);
        newsRequest.setImageUrl(null);

        Mockito.doThrow(new RuntimeException("News with ID not found: 1")).when(newsRepository).findById(1);
        Mockito.when(newsRepository.save(any(News.class))).thenReturn(news);

        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            newsService.update(newsRequest);
        });
        Assertions.assertEquals("News with ID not found: 1", exception.getMessage());
    }

    @Test
    void deleteNewsTest() {
        Mockito.doNothing().when(newsRepository).deleteById(1);
        Mockito.when(newsRepository.findById(1)).thenReturn(Optional.of(news));

        newsService.deleteNews(1);

        Mockito.verify(newsRepository).deleteById(1);

    }

    @Test
    void deleteNewsTest_NotFound() {
        Mockito.doThrow(new RuntimeException("News with ID not found: 1")).when(newsRepository).findById(1);

        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            newsService.deleteNews(1);
        });
        Assertions.assertEquals("News with ID not found: 1", exception.getMessage());
    }




}
