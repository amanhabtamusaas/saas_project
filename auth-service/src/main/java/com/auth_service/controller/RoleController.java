package com.auth_service.controller;

import com.auth_service.config.PermissionEvaluator;
import com.auth_service.dto.request.RoleRequest;
import com.auth_service.dto.response.RoleResponse;
import com.auth_service.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/keycloak/roles/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Role")
public class RoleController {

    private final RoleService roleService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createRole(
            @PathVariable UUID tenantId,
            @RequestBody RoleRequest request) {

        permissionEvaluator.addRolePermission();

        RoleResponse response = roleService.addRole(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/add/admin-and-default")
    public ResponseEntity<?> addDefaultAndAdminRole(
            @PathVariable UUID tenantId) {

        List<RoleResponse> responses = roleService.addDefaultAndAdminRole(tenantId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllRoles(
            @PathVariable UUID tenantId) {

        permissionEvaluator.getAllRolesPermission();

        List<RoleResponse> response = roleService.getAllRoles(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/{role-name}")
    public ResponseEntity<?> getRoleByName(
            @PathVariable UUID tenantId,
            @PathVariable("role-name") String roleName) {

        permissionEvaluator.getRoleByNamePermission();

        RoleResponse response = roleService.getRoleByName(tenantId, roleName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{role-name}")
    public ResponseEntity<?> updateRole(
            @PathVariable UUID tenantId,
            @PathVariable("role-name") String roleName,
            @RequestBody RoleRequest request) {

        permissionEvaluator.updateRolePermission();

        RoleResponse response = roleService.updateRole(tenantId, roleName, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{user-id}/assign-role/{role-name}")
    public ResponseEntity<?> assignRoleToUser(
            @PathVariable UUID tenantId,
            @PathVariable("user-id") String userId,
            @PathVariable("role-name") String roleName) {

        permissionEvaluator.assignRoleToUserPermission();

        RoleResponse response = roleService.assignRoleToUser(tenantId, userId, roleName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{user-id}/unassign-role/{role-name}")
    public ResponseEntity<?> unAssignRoleFromUser(
            @PathVariable UUID tenantId,
            @PathVariable("user-id") String userId,
            @PathVariable("role-name") String roleName) {

        permissionEvaluator.unassignRoleFromUserPermission();

        roleService.unAssignRoleFromUser(tenantId, userId, roleName);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Role removed from the user successfully!");
    }

    @DeleteMapping("/delete/{role-name}")
    public ResponseEntity<?> deleteRole(
            @PathVariable UUID tenantId,
            @PathVariable("role-name") String roleName) {

        permissionEvaluator.deleteRolePermission();

        roleService.deleteRole(tenantId, roleName);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Role deleted successfully!");
    }
}