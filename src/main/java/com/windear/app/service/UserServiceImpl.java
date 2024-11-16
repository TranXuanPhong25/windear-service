package com.windear.app.service;

import com.windear.app.entity.Book;
import com.windear.app.entity.BookId;
import com.windear.app.entity.User;
import com.windear.app.exception.BookNotFoundException;
import com.windear.app.exception.UserNotFoundException;
import com.windear.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ExternalBookService externalBookService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ExternalBookService externalBookService) {
        this.userRepository = userRepository;
        this.externalBookService = externalBookService;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        else {
            throw new UserNotFoundException("User with id not found: " + id);
        }
    }

    @Override
    @Transactional
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        User userFromDB = findById(user.getId());
        if (user.getAge() != null) {
            userFromDB.setAge(user.getAge());
        }
        if (user.getName() != null) {
            userFromDB.setName(user.getName());
        }
        if (user.getPassword() != null) {
            userFromDB.setPassword(user.getPassword());
        }
        userRepository.save(userFromDB);
        return userFromDB;
    }
}