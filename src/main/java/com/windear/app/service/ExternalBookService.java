package com.windear.app.service;

import com.windear.app.entity.ExternalBook;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExternalBookService {
    List<ExternalBook> findById(int id);
    List<ExternalBook> findByTitle(String title);
}
