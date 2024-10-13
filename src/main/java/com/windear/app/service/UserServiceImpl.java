package com.windear.app.service;

import com.windear.app.entity.Book;
import com.windear.app.entity.User;
import com.windear.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BookServiceImpl bookService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BookServiceImpl bookService) {
        this.userRepository = userRepository;
        this.bookService = bookService;
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
            throw new RuntimeException("User with id not found: " + id);
        }
    }

    @Override
    @Transactional
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User handleBookAction(String action, int userId, int bookId) {
        User user = findById(userId);
        Book book = bookService.findById(bookId);
        if (action.equals("borrow")) {
            user.borrowBook(book);
        } else if (action.equals("return")){
            user.returnBook(book);
        }
        return user;
    }
}