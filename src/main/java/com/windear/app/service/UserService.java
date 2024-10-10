package com.windear.app.service;

import com.windear.app.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> findAllUsers();
    User findUserById(int id);
    User saveUser(User user);
    void deleteUser(int id);
}
