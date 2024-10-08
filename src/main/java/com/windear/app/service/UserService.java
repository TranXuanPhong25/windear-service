package com.windear.app.service;

import com.windear.app.entity.User;
import com.windear.app.model.UserModel;
import com.windear.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModel> getUserList() {
        // Lấy ra từ Repository dạng List<User> là Entity
        List<User> users = (List<User>) userRepository.findAll();

        // Biển đổi List<Entity> thành List<Model> (xem ở constructor của UserModel có code copy dữ liệu)

        // Hoặc dùng cách bình thường (gà :D)
//        // Chọn 1 trong 2 nhé
//        List<UserModel> userModels = new ArrayList<>();
//        for (User user: users)
//            userModels.add(new UserModel(user));

        return users.stream()
                .map(UserModel::new)
                .collect(Collectors.toList());  // Code khá nhiều so với Controller
    }
}
