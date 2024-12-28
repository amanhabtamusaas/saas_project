package com.organization_service.client;

import com.organization_service.dto.ResourceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient("AUTH-SERVICE")
public interface AuthServiceClient {

    @PostMapping("/api/keycloak/resources/{tenantId}/add")
    void addResource(@PathVariable UUID tenantId);

    @GetMapping("/api/keycloak/resources/{tenantId}/get/resource")
    ResourceDto getResourceByName(@PathVariable UUID tenantId,
                                  @RequestParam String resourceName);

    @DeleteMapping("/api/keycloak/resources/{tenantId}/delete-all")
    void deleteAllResource(@PathVariable UUID tenantId);

    @PostMapping("/api/keycloak/roles/{tenantId}/add/admin-and-default")
    void addDefaultAndAdminRole(@PathVariable UUID tenantId);

    @PostMapping("/api/keycloak/users/{tenantId}/add/admin")
    void addAdminUser(@PathVariable UUID tenantId);
}