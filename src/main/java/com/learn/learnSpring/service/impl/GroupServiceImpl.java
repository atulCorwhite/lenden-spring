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
import java.util.Set;


@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {


    @Autowired
    private UserRepository userRepository;
    private final GroupRepository groupRepository;

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
    }

    @Override
    public ResponseEntity<?> addGroupMembers(Long groupId, List<GroupMembers> groupMembersList) {

        GroupModal group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        Set<GroupMembers> existingMembers = group.getMembers();  // Get the existing members

        // To track how many new members were added
        int newMembersAdded = 0;


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
    }

    @Override
    public ResponseEntity<?> groupList(Long userId) {
        return new ResponseEntity<>(groupRepository.getGroupList(userId),HttpStatus.OK);
//        return new ResponseEntity<>(groupRepository.findByCreatedBy(userId),HttpStatus.OK);
    }
}
