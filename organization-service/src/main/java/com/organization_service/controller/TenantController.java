package com.organization_service.controller;

import com.organization_service.config.PermissionEvaluator;
import com.organization_service.dto.requestDto.TenantRequest;
import com.organization_service.dto.responseDto.TenantResponse;
import com.organization_service.service.TenantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
@Tag(name = "Tenant")
public class TenantController {

    private final TenantService tenantService;
    private final PermissionEvaluator permissionEvaluator;

    @PreAuthorize("hasRole('admin')")
    @PostMapping(value = "/add-tenant")
    public ResponseEntity<?> createTenant(
            @RequestPart("tenant") TenantRequest tenantRequest,
            @RequestPart(value = "logo", required = false) MultipartFile file) throws IOException {

        TenantResponse tenant = tenantService
                .createTenant(tenantRequest, file);
        return new ResponseEntity<>(tenant, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllTenants() {

        List<TenantResponse> tenants = tenantService.getAllTenants();
        return ResponseEntity.ok(tenants);
    }

    @PreAuthorize("isAuthenticated")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getTenantById(@PathVariable UUID id) {

        //permissionEvaluator.getTenantByIdPermission();

        TenantResponse tenant = tenantService.getTenantById(id);
        return ResponseEntity.ok(tenant);
    }

    @GetMapping("/{id}/get-logo")
    public ResponseEntity<?> getTenantLogoById(@PathVariable UUID id) {

        permissionEvaluator.downloadTenantLogoPermission();

        TenantResponse tenant = tenantService.getTenantById(id);
        MediaType mediaType = MediaType.valueOf(tenant.getLogoType());
        byte[] logoBytes = tenantService.getLogoByTenantId(id, mediaType);
        return ResponseEntity.ok().contentType(mediaType).body(logoBytes);
    }

    @PutMapping(value = "/update-tenant/{id}")
    public ResponseEntity<?> updateTenant(
            @PathVariable UUID id,
            @RequestPart("tenant") TenantRequest tenantRequest,
            @RequestPart(value = "logo", required = false) MultipartFile file) throws IOException {

        permissionEvaluator.updateTenantPermission();

        TenantResponse tenant = tenantService
                .updateTenant(id, tenantRequest, file);
        return ResponseEntity.ok(tenant);
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/delete-tenant/{id}")
    public ResponseEntity<?> deleteTenant(@PathVariable UUID id) {

        tenantService.deleteTenant(id);
        return ResponseEntity.ok("Tenant deleted successfully!");
    }
}