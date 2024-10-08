package com.windear.app.service;

import com.windear.app.entity.User;
import com.windear.app.model.UserModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
//    private final UserRepository userRepository;
    private EntityManager entityManager;

//    @Autowired
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
    @Autowired
    public UserService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @GetMapping("/users")
    public List<UserModel> getUserList() {
        TypedQuery<User> typedQuery = entityManager.createQuery("FROM User", User.class);
        List<User> users = typedQuery.getResultList();
        return users.stream()
                .map(user -> new UserModel(user))
                .collect(Collectors.toList());
    }
}
