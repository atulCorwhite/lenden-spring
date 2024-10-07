package com.learn.learnSpring.service;

import com.learn.learnSpring.entities.User;
import com.learn.learnSpring.entities.UserLogin;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    List<User> getUserList();

    User getUserById(long id);

    ResponseEntity<?> createUser(User user);

    ResponseEntity<?> loginUser(UserLogin userLogin);

    ResponseEntity<String> deleteUserById(long id);

    User updateUserById(long id, User user);
}
