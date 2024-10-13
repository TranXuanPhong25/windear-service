package com.windear.app.service;

import com.windear.app.entity.News;
import com.windear.app.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService{
    private NewsRepository newsRepository;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public News saveNews(News news) {
        return newsRepository.save(news);
    }

    @Override
    public News findNewsById(String title) {
        Optional<News> news = newsRepository.findById(title);
        if(news.isPresent()) {
            return news.get();
        }
        else {
            throw new RuntimeException("User with title not found: " + title);
        }
    }

    @Override
    public void deleteNews(String title) {
        newsRepository.deleteById(title);
    }

}
