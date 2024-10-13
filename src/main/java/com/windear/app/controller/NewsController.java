package com.windear.app.controller;

import com.windear.app.entity.News;
import com.windear.app.service.NewsService;
import com.windear.app.service.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NewsController {
    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping("/news")
    public News createNews(@RequestBody News news) {
        return newsService.saveNews(news);
    }

    @GetMapping("/news/{id}")
    public News getNews(@PathVariable String title) {
        return newsService.findNewsById(title);
    }

    @DeleteMapping("news/{id}")
    public void deleteNews(@PathVariable String title) {
        newsService.deleteNews(title);
    }


}
