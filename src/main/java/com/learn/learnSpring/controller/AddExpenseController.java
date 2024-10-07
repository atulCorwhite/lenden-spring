package com.learn.learnSpring.controller;

import com.learn.learnSpring.entities.AddExpenses;
import com.learn.learnSpring.service.AddExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AddExpenseController {

    @Autowired
     AddExpenseService addExpenseService;

    @PostMapping("/add-expense")
    public ResponseEntity<?> addExpense(@RequestBody AddExpenses addExpenses) {
        try {
            // Call the service to add the expense
            return addExpenseService.addAddExpense(addExpenses);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while adding the expense.");
        }
    }
}

