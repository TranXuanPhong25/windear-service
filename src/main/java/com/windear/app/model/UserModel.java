package com.windear.app.model;

import com.windear.app.entity.User;

// Có hậu tố Model là Model
public class UserModel {
    public String name;
    public Integer age;
    // Không public password, khi mapping từ Entity > Model thì field password bị bỏ đi
    // Không trả về password cho người dùng
    public UserModel (){}    // Copy thông qua constructor, khi new Model(entity)
    // Xem trong UserService có chỗ .map đó, đó là Stream API của java
    public UserModel(User entity) {
        this.name = entity.name;
        this.age = entity.age;
        // Không gán password, vì bị bỏ qua
    }
}
