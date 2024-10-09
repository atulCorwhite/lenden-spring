package com.learn.learnSpring.service.impl;

import com.learn.learnSpring.entities.GroupMembers;
import com.learn.learnSpring.entities.GroupModal;
import com.learn.learnSpring.entities.GroupRequest;
import com.learn.learnSpring.entities.User;
import com.learn.learnSpring.repository.GroupRepository;
import com.learn.learnSpring.repository.UserRepository;
import com.learn.learnSpring.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {


    @Autowired
    private UserRepository userRepository;
    private final GroupRepository groupRepository;
/*
    @Override
    public ResponseEntity<?> createGroup(GroupRequest groupRequest, String email) {
        // Fetch the user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Create the Group entity and set its properties
        GroupModal group = new GroupModal();
        group.setGroupName(groupRequest.getGroupName());
        group.setGroupDes(groupRequest.getGroupDes());
        group.setImage(groupRequest.getImage());

        // Add the user to the group's users set
        group.setCreatedBy(user.getId());

        // Also add the group to the user's groups set
        // user.getGroups().add(group);

        // Save the group
        GroupModal savedGroup = groupRepository.save(group);

        // Return a success response
        return new ResponseEntity<>(savedGroup, HttpStatus.CREATED);
    }*/

    @Override
    public ResponseEntity<?> createGroup(GroupRequest groupRequest, Long userId) {
        // Fetch the user by userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Create the Group entity and set its properties
        GroupModal group = new GroupModal();
        group.setGroupName(groupRequest.getGroupName());
        group.setGroupDes(groupRequest.getGroupDes());
        group.setImage(groupRequest.getImage());

        // Set the creator of the group to the user ID
        group.setCreatedBy(user.getId());

        // Save the group
        GroupModal savedGroup = groupRepository.save(group);

        // Return a success response
        return new ResponseEntity<>(savedGroup, HttpStatus.CREATED);
    }

   /* @Override
    public ResponseEntity<?> addGroupMembers(Long groupId, List<GroupMembers> groupMembersList) {

        GroupModal group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        Set<GroupMembers> existingMembers = group.getMembers();  // Get the existing members

        // To track how many new members were added
        int newMembersAdded = 0;
        // Get the logged-in user's ID from the group (the user who created it)
        Long loggedInUserId = group.getCreatedBy();

        User loggedInUser = userRepository.findById(loggedInUserId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        boolean isLoggedInUserMember = existingMembers.stream()
                .anyMatch(member -> member.getEmail().equalsIgnoreCase(loggedInUser.getEmail()));


        for (GroupMembers members : groupMembersList) {
            boolean isDuplicate = existingMembers.stream()
                    .anyMatch(existingMember ->
                            existingMember.getEmail().equalsIgnoreCase(members.getEmail()) ||
                                    existingMember.getMobileNumber().equals(members.getMobileNumber())
                    );
            if (!isDuplicate) {
                GroupMembers newMember = new GroupMembers();
                newMember.setName(members.getName());
                newMember.setEmail(members.getEmail());
                newMember.setMobileNumber(members.getMobileNumber());

                group.getMembers().add(newMember);
                newMembersAdded++;
            }
        }
        // Save the group with new members, if any were added
        if (newMembersAdded > 0) {
            groupRepository.save(group);
            return new ResponseEntity<>("Added " + newMembersAdded + " new members to the group.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No new members were added (duplicates found).", HttpStatus.OK);
        }
    }*/

    @Override
    public ResponseEntity<?> addGroupMembers(Long groupId, List<GroupMembers> groupMembersList) {
        int newMembersAdded = 0;
        // Fetch the group by ID
        GroupModal group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        Set<GroupMembers> existingMembers = group.getMembers();  // Get the existing members

        // Get the logged-in user's ID from the group (the user who created it)
        Long loggedInUserId = group.getCreatedBy();

        // Retrieve full user data directly using the user ID
        User loggedInUser = userRepository.findById(loggedInUserId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Check if the logged-in user is already a member
        boolean isLoggedInUserMember = existingMembers.stream()
                .anyMatch(member -> member.getEmail().equalsIgnoreCase(loggedInUser.getEmail()));

        // If not a member, add the logged-in user as the first member
        if (!isLoggedInUserMember) {
            GroupMembers loggedInUserMember = new GroupMembers();
            loggedInUserMember.setName(loggedInUser.getName());
            loggedInUserMember.setEmail(loggedInUser.getEmail());
            loggedInUserMember.setMobileNumber(loggedInUser.getMobileNumber());

            // Add the logged-in user as the first member
            group.getMembers().add(loggedInUserMember);
        }

        // Process the incoming group members
        for (GroupMembers member : groupMembersList) {
            boolean isDuplicate = existingMembers.stream()
                    .anyMatch(existingMember ->
                            existingMember.getEmail().equalsIgnoreCase(member.getEmail()) ||
                                    existingMember.getMobileNumber().equals(member.getMobileNumber())
                    );

            if (!isDuplicate) {
                GroupMembers newMember = new GroupMembers();
                newMember.setName(member.getName());
                newMember.setEmail(member.getEmail());
                newMember.setMobileNumber(member.getMobileNumber());
                group.getMembers().add(newMember);
                newMembersAdded++;
            }
        }

        // Save the group with new members
        groupRepository.save(group);
        if (newMembersAdded > 0) {
            groupRepository.save(group);
            return new ResponseEntity<>("Added " + newMembersAdded + " new members to the group.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No new members were added (duplicates found).", HttpStatus.OK);
        }
    }


    @Override
    public ResponseEntity<?> groupList(Long userId) {
        return new ResponseEntity<>(groupRepository.getGroupList(userId),HttpStatus.OK);
//        return new ResponseEntity<>(groupRepository.findByCreatedBy(userId),HttpStatus.OK);
    }
}
