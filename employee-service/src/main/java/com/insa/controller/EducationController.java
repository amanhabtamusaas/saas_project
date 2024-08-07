package com.insa.controller;

import com.insa.dto.request.EducationRequest;
import com.insa.dto.response.EducationResponse;
import com.insa.service.EducationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/educations/{tenant-id}")
@RequiredArgsConstructor
@Tag (name = "Education")
public class EducationController {

    private final EducationService educationService;

    @PostMapping("/{employee-id}/add")
    public ResponseEntity<?> addEducation(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @RequestPart("education") EducationRequest educationRequest,
            @RequestPart("document") MultipartFile file) {

        try {
            EducationResponse education = educationService
                    .addEducation(tenantId, employeeId, educationRequest, file);
            return new ResponseEntity<>(education, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the education: " + e.getMessage());
        }
    }

    @GetMapping("/{employee-id}/get-all")
    public ResponseEntity<?> getAllEducations(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId) {

        try {
            List<EducationResponse> educations = educationService
                    .getAllEducations(tenantId, employeeId);
            return ResponseEntity.ok(educations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the educations: " + e.getMessage());
        }
    }

    @GetMapping("/get/employee-educations")
    public ResponseEntity<?> getAllEducations(
            @PathVariable("tenant-id") Long tenantId,
            @RequestParam("employee-id") String employeeId) {

        try {
            List<EducationResponse> educations = educationService
                    .getEmployeeEducations(tenantId, employeeId);
            return ResponseEntity.ok(educations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the educations: " + e.getMessage());
        }
    }

    @GetMapping("/{employee-id}/get/{education-id}")
    public ResponseEntity<?> getEducationById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("education-id") Long educationId) {

        try {
            EducationResponse education = educationService
                    .getEducationById(tenantId, employeeId, educationId);
            return ResponseEntity.ok(education);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the education: " + e.getMessage());
        }
    }

    @GetMapping("/{employee-id}/download-document/{education-id}")
    public ResponseEntity<?> getEducationDocumentById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("education-id") Long educationId) {

        try {
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
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while downloading the document: " + e.getMessage());
        }
    }

    @PutMapping("/{employee-id}/update/{education-id}")
    public ResponseEntity<?> updateEducation(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("education-id") Long educationId,
            @RequestPart("education") EducationRequest educationRequest,
            @RequestPart(value = "document", required = false) MultipartFile file) {

        try {
            EducationResponse education = educationService
                    .updateEducation(tenantId, employeeId, educationId, educationRequest, file);
            return ResponseEntity.ok(education);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the education: " + e.getMessage());
        }
    }

    @DeleteMapping("/{employee-id}/delete/{education-id}")
    public ResponseEntity<?> deleteEducation(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("education-id") Long educationId) {

        try {
            educationService.deleteEducation(tenantId, employeeId, educationId);
            return ResponseEntity.ok("Education deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the education: " + e.getMessage());
        }
    }
}
