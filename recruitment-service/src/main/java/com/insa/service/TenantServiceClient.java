package com.insa.service;

import com.insa.dto.apiDto.*;
import org.springframework.cloud.openfeign.FeignClient;
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
    public JobDto getJobById(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId);

    @GetMapping("/api/pay-grades/{tenant-id}/get/{id}")
    public PayGradeDto getPayGradeById(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId);

    @GetMapping("/api/education-levels/{tenant-id}/get/{id}")
    public EducationLevelDto getEducationLevelById(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId);

    @GetMapping("/api/field-of-studies/{tenant-id}/get/{id}")
    public FieldOfStudyDto getFieldOfStudyById(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId);
}

