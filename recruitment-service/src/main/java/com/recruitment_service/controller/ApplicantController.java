package com.recruitment_service.controller;

import com.recruitment_service.config.PermissionEvaluator;
import com.recruitment_service.dto.request.ApplicantRequest;
import com.recruitment_service.dto.response.ApplicantResponse;
import com.recruitment_service.service.ApplicantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/applicants/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Applicant")
public class ApplicantController {

    private final ApplicantService applicantService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createApplicant(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody ApplicantRequest request) {

        permissionEvaluator.addApplicantPermission();

        ApplicantResponse response = applicantService
                .createApplicant(tenantId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{recruitment-id}/get-all")
    public ResponseEntity<?> getAllApplicants(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId) {

        permissionEvaluator.getAllApplicantsPermission();

        List<ApplicantResponse> responses = applicantService
                .getAllApplicants(tenantId, recruitmentId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{applicant-id}")
    public ResponseEntity<?> getApplicantById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId) {

        permissionEvaluator.getApplicantByIdPermission();

        ApplicantResponse response = applicantService
                .getApplicantById(tenantId, applicantId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{applicant-id}")
    public ResponseEntity<?> updateApplicant(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @RequestBody ApplicantRequest request) {

        permissionEvaluator.updateApplicantPermission();

        ApplicantResponse response = applicantService
                .updateApplicant(tenantId, applicantId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{applicant-id}")
    public ResponseEntity<?> deleteApplicant(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId) {

        permissionEvaluator.deleteApplicantPermission();

        applicantService.deleteApplicant(tenantId, applicantId);
        return ResponseEntity.ok("Applicant deleted successfully");
    }
}