package com.insa.controller;

import com.insa.dto.request.EducationOpportunityRequest;
import com.insa.dto.response.EducationOpportunityResponse;
import com.insa.service.EducationOpportunityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/education-opportunities/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Education Opportunity")
public class EducationOpportunityController {

    private final EducationOpportunityService educationOpportunityService;

    @PostMapping("/add")
    public ResponseEntity<?> addEducationOpportunity(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody EducationOpportunityRequest request) {

        try {
            EducationOpportunityResponse response = educationOpportunityService
                    .createEducationOpportunity(tenantId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the education opportunity: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllEducationOpportunities(
            @PathVariable("tenant-id") Long tenantId) {

        try {
            List<EducationOpportunityResponse> responses = educationOpportunityService
                    .getAllEducationOpportunities(tenantId);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the education opportunities: " + e.getMessage());
        }
    }

    @GetMapping("/get/{education-id}")
    public ResponseEntity<?> getEducationOpportunityById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("education-id") Long educationId) {

        try {
            EducationOpportunityResponse response = educationOpportunityService
                    .getEducationOpportunityById(tenantId, educationId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the education opportunity: " + e.getMessage());
        }
    }

    @GetMapping("/get/employee-education-opportunity/{employee-id}")
    public ResponseEntity<?> getEducationOpportunityByEmployeeId(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId) {

        try {
            List<EducationOpportunityResponse> responses = educationOpportunityService
                    .getEducationOpportunityByEmployeeId(tenantId, employeeId);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the education opportunities: " + e.getMessage());
        }
    }

    @PutMapping("/update/{education-id}")
    public ResponseEntity<?> updateEducationOpportunity(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("education-id") Long educationId,
            @RequestBody EducationOpportunityRequest request) {

        try {
            EducationOpportunityResponse response = educationOpportunityService
                    .updateEducationOpportunity(tenantId, educationId, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the education opportunity: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{education-id}")
    public ResponseEntity<?> deleteEducationOpportunity(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("education-id") Long educationId) {

        try {
            educationOpportunityService.deleteEducationOpportunity(tenantId, educationId);
            return ResponseEntity.ok("Annual education opportunity deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the education opportunity: " + e.getMessage());
        }
    }
}
