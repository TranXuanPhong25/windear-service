package com.windear.app.service;

import com.windear.app.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public List<User> findAllUsers();
    public User findUserById(int id);
    public User saveUser(User user);
    public void deleteUser(int id);
}
