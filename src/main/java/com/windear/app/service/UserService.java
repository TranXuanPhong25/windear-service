package com.windear.app.service;

import com.windear.app.entity.User;
import com.windear.app.model.UserModel;
import com.windear.app.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.hibernate.sql.ast.tree.expression.SqlTuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<UserModel> getUserList() {
        TypedQuery<User> typedQuery = entityManager.createQuery("FROM User", User.class);
        List<User> users = typedQuery.getResultList();
        return users.stream()
                .map(user -> new UserModel(user))
                .collect(Collectors.toList());
    }
}
