package com.learn.learnSpring.entities;

import com.learn.learnSpring.SplitBy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class AddExpenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    long amount;
    long categoryId;
    String description;
    long date;
    long loginUserId;
    String attachment;
    long paidBy;
    @Enumerated(EnumType.STRING)
    private SplitBy splitBy;
    private long groupId;
}
