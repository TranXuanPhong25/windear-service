package com.windear.app.service;

import com.windear.app.entity.News;
import com.windear.app.repository.NewsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
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
        news.setCreateAt(LocalDate.now());
        return newsRepository.save(news);
    }

    @Override
    public News findNewsById(int newsId) {
        Optional<News> news = newsRepository.findById(newsId);
        if (news.isPresent()) {
            return news.get();
        } else {
            throw new RuntimeException("User with title not found: " + newsId);
        }
    }

    @Override
    public List<News> findAllNews() {
        return newsRepository.findAll();
    }

    @Override
    @Transactional
    public News update(News news) {
        News newsFromDB = findNewsById(news.getNewsId());
        if(news.getDescription() != null) {
            newsFromDB.setDescription(news.getDescription());
        }
        if(news.getTitle() != null) {
            newsFromDB.setTitle(news.getTitle());
        }
        if(news.getImageUrl() != null) {
            newsFromDB.setImageUrl(news.getImageUrl());
        }
        return newsRepository.save(newsFromDB);
    }

    @Override
    @Transactional
    public void deleteNews(int newsId) {
        if(findNewsById(newsId) != null) {
            newsRepository.deleteById(newsId);
        }
    }

}
