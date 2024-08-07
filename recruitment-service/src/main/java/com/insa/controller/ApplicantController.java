package com.insa.controller;


import com.insa.dto.request.ApplicantRequest;
import com.insa.dto.response.ApplicantResponse;
import com.insa.service.ApplicantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/applicants/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Applicant")
public class ApplicantController {

    private final ApplicantService applicantService;

    @PostMapping("/add")
    public ResponseEntity<?> createApplicant(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody ApplicantRequest request) {

        try {
            ApplicantResponse response = applicantService
                    .createApplicant(tenantId, request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the applicant: " + e.getMessage());
        }
    }

    @GetMapping("/{recruitment-id}/get-all")
    public ResponseEntity<?> getAllApplicants(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("recruitment-id") Long recruitmentId) {

        try {
            List<ApplicantResponse> responses = applicantService
                    .getAllApplicants(tenantId, recruitmentId);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the applicants: " + e.getMessage());
        }
    }

    @GetMapping("/get/{applicant-id}")
    public ResponseEntity<?> getApplicantById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("applicant-id") Long applicantId) {

        try {
            ApplicantResponse response = applicantService
                    .getApplicantById(tenantId, applicantId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the applicant: " + e.getMessage());
        }
    }

    @PutMapping("/update/{applicant-id}")
    public ResponseEntity<?> updateApplicant(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("applicant-id") Long applicantId,
            @RequestBody ApplicantRequest request) {

        try {
            ApplicantResponse response = applicantService
                    .updateApplicant(tenantId, applicantId, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the applicant: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{applicant-id}")
    public ResponseEntity<?> deleteApplicant(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("applicant-id") Long applicantId) {

        try {
            applicantService.deleteApplicant(tenantId, applicantId);
            return ResponseEntity.ok("Applicant deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the applicant: " + e.getMessage());
        }
    }
}
