package com.windear.app.controller;

import com.windear.app.entity.News;
import com.windear.app.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NewsController {
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping("/news")
    public News createNews(@RequestBody News news) {
        return newsService.saveNews(news);
    }

    @GetMapping("/news")
    public List<News> getNewsList() {
        return newsService.findAllNews();
    }

    @GetMapping("/news/{newsId}")
    public News getNews(@PathVariable int newsId) {
        return newsService.findNewsById(newsId);
    }
    //TODO: add {newsId}
    @PutMapping("/news")
    public News updateNews(@RequestBody News news) {
        return newsService.update(news);
    }

    @DeleteMapping("news/{newsId}")
    public void deleteNews(@PathVariable int newsId) {
        newsService.deleteNews(newsId);
    }


}
