package com.learn.learnSpring.entities;

import com.learn.learnSpring.SplitBy;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class Devided {

    long UserId;
    long amount;
    @Enumerated(EnumType.STRING)
    private SplitBy splitBy;
    int percentage;
    long groupId;
    long expenseId;
}
