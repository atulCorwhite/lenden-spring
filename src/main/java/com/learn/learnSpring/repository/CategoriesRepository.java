package com.learn.learnSpring.repository;

import com.learn.learnSpring.entities.Categories;
import com.learn.learnSpring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriesRepository extends JpaRepository<Categories,Long> {

    Optional<Categories> findByCategoryName(String CategoryName);

}
