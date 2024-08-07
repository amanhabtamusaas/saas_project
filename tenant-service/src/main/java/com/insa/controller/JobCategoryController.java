package com.insa.controller;

import com.insa.dto.requestDto.JobCategoryRequest;
import com.insa.dto.responseDto.JobCategoryResponse;
import com.insa.service.JobCategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-categories/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Job Category")
@SecurityRequirement(name = "Keycloak")
public class JobCategoryController {

    private final JobCategoryService jobCategoryService;

    @PostMapping("/add")
    public ResponseEntity<JobCategoryResponse> createJobCategory(
            @PathVariable("tenantId") Long tenantId,
            @RequestBody JobCategoryRequest jobCategoryRequest) {

        JobCategoryResponse jobCategory = jobCategoryService.createJobCategory(tenantId, jobCategoryRequest);
        return new ResponseEntity<>(jobCategory, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<JobCategoryResponse>> getAllJobCategories(
            @PathVariable("tenantId") Long tenantId) {
        List<JobCategoryResponse> jobCategories = jobCategoryService.getAllJobCategories(tenantId);
        return ResponseEntity.ok(jobCategories);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<JobCategoryResponse> getJobCategoryById(
            @PathVariable Long id,
            @PathVariable("tenantId") Long tenantId) {

        JobCategoryResponse jobCategory = jobCategoryService.getJobCategoryById(id, tenantId);
        return ResponseEntity.ok(jobCategory);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<JobCategoryResponse> updateJobCategory(
            @PathVariable Long id,
            @PathVariable("tenantId") Long tenantId,
            @RequestBody JobCategoryRequest jobCategoryRequest) {

        JobCategoryResponse jobCategory = jobCategoryService.updateJobCategory(id, tenantId, jobCategoryRequest);
        return ResponseEntity.ok(jobCategory);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJobCategory(
            @PathVariable Long id,
            @PathVariable("tenantId") Long tenantId) {

        jobCategoryService.deleteJobCategory(id, tenantId);
        return ResponseEntity.ok("Job Category deleted successfully!");
    }
}

