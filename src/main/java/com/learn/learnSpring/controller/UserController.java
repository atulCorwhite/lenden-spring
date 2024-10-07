package com.learn.learnSpring.controller;

import com.learn.learnSpring.entities.User;
import com.learn.learnSpring.entities.UserLogin;
import com.learn.learnSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create-user")
    ResponseEntity<?> createUser(@RequestBody User user) {

        return  userService.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?>loginUser(@RequestBody UserLogin userLogin){
        return userService.loginUser(userLogin) ;
    }


    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable long id) {
        return userService.deleteUserById(id);
    }

    @GetMapping("/getuser")
    User getUserById(@RequestParam long id){
        return userService.getUserById(id);

    }

    @PutMapping("/update-user/{id}")
    User updateUserById(@PathVariable long id,@RequestBody User user){
        return userService.updateUserById(id,user);
    }

    @GetMapping("/getallusers")
    List<User> getAllUsers(){
        return userService.getUserList();

    }
}
