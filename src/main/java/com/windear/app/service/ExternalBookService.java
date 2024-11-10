package com.windear.app.service;

import org.springframework.stereotype.Service;

@Service
public interface ExternalBookService {
    String findById(String id);
    String findByTitle(String title, int pageNo);
}
