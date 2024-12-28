package com.recruitment_service.client;

import com.recruitment_service.dto.clientDto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient("ORGANIZATION-SERVICE")
public interface OrganizationServiceClient {

    @GetMapping("/api/tenants/get/{id}")
    TenantDto getTenantById(@PathVariable UUID id);

    @GetMapping("/api/departments/{tenant-id}/get/{id}")
    DepartmentDto getDepartmentById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);

    @GetMapping("/api/job-registrations/{tenant-id}/get/{id}")
    JobDto getJobById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);

    @GetMapping("/api/pay-grades/{tenant-id}/get/{id}")
    PayGradeDto getPayGradeById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);

    @GetMapping("/api/education-levels/{tenant-id}/get/{id}")
    EducationLevelDto getEducationLevelById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);

    @GetMapping("/api/field-of-studies/{tenant-id}/get/{id}")
    FieldOfStudyDto getFieldOfStudyById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);
}

