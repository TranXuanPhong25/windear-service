package com.windear.app.controller;

import com.windear.app.entity.User;
import com.windear.app.model.UserModel;
import com.windear.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    // Inject Service vào để gọi được
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    // Trả về Model là một List<UserModel>
    public List<UserModel> getUserList() {
        // Service trả về Model (là List<UserModel>) nên có thể return thẳng luôn
        return userService.getUserList();
    }

    // Có thể có các endpoint khác
}
