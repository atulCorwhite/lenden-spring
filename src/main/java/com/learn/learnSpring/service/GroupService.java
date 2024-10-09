package com.learn.learnSpring.service;

import com.learn.learnSpring.entities.GroupMembers;
import com.learn.learnSpring.entities.GroupModal;
import com.learn.learnSpring.entities.GroupRequest;
import com.learn.learnSpring.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GroupService {

   // ResponseEntity<?> createGroup(GroupModal groupModal);
    ResponseEntity<?> createGroup(GroupRequest groupRequest, Long userId);
    ResponseEntity<?> addGroupMembers(Long groupId, List<GroupMembers> groupMembers);

    ResponseEntity<?> groupList(Long userId);
}

