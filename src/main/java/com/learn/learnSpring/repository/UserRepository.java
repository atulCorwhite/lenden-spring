package com.learn.learnSpring.repository;

import com.learn.learnSpring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByMobileNumber(String mobileNumber);
    Optional<User> findByEmail(String email);
    Optional<User> findById(long id);

    Optional<User> findByEmailAndMobileNumber(String mobile, String email);

    Optional<User> findByEmailAndMobileNumberAndPassword(String email, String phoneNumber, String password);


}
