package com.learn.learnSpring.service.impl;

import com.learn.learnSpring.dto.ApiResponse;
import com.learn.learnSpring.entities.User;
import com.learn.learnSpring.entities.UserLogin;
import com.learn.learnSpring.repository.UserRepository;
import com.learn.learnSpring.security.JwtHelper;
import com.learn.learnSpring.service.CustomUserDetailsService;
import com.learn.learnSpring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    /*@Autowired
    private JwtUtil jwtUtil;*/



    private final CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    private final JwtHelper helper;

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).get();
    }


 /*   @Override
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
    }*/

    @Override
    public ResponseEntity<ApiResponse<User>> createUser(User user) {
        Optional<User> isMobileExist = userRepository.findByMobileNumber(user.getMobileNumber());

        // Check if the mobile number exists
        if (isMobileExist.isPresent()) {
            // Return a bad request response with an error message
            ApiResponse<User> response = new ApiResponse<>(
                    "Error: Mobile number already exists.",
                    null,
                    false
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Save the user and return a success response
        User savedUser = userRepository.save(user);
        ApiResponse<User> response = new ApiResponse<>(
                "User created successfully.",
                savedUser,
                true
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

 /*   @Override
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
    }*/

    @Override
    public ResponseEntity<ApiResponse<UserLogin>> loginUser(UserLogin userLogin) {
        // 1. Check if the user exists by email
        Optional<User> user = userRepository.findByEmail(userLogin.getEmail());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("Invalid email.", null, false));
        }

        // 2. Check if the mobile number matches the email
        if (!user.get().getMobileNumber().equals(userLogin.getMobileNumber())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("Invalid mobile number.", null, false));
        }

        // 3. Check if the password is correct
        if (!user.get().getPassword().equals(userLogin.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("Invalid password.", null, false));
        }

        // 4. Generate JWT tokentoken
//        this.doAuthenticate(userLogin.getEmail(), userLogin.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.get().getEmail());
        String token = this.helper.generateToken(userDetails);

        UserLogin userDetail = new UserLogin();
        userDetail.setEmail(user.get().getEmail());
        userDetail.setToken(token);

        // Return success response with token
        return ResponseEntity.ok(new ApiResponse<>("Login successful.", userDetail, true));
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


    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }
}
