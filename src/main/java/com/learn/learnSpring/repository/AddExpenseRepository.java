package com.learn.learnSpring.repository;

import com.learn.learnSpring.entities.AddExpenses;
import com.learn.learnSpring.entities.GroupMembers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddExpenseRepository extends JpaRepository<AddExpenses,Long> {

}
