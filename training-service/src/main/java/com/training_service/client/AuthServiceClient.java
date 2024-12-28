package com.training_service.client;

import com.training_service.dto.clientDto.ResourceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient("AUTH-SERVICE")
public interface AuthServiceClient {

    @GetMapping("/api/keycloak/resources/{tenantId}/get/resource")
    public ResourceDto getResourceByName(@PathVariable UUID tenantId,
                                         @RequestParam String resourceName);
}
