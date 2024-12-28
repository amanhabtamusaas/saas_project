package com.hr_planning_service.client;

import com.hr_planning_service.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "TENANT-SERVICE", url = "${tenant-service.base-url}")
public interface OrganizationServiceClient {

    @GetMapping("/api/tenants/get/{id}")
    public TenantDto getTenantById(@PathVariable UUID id);

    @GetMapping("/api/departments/{tenant-id}/get/{id}")
    public DepartmentDto getDepartmentById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);
    @GetMapping("/api/job-registrations/{tenant-id}/get/{id}")
    public JobRegistrationDto getJobById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);
    @GetMapping("/api/staff-plans/{tenant-id}/get/{id}")
    public StaffPlanDto getStaffPlanById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);
    @GetMapping("/api/work-units/{tenantId}/get/{id}")
    public WorkUnitDto getWorkUnitById(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId);
}