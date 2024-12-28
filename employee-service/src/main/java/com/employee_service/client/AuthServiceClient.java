package com.employee_service.client;

import com.employee_service.dto.clientDto.ResourceDto;
import com.employee_service.dto.clientDto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient("AUTH-SERVICE")
public interface AuthServiceClient {

    @GetMapping("/api/keycloak/resources/{tenantId}/get-all")
    List<ResourceDto> getAllResources(@PathVariable UUID tenantId);

    @GetMapping("/api/keycloak/resources/{tenantId}/get/resource")
    ResourceDto getResourceByName(@PathVariable UUID tenantId,
                                  @RequestParam String resourceName);

    @PostMapping("/api/keycloak/users/{tenantId}/add")
    UserDto createUser(@PathVariable UUID tenantId,
                       @RequestParam String employeeId);

    @PutMapping("/api/keycloak/users/{tenantId}/update/{user-id}/{employeeId}")
    UserDto updateUser(@PathVariable UUID tenantId,
                                 @PathVariable("user-id") String userId,
                                 @PathVariable UUID employeeId);

    @GetMapping("/api/keycloak/users/{tenantId}/get/username")
    UserDto getUserByUsername(@PathVariable UUID tenantId,
                              @RequestParam String username);

    @DeleteMapping("/api/keycloak/users/{tenantId}/delete/{user-id}")
    String deleteUser(@PathVariable UUID tenantId,
                      @PathVariable("user-id") String userId);
}
