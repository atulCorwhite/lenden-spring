package com.learn.learnSpring.service.impl;


import com.learn.learnSpring.entities.Categories;
import com.learn.learnSpring.repository.CategoriesRepository;
import com.learn.learnSpring.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {
 @Autowired
    CategoriesRepository categoriesRepository;

    @Override
    public ResponseEntity<?> addCategories(List<Categories> categories) {
        List<Categories> savedCategories = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();

        for (Categories category : categories) {
            Optional<Categories> existingCategory = categoriesRepository.findByCategoryName(category.getCategoryName());
            if (existingCategory.isPresent()) {
                errorMessages.add("Category '" + category.getCategoryName() + "' already exists.");
            } else {
                savedCategories.add(categoriesRepository.save(category));
            }
        }

        if (!errorMessages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessages); // Return conflict status with errors
        }

        return new ResponseEntity<>(savedCategories, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Categories>> getAllCategories() {
        List<Categories> categories = categoriesRepository.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
