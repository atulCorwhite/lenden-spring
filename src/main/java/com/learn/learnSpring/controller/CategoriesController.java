package com.learn.learnSpring.controller;

import com.learn.learnSpring.entities.Categories;
import com.learn.learnSpring.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    @PostMapping("/add-categories")
    public ResponseEntity<?> addCategories(@RequestBody List<Categories> categories) {
        return categoriesService.addCategories(categories);
    }

    @GetMapping("/get-all-categories")
    public ResponseEntity<List<Categories>> getAllCategories() {
        return categoriesService.getAllCategories();
    }
}
