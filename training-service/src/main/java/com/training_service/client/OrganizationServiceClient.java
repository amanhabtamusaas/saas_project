package com.training_service.client;

import com.training_service.dto.clientDto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient("ORGANIZATION-SERVICE")
public interface OrganizationServiceClient {

    @GetMapping("/api/tenants/get/{id}")
    public TenantDto getTenantById(@PathVariable UUID id);

    @GetMapping("/api/locations/{tenant-id}/get/{id}")
    public LocationDto getLocationById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);

    @GetMapping("/api/departments/{tenant-id}/get/{id}")
    public DepartmentDto getDepartmentById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);

    @GetMapping("/api/education-levels/{tenant-id}/get/{id}")
    public EducationLevelDto getEducationLevelById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);

    @GetMapping("/api/field-of-studies/{tenant-id}/get/{id}")
    public FieldOfStudyDto getFieldOfStudyById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);

    @GetMapping("/api/qualifications/{tenantId}/get/{id}")
    public QualificationDto getQualificationById(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId);
//
//    @GetMapping("/api/job-registrations/{tenant-id}/get/{id}")
//    public JobDto getJobById(
//            @PathVariable UUID id,
//            @PathVariable("tenant-id") UUID tenantId);
//
//    @GetMapping("/api/pay-grades/{tenant-id}/get/{id}")
//    public PayGradeDto getPayGradeById(
//            @PathVariable UUID id,
//            @PathVariable("tenant-id") UUID tenantId);
}

