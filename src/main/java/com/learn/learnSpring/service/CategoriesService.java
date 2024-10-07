package com.learn.learnSpring.service;

import com.learn.learnSpring.entities.Categories;
import com.learn.learnSpring.entities.User;
import com.learn.learnSpring.repository.UserRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoriesService {
   // ResponseEntity<?> addCategories(Categories categories);
    ResponseEntity<?> addCategories(List<Categories> categories);
    ResponseEntity<List<Categories>> getAllCategories();
}
