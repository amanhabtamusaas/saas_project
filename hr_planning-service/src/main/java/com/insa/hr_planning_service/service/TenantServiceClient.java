package com.insa.hr_planning_service.service;

import com.insa.hr_planning_service.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "TENANT-SERVICE", url = "${tenant-service.base-url}")
public interface TenantServiceClient {

    @GetMapping("/api/tenants/get/{id}")
    public TenantDto getTenantById(@PathVariable Long id);

    @GetMapping("/api/departments/{tenant-id}/get/{id}")
    public DepartmentDto getDepartmentById(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId);
    @GetMapping("/api/job-registrations/{tenant-id}/get/{id}")
    public JobRegistrationDto getJobById(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId);
    @GetMapping("/api/staff-plans/{tenant-id}/get/{id}")
    public StaffPlanDto getStaffPlanById(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId);
    @GetMapping("/api/work-units/{tenantId}/get/{id}")
    public WorkUnitDto getWorkUnitById(
            @PathVariable Long id,
            @PathVariable("tenantId") Long tenantId);
}