package com.recruitment_service.controller;

import com.recruitment_service.config.PermissionEvaluator;
import com.recruitment_service.dto.request.EducationRequest;
import com.recruitment_service.dto.response.EducationResponse;
import com.recruitment_service.service.EducationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/applicant-educations/{tenant-id}/{applicant-id}")
@RequiredArgsConstructor
@Tag(name = "Applicant Education")
public class EducationController {

    private final EducationService educationService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addEducation(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @RequestPart("education") EducationRequest educationRequest,
            @RequestPart("document") MultipartFile file) throws IOException {

        permissionEvaluator.addEducationPermission();

        EducationResponse education = educationService
                .addEducation(tenantId, applicantId, educationRequest, file);
        return new ResponseEntity<>(education, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllEducations(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId) {

        permissionEvaluator.getAllEducationsPermission();

        List<EducationResponse> educations = educationService
                .getAllEducations(tenantId, applicantId);
        return ResponseEntity.ok(educations);
    }

    @GetMapping("/get/{education-id}")
    public ResponseEntity<?> getEducationById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("education-id") UUID educationId) {

        permissionEvaluator.getEducationByIdPermission();

        EducationResponse education = educationService
                .getEducationById(tenantId, applicantId, educationId);
        return ResponseEntity.ok(education);
    }

    @GetMapping("/download-document/{education-id}")
    public ResponseEntity<?> getEducationDocumentById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("education-id") UUID educationId) {

        permissionEvaluator.downloadEducationDocumentPermission();

        EducationResponse education = educationService
                .getEducationById(tenantId, applicantId, educationId);
        if (education == null || education.getFileType() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Applicant education document not found");
        }
        MediaType mediaType = MediaType.valueOf(education.getFileType());
        byte[] documentBytes = educationService
                .getEducationDocumentById(tenantId, applicantId, educationId, mediaType);
        return ResponseEntity.ok().contentType(mediaType).body(documentBytes);
    }

    @PutMapping("/update/{education-id}")
    public ResponseEntity<?> updateEducation(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("education-id") UUID educationId,
            @RequestPart("education") EducationRequest educationRequest,
            @RequestPart(value = "document", required = false) MultipartFile file) throws IOException {

        permissionEvaluator.updateEducationPermission();

        EducationResponse education = educationService
                .updateEducation(tenantId, applicantId, educationId, educationRequest, file);
        return ResponseEntity.ok(education);
    }

    @DeleteMapping("/delete/{education-id}")
    public ResponseEntity<?> deleteEducation(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("education-id") UUID educationId) {

        permissionEvaluator.deleteEducationPermission();

        educationService.deleteEducation(tenantId, applicantId, educationId);
        return ResponseEntity.ok("Education deleted successfully");
    }
}