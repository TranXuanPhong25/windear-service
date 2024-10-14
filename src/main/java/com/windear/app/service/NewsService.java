package com.windear.app.service;

import com.windear.app.entity.News;

import java.util.List;

public interface NewsService {
    News saveNews(News news);
    List<News> findAllNews();
    News findNewsById(int newsId);
    void deleteNews(int newsId);
    News update(News news);
}
