package com.employee_service.controller;

import com.employee_service.config.PermissionEvaluator;
import com.employee_service.dto.request.EducationRequest;
import com.employee_service.dto.response.EducationResponse;
import com.employee_service.service.EducationService;
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
@RequestMapping("/api/educations/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Education")
public class EducationController {

    private final EducationService educationService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/{employee-id}/add")
    public ResponseEntity<?> addEducation(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @RequestPart("education") EducationRequest educationRequest,
            @RequestPart("document") MultipartFile file) throws IOException {

        permissionEvaluator.addEducationPermission();

        EducationResponse education = educationService
                .addEducation(tenantId, employeeId, educationRequest, file);
        return new ResponseEntity<>(education, HttpStatus.CREATED);
    }

    @GetMapping("/{employee-id}/get-all")
    public ResponseEntity<?> getAllEducations(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId) {

        permissionEvaluator.getAllEducationsPermission();

        List<EducationResponse> educations = educationService
                .getAllEducations(tenantId, employeeId);
        return ResponseEntity.ok(educations);
    }

    @GetMapping("/get/employee-educations")
    public ResponseEntity<?> getAllEducationsByEmployeeId(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("employee-id") String employeeId) {

        permissionEvaluator.getEducationsByEmployeeIdPermission();

        List<EducationResponse> educations = educationService
                .getEmployeeEducations(tenantId, employeeId);
        return ResponseEntity.ok(educations);
    }

    @GetMapping("/{employee-id}/get/{education-id}")
    public ResponseEntity<?> getEducationById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("education-id") UUID educationId) {

        permissionEvaluator.getEducationByIdPermission();

        EducationResponse education = educationService
                .getEducationById(tenantId, employeeId, educationId);
        return ResponseEntity.ok(education);
    }

    @GetMapping("/{employee-id}/download-document/{education-id}")
    public ResponseEntity<?> getEducationDocumentById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("education-id") UUID educationId) {

        permissionEvaluator.downloadEducationDocumentPermission();

        EducationResponse education = educationService
                .getEducationById(tenantId, employeeId, educationId);
        if (education == null || education.getFileType() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee education document not found");
        }
        MediaType mediaType = MediaType.valueOf(education.getFileType());
        byte[] documentBytes = educationService
                .getEducationDocumentById(tenantId, employeeId, educationId, mediaType);
        return ResponseEntity.ok().contentType(mediaType).body(documentBytes);
    }

    @PutMapping("/{employee-id}/update/{education-id}")
    public ResponseEntity<?> updateEducation(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("education-id") UUID educationId,
            @RequestPart("education") EducationRequest educationRequest,
            @RequestPart(value = "document", required = false) MultipartFile file) throws IOException {

        permissionEvaluator.updateEducationPermission();

        EducationResponse education = educationService
                .updateEducation(tenantId, employeeId, educationId, educationRequest, file);
        return ResponseEntity.ok(education);
    }

    @DeleteMapping("/{employee-id}/delete/{education-id}")
    public ResponseEntity<?> deleteEducation(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("education-id") UUID educationId) {

        permissionEvaluator.deleteEducationPermission();

        educationService.deleteEducation(tenantId, employeeId, educationId);
        return ResponseEntity.ok("Education deleted successfully");
    }
}