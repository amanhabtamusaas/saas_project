package com.insa.controller;

import com.insa.dto.requestDto.TenantRequest;
import com.insa.dto.responseDto.TenantResponse;
import com.insa.service.TenantService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
@Tag (name = "Tenant")
public class TenantController {

    private final TenantService tenantService;

    @PostMapping(value = "/add-tenant")
    public ResponseEntity<TenantResponse> createTenant(
            @RequestPart("tenant") TenantRequest tenantRequest,
            @RequestPart(value = "logo", required = false) MultipartFile file) throws IOException {

        TenantResponse Tenant = tenantService
                .createTenant (tenantRequest, file);
        return new ResponseEntity<> (Tenant, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<TenantResponse>> getAllTenants() {
        List<TenantResponse> Tenants = tenantService.getAllTenants ();
        return ResponseEntity.ok (Tenants);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TenantResponse> getTenantById(@PathVariable Long id) {
        TenantResponse Tenant = tenantService.getTenantById (id);
        return ResponseEntity.ok (Tenant);
    }

    @GetMapping("/{id}/get-logo")
    public ResponseEntity<byte[]> getTenantLogoById(@PathVariable Long id) {
        try {
            TenantResponse tenant = tenantService.getTenantById (id);
            MediaType mediaType = MediaType.valueOf (tenant.getLogoType ());
            byte[] logoBytes = tenantService.getLogoByTenantId (id, mediaType);

            return ResponseEntity.ok ().contentType (mediaType).body (logoBytes);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/update-tenant/{id}")
    public ResponseEntity<TenantResponse> updateTenant(
            @PathVariable Long id,
            @RequestPart("tenant") TenantRequest tenantRequest,
            @RequestPart(value = "logo", required = false) MultipartFile file) throws IOException {

        TenantResponse Tenant = tenantService
                .updateTenant (id, tenantRequest, file);
        return ResponseEntity.ok (Tenant);
    }

    @DeleteMapping("/delete-tenant/{id}")
    public ResponseEntity<String> deleteTenant(@PathVariable Long id) {
        tenantService.deleteTenant (id);
        return ResponseEntity.ok ("Tenant deleted successfully!");
    }
}
