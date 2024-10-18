package com.windear.app.service;

import com.windear.app.entity.ExternalBook;
import com.windear.app.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> findAll();
    User findById(int id);
    User add(User user);
    void delete(int id);
    User update(User user);
    ExternalBook borrowBook(int userId, int bookId);
}
