package com.insa.controller;

import com.insa.dto.request.UserRequest;
import com.insa.dto.response.RoleResponse;
import com.insa.dto.response.UserResponse;
import com.insa.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keycloak/users/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> createUser(
            @PathVariable Long tenantId,
            @RequestBody UserRequest request) {

        try {
            UserResponse response = userService.addUser(tenantId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the user: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllUsers(
            @PathVariable Long tenantId) {

        try {
            List<UserResponse> response = userService.getAllUsers(tenantId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the users: " + e.getMessage());
        }
    }

    @GetMapping("/get/{user-id}")
    public ResponseEntity<?> getUserById(
            @PathVariable Long tenantId,
            @PathVariable("user-id") String userId) {

        try {
            UserResponse response = userService.getUserById(tenantId, userId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the user: " + e.getMessage());
        }
    }

    @PutMapping("/update/{user-id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long tenantId,
            @PathVariable("user-id") String userId,
            @RequestBody UserRequest request) {

        try {
            UserResponse response = userService.updateUser(tenantId, userId, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the user: " + e.getMessage());
        }
    }

    @PostMapping("/{user-id}/assign-role/{role-name}")
    public ResponseEntity<?> assignRoleToUser(
            @PathVariable Long tenantId,
            @PathVariable("user-id") String userId,
            @PathVariable("role-name") String roleName) {

        try {
            RoleResponse response = userService.assignRoleToUser(tenantId, userId, roleName);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while assigning role to the user: " + e.getMessage());
        }
    }

    @GetMapping("/get/user-roles/{user-id}")
    public ResponseEntity<?> getUserRoles(
            @PathVariable Long tenantId,
            @PathVariable("user-id") String userId) {

        try {
            List<RoleResponse> response = userService.getUserRoles(tenantId, userId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the user roles: " + e.getMessage());
        }
    }

    @DeleteMapping("/{user-id}/remove-role/{role-name}")
    public ResponseEntity<?> removeRoleFromUser(
            @PathVariable Long tenantId,
            @PathVariable("user-id") String userId,
            @PathVariable("role-name") String roleName) {

        try {
            userService.removeRoleFromUser(tenantId, userId, roleName);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Role removed from the user successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while removing role from the user: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{user-id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable Long tenantId,
            @PathVariable("user-id") String userId) {

        try {
            userService.deleteUser(tenantId, userId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("User deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the user: " + e.getMessage());
        }
    }
}
