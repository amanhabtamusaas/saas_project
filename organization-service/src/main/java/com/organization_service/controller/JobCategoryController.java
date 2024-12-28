package com.organization_service.controller;

import com.organization_service.config.PermissionEvaluator;
import com.organization_service.dto.requestDto.JobCategoryRequest;
import com.organization_service.dto.responseDto.JobCategoryResponse;
import com.organization_service.service.JobCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/job-categories/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Job Category")
public class JobCategoryController {

    private final JobCategoryService jobCategoryService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createJobCategory(
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody JobCategoryRequest jobCategoryRequest) {

        permissionEvaluator.addJobCategoryPermission();

        JobCategoryResponse jobCategory = jobCategoryService
                .createJobCategory(tenantId, jobCategoryRequest);
        return new ResponseEntity<>(jobCategory, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllJobCategories(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllJobCategoriesPermission();

        List<JobCategoryResponse> jobCategories = jobCategoryService
                .getAllJobCategories(tenantId);
        return ResponseEntity.ok(jobCategories);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getJobCategoryById(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getJobCategoryByIdPermission();

        JobCategoryResponse jobCategory = jobCategoryService.getJobCategoryById(id, tenantId);
        return ResponseEntity.ok(jobCategory);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateJobCategory(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody JobCategoryRequest jobCategoryRequest) {

        permissionEvaluator.updateJobCategoryPermission();

        JobCategoryResponse jobCategory = jobCategoryService
                .updateJobCategory(id, tenantId, jobCategoryRequest);
        return ResponseEntity.ok(jobCategory);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteJobCategory(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.deleteJobCategoryPermission();

        jobCategoryService.deleteJobCategory(id, tenantId);
        return ResponseEntity.ok("Job Category deleted successfully!");
    }
}