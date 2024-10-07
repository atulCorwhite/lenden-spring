package com.learn.learnSpring.service;

import com.learn.learnSpring.entities.AddExpenses;
import com.learn.learnSpring.entities.Categories;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AddExpenseService {

    ResponseEntity<?> addAddExpense(AddExpenses addExpenses);
}
