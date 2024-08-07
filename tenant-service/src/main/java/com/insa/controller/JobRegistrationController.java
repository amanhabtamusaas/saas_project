package com.insa.controller;

import com.insa.dto.requestDto.JobRegistrationRequest;
import com.insa.dto.responseDto.JobRegistrationResponse;

import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.JobRegistrationMapper;
import com.insa.service.JobRegistrationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/job-registrations/{tenant-id}")
@RequiredArgsConstructor
@Tag (name = "Job Registration")
@SecurityRequirement(name = "Keycloak")
public class JobRegistrationController {

    private final JobRegistrationService jobRegistrationService;
    private final JobRegistrationMapper jobRegistrationMapper;

    @PostMapping("/add-job")
    public ResponseEntity<JobRegistrationResponse> registerJob(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody JobRegistrationRequest jobRegistrationRequest) {

        JobRegistrationResponse job = jobRegistrationService
                .registerJob (tenantId, jobRegistrationRequest);
        return new ResponseEntity<> (job, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<JobRegistrationResponse>> getAllJobs(
            @PathVariable("tenant-id") Long tenantId) {

        List<JobRegistrationResponse> jobs = jobRegistrationService.getAllJobs (tenantId);
        return ResponseEntity.ok (jobs);
    }
    @GetMapping("/jobs/{departmentId}")
    public ResponseEntity<List<JobRegistrationResponse>> getAllJobsByTenantAndDepartment(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("departmentId") Long departmentId) {

            List<JobRegistrationResponse> jobs = jobRegistrationService.getAllJobsByDepartment(tenantId, departmentId);

           return ResponseEntity.ok (jobs);

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<JobRegistrationResponse> getJobById(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId) {

        JobRegistrationResponse job = jobRegistrationService.getJobById (id, tenantId);
        return ResponseEntity.ok (job);
    }

    @PutMapping("/update-job/{id}")
    public ResponseEntity<JobRegistrationResponse> updateJob(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody JobRegistrationRequest jobRegistrationRequest) {

        JobRegistrationResponse job = jobRegistrationService
                .updateJobs (id, tenantId, jobRegistrationRequest);
        return ResponseEntity.ok (job);
    }

    @DeleteMapping("/delete-job/{id}")
    public ResponseEntity<String> deleteJob(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId) {

        jobRegistrationService.deleteJob (id, tenantId);
        return ResponseEntity.ok ("Job deleted successfully!");
    }
}
