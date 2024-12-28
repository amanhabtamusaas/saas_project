package com.employee_service.controller;

import com.employee_service.config.PermissionEvaluator;
import com.employee_service.dto.request.ExperienceRequest;
import com.employee_service.dto.response.ExperienceResponse;
import com.employee_service.service.ExperienceService;
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
@RequestMapping("/api/experiences/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Experience")
public class ExperienceController {

    private final ExperienceService experienceService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/{employee-id}/add")
    public ResponseEntity<?> addExperience(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @RequestPart("experience") ExperienceRequest experienceRequest,
            @RequestPart("document") MultipartFile file) throws IOException {

        permissionEvaluator.addExperiencePermission();

        ExperienceResponse experience = experienceService
                .addExperience(tenantId, employeeId, experienceRequest, file);
        return new ResponseEntity<>(experience, HttpStatus.CREATED);
    }

    @GetMapping("/{employee-id}/get-all")
    public ResponseEntity<?> getAllExperiences(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId) {

        permissionEvaluator.getAllExperiencesPermission();

        List<ExperienceResponse> experiences = experienceService
                .getAllExperiences(tenantId, employeeId);
        return ResponseEntity.ok(experiences);
    }

    @GetMapping("/get/employee-experiences")
    public ResponseEntity<?> getEmployeeExperiences(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("employee-id") String employeeId) {

        permissionEvaluator.getExperiencesByEmployeeIdPermission();

        List<ExperienceResponse> experiences = experienceService
                .getEmployeeExperiences(tenantId, employeeId);
        return ResponseEntity.ok(experiences);
    }

    @GetMapping("/{employee-id}/get/{experience-id}")
    public ResponseEntity<?> getExperienceById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("experience-id") UUID experienceId) {

        permissionEvaluator.getExperienceByIdPermission();

        ExperienceResponse experience = experienceService
                .getExperienceById(tenantId, employeeId, experienceId);
        return ResponseEntity.ok(experience);
    }

    @GetMapping(value = "/{employee-id}/download-document/{experience-id}")
    public ResponseEntity<?> getExperienceDocumentById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("experience-id") UUID experienceId) {

        permissionEvaluator.downloadExperienceDocumentPermission();

        ExperienceResponse experience = experienceService
                .getExperienceById(tenantId, employeeId, experienceId);
        if (experience == null || experience.getFileType() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee experience document not found");
        }
        MediaType mediaType = MediaType.valueOf(experience.getFileType());
        byte[] documentBytes = experienceService
                .getExperienceDocumentById(tenantId, employeeId, experienceId, mediaType);
        return ResponseEntity.ok().contentType(mediaType).body(documentBytes);
    }

    @PutMapping("/{employee-id}/update/{experience-id}")
    public ResponseEntity<?> updateExperience(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("experience-id") UUID experienceId,
            @RequestPart("experience") ExperienceRequest request,
            @RequestPart(value = "document", required = false) MultipartFile file) throws IOException {

        permissionEvaluator.updateExperiencePermission();

        ExperienceResponse experience = experienceService
                .updateExperience(tenantId, employeeId, experienceId, request, file);
        return ResponseEntity.ok(experience);
    }

    @DeleteMapping("/{employee-id}/delete/{experience-id}")
    public ResponseEntity<?> deleteExperience(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("experience-id") UUID experienceId) {

        permissionEvaluator.deleteExperiencePermission();

        experienceService.deleteExperience(tenantId, employeeId, experienceId);
        return ResponseEntity.ok("Experience deleted successfully");
    }
}