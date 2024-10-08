package com.windear.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {
    // Để public hết cho đơn giản, bình thường sẽ là private và dùng getter, setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "age")
    public Integer age;

    @Column(name = "password")
    public String password;  // Đã được hash bcrypt

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}