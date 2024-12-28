package com.auth_service.controller;

import com.auth_service.dto.request.GroupRequest;
import com.auth_service.dto.response.GroupResponse;
import com.auth_service.service.GroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keycloak/groups")
@RequiredArgsConstructor
@Tag(name = "Group")
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/add")
    public ResponseEntity<?> createUser(@RequestBody GroupRequest request) {

        try {
            GroupResponse response = groupService.addGroup(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the group: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllGroups() {

        try {
            List<GroupResponse> response = groupService.getAllGroups();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the groups: " + e.getMessage());
        }
    }

    @GetMapping("/get/{group-id}")
    public ResponseEntity<?> getGroupByName(@PathVariable("group-id") String groupId) {

        try {
            GroupResponse response = groupService.getGroupId(groupId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the group: " + e.getMessage());
        }
    }

    @PutMapping("/update/{group-id}")
    public ResponseEntity<?> updateGroup(@PathVariable("group-id") String groupId,
                                        @RequestBody GroupRequest request) {

        try {
            GroupResponse response = groupService.updateGroup(groupId, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the group: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{group-id}")
    public ResponseEntity<?> deleteGroup(@PathVariable("group-id") String groupId) {

        try {
            groupService.deleteGroup(groupId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Group deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the group: " + e.getMessage());
        }
    }
}
