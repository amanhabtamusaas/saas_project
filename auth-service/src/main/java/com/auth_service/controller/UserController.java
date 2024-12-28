package com.auth_service.controller;

import com.auth_service.config.PermissionEvaluator;
import com.auth_service.dto.request.ResetPasswordRequest;
import com.auth_service.dto.request.UpdateUserRequest;
import com.auth_service.dto.response.RoleResponse;
import com.auth_service.dto.response.UserResponse;
import com.auth_service.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/keycloak/users/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final UserService userService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createUser(
            @PathVariable UUID tenantId,
            @RequestParam String employeeId) {

        permissionEvaluator.addUserPermission();

        UserResponse response = userService.addUser(tenantId, employeeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/add/admin")
    public ResponseEntity<?> addAdminUser(
            @PathVariable UUID tenantId) {

        UserResponse response = userService.addAdminUser(tenantId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllUsers(
            @PathVariable UUID tenantId) {

        permissionEvaluator.getAllUsersPermission();

        List<UserResponse> response = userService.getAllUsers(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/{user-id}")
    public ResponseEntity<?> getUserById(
            @PathVariable UUID tenantId,
            @PathVariable("user-id") String userId) {

        permissionEvaluator.getUserByIdPermission();

        UserResponse response = userService.getUserById(tenantId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/username")
    public ResponseEntity<?> getUserByUsername(
            @PathVariable UUID tenantId,
            @RequestParam String username) {

        permissionEvaluator.getUserRolesPermission();

        UserResponse response = userService.getUserByUsername(tenantId, username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/user-roles/{user-id}")
    public ResponseEntity<?> getUserRoles(
            @PathVariable UUID tenantId,
            @PathVariable("user-id") String userId) {

        permissionEvaluator.getUserRolesPermission();

        List<RoleResponse> response = userService.getUserRoles(tenantId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{user-id}/{employeeId}")
    public ResponseEntity<?> updateUser(
            @PathVariable UUID tenantId,
            @PathVariable("user-id") String userId,
            @PathVariable UUID employeeId) {

        permissionEvaluator.updateUserPermission();

        UserResponse response = userService.updateUser(tenantId, userId, employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/add-email/{user-id}")
    public ResponseEntity<?> addEmailToUser(
            @PathVariable UUID tenantId,
            @PathVariable("user-id") String userId,
            @RequestParam String email) {

        permissionEvaluator.updateUserPermission();

        UserResponse response = userService.addEmailToUser(tenantId, userId, email);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('default-roles-saas-realm')")
    @PutMapping("/send-verification-email/{userId}")
    public ResponseEntity<?> sendVerificationEmail(
            @PathVariable UUID tenantId,
            @PathVariable String userId) {

        userService.sendVerificationEmail(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Verification email sent successfully!");
    }

    @PreAuthorize("hasRole('default-roles-saas-realm')")
    @PutMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @PathVariable UUID tenantId,
            @RequestParam String email) {

        userService.forgotPassword(tenantId, email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasRole('default-roles-saas-realm')")
    @PutMapping("{userId}/reset-password")
    public ResponseEntity<?> resetUserPassword(
            @PathVariable UUID tenantId,
            @PathVariable String userId,
            @RequestBody ResetPasswordRequest request) {

        userService.resetUserPassword(tenantId, userId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/delete/{user-id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable UUID tenantId,
            @PathVariable("user-id") String userId) {

        permissionEvaluator.deleteUserPermission();

        userService.deleteUser(tenantId, userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("User deleted successfully!");
    }
}