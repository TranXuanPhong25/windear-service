package com.windear.app.service;

import com.windear.app.entity.News;
import com.windear.app.repository.NewsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService{
    private final NewsRepository newsRepository;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    @Transactional
    public News saveNews(News news) {
        return newsRepository.save(news);
    }

    @Override
    public News findNewsById(int newsId) {
        Optional<News> news = newsRepository.findById(newsId);
        if(news.isPresent()) {
            return news.get();
        }
        else {
            throw new RuntimeException("User with title not found: " + newsId);
        }
    }

    @Override
    @Transactional
    public void deleteNews(int newsId) {
        newsRepository.deleteById(newsId);
    }

}
