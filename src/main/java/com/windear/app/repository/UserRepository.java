package com.windear.app.repository;

import com.windear.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface UserRepository extends CrudRepository<User, String> {
    // Méo định nghĩa thêm method nào, chỉ sử dụng .findAll() có sẵn
}