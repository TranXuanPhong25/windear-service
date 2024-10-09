package com.windear.app.service;

import com.windear.app.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private EntityManager entityManager;

    @Autowired
    public UserServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findAllUsers() {
        TypedQuery<User> typedQuery = entityManager.createQuery("FROM User", User.class);
        return typedQuery.getResultList();
    }

    @Override
    public User findUserById(int id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new RuntimeException("User id not found: " + id);
        }
        return user;
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        User newUser = entityManager.merge(user);
        return newUser;
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new RuntimeException("User id not found: " + id);
        }
        entityManager.remove(user);
    }
}