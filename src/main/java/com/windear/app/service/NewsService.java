package com.windear.app.service;

import com.windear.app.entity.News;

public interface NewsService {
    News saveNews(News news);
    News findNewsById(String title);
    void deleteNews(String title);
}
