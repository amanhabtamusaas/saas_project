package com.leave_service.client;
import com.leave_service.dto.DepartmentDto;
import com.leave_service.dto.TenantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient("ORGANIZATION-SERVICE")
public interface OrganizationServiceClient {

    @GetMapping("/api/tenants/get/{id}")
    public TenantDto getTenantById(@PathVariable UUID id);

    @GetMapping("/api/departments/{tenant-id}/get/{id}")
    public DepartmentDto getDepartmentById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);
}
