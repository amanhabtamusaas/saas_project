package com.auth_service.client;

import com.auth_service.dto.clientDto.TenantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient("ORGANIZATION-SERVICE")
public interface OrganizationServiceClient {

    @GetMapping("/api/tenants/get/{id}")
    TenantDto getTenantById(@PathVariable UUID id);
}

