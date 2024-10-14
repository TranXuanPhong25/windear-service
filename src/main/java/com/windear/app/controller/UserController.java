package com.windear.app.controller;

import com.windear.app.entity.User;
import com.windear.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        return userService.add(user);
    }

    @GetMapping("/users")
    public List<User> getUserList() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User findUserById(@PathVariable int id) {
        return userService.findById(id);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.delete(id);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping("/users/{id}/{action}")
    public User returnBook(@PathVariable int id, @PathVariable String action, @RequestBody int bookId) {
        return userService.handleBookAction(action, id, bookId);
    }
}
