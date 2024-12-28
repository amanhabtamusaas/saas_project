package com.training_service.controller;

import com.training_service.config.PermissionEvaluator;
import com.training_service.dto.request.EducationOpportunityRequest;
import com.training_service.dto.response.EducationOpportunityResponse;
import com.training_service.service.EducationOpportunityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/education-opportunities/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Education Opportunity")
public class EducationOpportunityController {

    private final EducationOpportunityService educationOpportunityService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addEducationOpportunity(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody EducationOpportunityRequest request) {

        permissionEvaluator.addEducationOpportunityPermission();

        EducationOpportunityResponse response = educationOpportunityService
                .createEducationOpportunity(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllEducationOpportunities(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllEducationOpportunitiesPermission();

        List<EducationOpportunityResponse> responses = educationOpportunityService
                .getAllEducationOpportunities(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{education-id}")
    public ResponseEntity<?> getEducationOpportunityById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("education-id") UUID educationId) {

        permissionEvaluator.getEducationOpportunityByIdPermission();

        EducationOpportunityResponse response = educationOpportunityService
                .getEducationOpportunityById(tenantId, educationId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/employee-education-opportunity/{employee-id}")
    public ResponseEntity<?> getEducationOpportunityByEmployeeId(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId) {

        permissionEvaluator.getEducationOpportunitiesByEmployeeIdPermission();

        List<EducationOpportunityResponse> responses = educationOpportunityService
                .getEducationOpportunityByEmployeeId(tenantId, employeeId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/update/{education-id}")
    public ResponseEntity<?> updateEducationOpportunity(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("education-id") UUID educationId,
            @RequestBody EducationOpportunityRequest request) {

        permissionEvaluator.updateEducationOpportunityPermission();

        EducationOpportunityResponse response = educationOpportunityService
                .updateEducationOpportunity(tenantId, educationId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{education-id}")
    public ResponseEntity<?> deleteEducationOpportunity(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("education-id") UUID educationId) {

        permissionEvaluator.deleteEducationOpportunityPermission();

        educationOpportunityService.deleteEducationOpportunity(tenantId, educationId);
        return ResponseEntity.ok("Education opportunity deleted successfully");
    }
}