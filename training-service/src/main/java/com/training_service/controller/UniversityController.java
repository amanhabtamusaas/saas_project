package com.training_service.controller;

import com.training_service.config.PermissionEvaluator;
import com.training_service.dto.request.UniversityRequest;
import com.training_service.dto.response.UniversityResponse;
import com.training_service.service.UniversityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/universities/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "University")
public class UniversityController {

    private final UniversityService universityService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addUniversity(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody UniversityRequest request) {

        permissionEvaluator.addUniversityPermission();

        UniversityResponse response = universityService
                .addUniversity(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllUniversities(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllUniversitiesPermission();

        List<UniversityResponse> responses = universityService
                .getAllUniversities(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{university-id}")
    public ResponseEntity<?> getUniversityById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("university-id") UUID universityId) {

        permissionEvaluator.getUniversityByIdPermission();

        UniversityResponse response = universityService
                .getUniversityById(tenantId, universityId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{university-id}")
    public ResponseEntity<?> updateUniversity(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("university-id") UUID universityId,
            @RequestBody UniversityRequest request) {

        permissionEvaluator.updateUniversityPermission();

        UniversityResponse response = universityService
                .updateUniversity(tenantId, universityId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{university-id}")
    public ResponseEntity<?> deleteUniversity(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("university-id") UUID universityId) {

        permissionEvaluator.deleteUniversityPermission();

        universityService.deleteUniversity(tenantId, universityId);
        return ResponseEntity.ok("University deleted successfully");
    }
}