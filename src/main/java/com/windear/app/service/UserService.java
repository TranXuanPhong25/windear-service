package com.windear.app.service;

import com.windear.app.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> findAll();
    User findById(int id);
    User add(User user);
    User update(User user);
}
