package com.organization_service.controller;

import com.organization_service.config.PermissionEvaluator;
import com.organization_service.dto.requestDto.JobRegistrationRequest;
import com.organization_service.dto.responseDto.JobRegistrationResponse;
import com.organization_service.service.JobRegistrationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/job-registrations/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Job Registration")
public class JobRegistrationController {

    private final JobRegistrationService jobRegistrationService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add-job")
    public ResponseEntity<?> registerJob(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody JobRegistrationRequest jobRegistrationRequest) {

        permissionEvaluator.addJobPermission();

        JobRegistrationResponse job = jobRegistrationService
                .registerJob(tenantId, jobRegistrationRequest);
        return new ResponseEntity<>(job, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllJobs(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllJobsPermission();

        List<JobRegistrationResponse> jobs = jobRegistrationService.getAllJobs(tenantId);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/jobs/{departmentId}")
    public ResponseEntity<?> getAllJobsByTenantAndDepartment(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("departmentId") UUID departmentId) {

        permissionEvaluator.getJobsByDepartmentIdPermission();

        List<JobRegistrationResponse> jobs = jobRegistrationService
                .getAllJobsByDepartment(tenantId, departmentId);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getJobById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getJobByIdPermission();

        JobRegistrationResponse job = jobRegistrationService.getJobById(id, tenantId);
        return ResponseEntity.ok(job);
    }

    @PutMapping("/update-job/{id}")
    public ResponseEntity<?> updateJob(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody JobRegistrationRequest jobRegistrationRequest) {

        permissionEvaluator.updateJobPermission();

        JobRegistrationResponse job = jobRegistrationService
                .updateJobs(id, tenantId, jobRegistrationRequest);
        return ResponseEntity.ok(job);
    }

    @DeleteMapping("/delete-job/{id}")
    public ResponseEntity<?> deleteJob(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.deleteJobPermission();

        jobRegistrationService.deleteJob(id, tenantId);
        return ResponseEntity.ok("Job deleted successfully!");
    }
}