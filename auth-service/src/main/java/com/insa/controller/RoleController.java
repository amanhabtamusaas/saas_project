package com.insa.controller;

import com.insa.dto.request.RoleRequest;
import com.insa.dto.response.RoleResponse;
import com.insa.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keycloak/roles/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Role")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/add")
    public ResponseEntity<?> createRole(
            @PathVariable Long tenantId,
            @RequestBody RoleRequest request) {

        try {
            RoleResponse response = roleService.addRole(tenantId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the role: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllRoles(
            @PathVariable Long tenantId) {

        try {
            List<RoleResponse> response = roleService.getAllRoles(tenantId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the roles: " + e.getMessage());
        }
    }

    @GetMapping("/get/{role-name}")
    public ResponseEntity<?> getRoleByName(
            @PathVariable Long tenantId,
            @PathVariable("role-name") String roleName) {

        try {
            RoleResponse response = roleService.getRoleByName(tenantId, roleName);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the role: " + e.getMessage());
        }
    }

    @PutMapping("/update/{role-name}")
    public ResponseEntity<?> updateRole(
            @PathVariable Long tenantId,
            @PathVariable("role-name") String roleName,
            @RequestBody RoleRequest request) {

        try {
            RoleResponse response = roleService.updateRole(tenantId, roleName, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the role: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{role-name}")
    public ResponseEntity<?> deleteRole(
            @PathVariable Long tenantId,
            @PathVariable("role-name") String roleName) {

        try {
            roleService.deleteRole(tenantId, roleName);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Role deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the role: " + e.getMessage());
        }
    }
}
