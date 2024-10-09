package com.learn.learnSpring.controller;

import com.learn.learnSpring.entities.GroupMembers;
import com.learn.learnSpring.entities.GroupRequest;
import com.learn.learnSpring.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupController {
    @Autowired
    GroupService groupService;

    // Create Group API with user email as a query parameter
    @PostMapping("/create-group")
    public ResponseEntity<?> createGroup(@RequestBody GroupRequest groupRequest,
                                         @RequestParam Long userId) {
        // Call the service to create the group with the GroupRequest DTO and userEmail
        return groupService.createGroup(groupRequest, userId);
    }

    @PostMapping("/add-group-members/{groupId}")
    public ResponseEntity<?> addGroupMembers(
            @PathVariable Long groupId,
            @RequestBody List<GroupMembers> groupMembersList) {
        return groupService.addGroupMembers(groupId, groupMembersList);
    }

    @GetMapping("/group-list/{userId}")
    public ResponseEntity<?> getGroupList(@PathVariable Long userId) {
        return groupService.groupList(userId);
    }
}
