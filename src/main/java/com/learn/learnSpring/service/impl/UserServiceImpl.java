package com.learn.learnSpring.service.impl;

import com.learn.learnSpring.entities.User;
import com.learn.learnSpring.entities.UserLogin;
import com.learn.learnSpring.repository.UserRepository;
import com.learn.learnSpring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).get();
    }


    @Override
    public ResponseEntity<?> createUser(User user) {
        Optional<User> isMobileExist = userRepository.findByMobileNumber(user.getMobileNumber());
        // Check if the mobile number exists
        if (isMobileExist.isPresent()) {
            // Return a bad request response with an error message
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Error: Mobile number already exists.");
        }

        // Save the user and return a success response
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> loginUser(UserLogin userLogin) {
        // 1. Check if the email exists
        Optional<User> userByEmail = userRepository.findByEmail(userLogin.getEmail());
        if (!userByEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email.");
        }
        // 2. Check if the mobile number matches the email

        Optional<User> userByMobileNumber = userRepository.findByEmailAndMobileNumber(
                userLogin.getEmail(),
                userLogin.getMobileNumber()
        );
        if (!userByMobileNumber.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid mobile number.");
        }

        // 3. Check if the password is correct
        Optional<User> userByPassword = userRepository.findByEmailAndMobileNumberAndPassword(
                userLogin.getEmail(),
                userLogin.getMobileNumber(),
                userLogin.getPassword()
        );
        if (!userByPassword.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid password.");
        }

        return ResponseEntity.ok("Login successful");
    }
    @Override
    public ResponseEntity<String> deleteUserById(long id) {
        Optional<User> userById = userRepository.findById(id);

        if (!userById.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found."); // User does not exist
        }

        userRepository.deleteById(id); // User exists and is deleted
        return ResponseEntity.ok("Deleted Success."); // Return success message
    }

    @Override
    public User updateUserById(long id,User user) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());

            if (user.getUserImage() != null && !user.getUserImage().isEmpty()) {
                existingUser.setUserImage(user.getUserImage());
            }
            // Add more fields to update as necessary

            return userRepository.save(existingUser);  // Save and return the updated user
        } else {
            throw new RuntimeException("User with ID " + id + " not found");
        }
    }
}
