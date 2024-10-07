package com.learn.learnSpring.service.impl;

import com.learn.learnSpring.entities.AddExpenses;
import com.learn.learnSpring.entities.GroupMembers;
import com.learn.learnSpring.entities.GroupModal;
import com.learn.learnSpring.repository.AddExpenseRepository;
import com.learn.learnSpring.repository.GroupRepository;
import com.learn.learnSpring.service.AddExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor

public class AddExpenseServiceImpl implements AddExpenseService {

    @Autowired
    AddExpenseRepository addExpenseRepository;
    @Autowired
    GroupRepository groupRepository;


    @Override
    public ResponseEntity<?> addAddExpense(AddExpenses addExpenses) {
      //  List<GroupMembers> members = groupRepository.findById(addExpenses.getGroupId());

        GroupModal group = groupRepository.findById(addExpenses.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found for the given ID."));
        // Get the members from the group
        Set<GroupMembers> members = group.getMembers();


        // Ensure there are members to split the expense
        if (members.isEmpty()) {
            throw new IllegalArgumentException("No members found for the given group ID.");
        }
        AddExpenses savedExpense = addExpenseRepository.save(addExpenses);
        double splitAmount = (double) savedExpense.getAmount() / members.size();


        return ResponseEntity.ok("Expense added and split amount calculated: " + splitAmount);

    }
}
