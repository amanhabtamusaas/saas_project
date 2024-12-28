package com.auth_service.controller;

import com.auth_service.config.PermissionEvaluator;
import com.auth_service.dto.request.ResourceRequest;
import com.auth_service.dto.request.RolesRequest;
import com.auth_service.dto.response.ResourceResponse;
import com.auth_service.service.ResourceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/keycloak/resources/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Resource")
public class ResourceController {

    private final ResourceService resourceService;
    private final PermissionEvaluator permissionEvaluator;

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/add")
    public ResponseEntity<?> addResource(
            @PathVariable UUID tenantId) {

        List<ResourceResponse> responses = resourceService
                .addResource(tenantId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllResources(
            @PathVariable UUID tenantId) {

        permissionEvaluator.getAllResourcesPermission();

        List<ResourceResponse> response = resourceService
                .getAllResources(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/exclude-default-role")
    public ResponseEntity<?> getResourcesNotContainDefaultRole(
            @PathVariable UUID tenantId) {

        permissionEvaluator.getAllResourcesPermission();

        List<ResourceResponse> response = resourceService
                .getResourcesNotContainDefaultRole(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/{resource-id}")
    public ResponseEntity<?> getResourceById(
            @PathVariable UUID tenantId,
            @PathVariable("resource-id") UUID resourceId) {

        permissionEvaluator.getResourceByIdPermission();

        ResourceResponse response = resourceService
                .getResourceById(tenantId, resourceId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/resource")
    public ResponseEntity<?> getResourceByName(
            @PathVariable UUID tenantId,
            @RequestParam String resourceName) {

        permissionEvaluator.getResourceByNamePermission();

        ResourceResponse response = resourceService
                .getResponseByName(tenantId, resourceName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/group")
    public ResponseEntity<?> getResourcesByGroupName(
            @PathVariable UUID tenantId,
            @RequestParam String groupName) {

        permissionEvaluator.getResourcesByGroupPermission();

        List<ResourceResponse> response = resourceService
                .getResourcesByGroupName(tenantId, groupName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping("/update/{resource-id}")
    public ResponseEntity<?> updateResource(
            @PathVariable UUID tenantId,
            @PathVariable("resource-id") UUID resourceId,
            @RequestBody ResourceRequest request) {

        ResourceResponse response = resourceService
                .updateResource(tenantId, resourceId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/assign-roles/{resourceId}")
    public ResponseEntity<?> giveResourceAccessToRole(
            @PathVariable UUID tenantId,
            @PathVariable UUID resourceId,
            @RequestBody RolesRequest request) {

        permissionEvaluator.giveResourceAccessToRolePermission();

        ResourceResponse response = resourceService
                .giveResourceAccessToRole(tenantId, resourceId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/role/{roleName}")
    public ResponseEntity<?> getResourcesByRole(
            @PathVariable UUID tenantId,
            @PathVariable String roleName) {

        permissionEvaluator.getResourcesByRoleNamePermission();

        List<ResourceResponse> response = resourceService
                .getResourcesByRole(tenantId, roleName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/unassign-roles/{resourceId}/{roleName}")
    public ResponseEntity<?> removeResourceAccessFromRole(
            @PathVariable UUID tenantId,
            @PathVariable UUID resourceId,
            @PathVariable String roleName) {

        permissionEvaluator.removeResourceAccessFromRolePermission();

        ResourceResponse response = resourceService
                .removeResourceAccessFromRole(tenantId, resourceId, roleName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/delete/{resource-id}")
    public ResponseEntity<?> deleteResource(
            @PathVariable UUID tenantId,
            @PathVariable("resource-id") UUID resourceId) {

        resourceService.deleteResource(tenantId, resourceId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Resource deleted successfully!");
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/delete-all")
    public ResponseEntity<?> deleteAllResource(
            @PathVariable UUID tenantId) {

        resourceService.deleteAllResources(tenantId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("All resources are deleted successfully!");
    }
}