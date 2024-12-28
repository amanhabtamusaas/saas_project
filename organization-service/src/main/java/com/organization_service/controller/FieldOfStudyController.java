package com.organization_service.controller;

import com.organization_service.config.PermissionEvaluator;
import com.organization_service.dto.requestDto.FieldOfStudyRequest;
import com.organization_service.dto.responseDto.FieldOfStudyResponse;
import com.organization_service.service.FieldOfStudyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/field-of-studies/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Field Of Study")
public class FieldOfStudyController {

    private final FieldOfStudyService fieldOfStudyService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createFieldOfStudy(
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody FieldOfStudyRequest fieldOfStudyRequest) {

        permissionEvaluator.addFieldOfStudyPermission();

        FieldOfStudyResponse fieldOfStudy = fieldOfStudyService
                .createFieldOfStudy(tenantId, fieldOfStudyRequest);
        return new ResponseEntity<>(fieldOfStudy, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllFieldOfStudies(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllFieldOfStudiesPermission();

        List<FieldOfStudyResponse> fieldOfStudies = fieldOfStudyService
                .getAllFieldOfStudies(tenantId);
        return ResponseEntity.ok(fieldOfStudies);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getFieldOfStudyById(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getFieldOfStudyByIdPermission();

        FieldOfStudyResponse fieldOfStudy = fieldOfStudyService.getFieldOfStudyById(id, tenantId);
        return ResponseEntity.ok(fieldOfStudy);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFieldOfStudy(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody FieldOfStudyRequest fieldOfStudyRequest) {

        permissionEvaluator.updateFieldOfStudyPermission();

        FieldOfStudyResponse fieldOfStudy = fieldOfStudyService
                .updateFieldOfStudy(id, tenantId, fieldOfStudyRequest);
        return ResponseEntity.ok(fieldOfStudy);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFieldOfStudy(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.deleteFieldOfStudyPermission();

        fieldOfStudyService.deleteFieldOfStudy(id, tenantId);
        return ResponseEntity.ok("Field Of Study deleted successfully!");
    }
}