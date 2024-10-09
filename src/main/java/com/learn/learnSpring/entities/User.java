package com.learn.learnSpring.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String mobileNumber;
    private String password;
    private  String userImage;

    // Define the ManyToMany relationship with GroupModal
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_groups",  // The name of the join table
            joinColumns = @JoinColumn(name = "user_id"),  // Foreign key from the User side
            inverseJoinColumns = @JoinColumn(name = "group_id")  // Foreign key from the Group side, should reference the GroupModal id
    )
    private Set<GroupModal> groups = new HashSet<>();  // Initialize the Set to avoid NullPointerException
}


