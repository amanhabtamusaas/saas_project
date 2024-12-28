package com.recruitment_service.controller;

import com.recruitment_service.config.PermissionEvaluator;
import com.recruitment_service.dto.request.ReferenceRequest;
import com.recruitment_service.dto.response.ReferenceResponse;
import com.recruitment_service.service.ReferenceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/applicant-references/{tenant-id}/{applicant-id}")
@RequiredArgsConstructor
@Tag(name = "Applicant Reference")
public class ReferenceController {

    private final ReferenceService referenceService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addReference(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @RequestBody ReferenceRequest referenceRequest) {

        permissionEvaluator.addReferencePermission();

        ReferenceResponse reference = referenceService
                .addReference(tenantId, applicantId, referenceRequest);
        return new ResponseEntity<>(reference, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllReferences(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId) {

        permissionEvaluator.getAllReferencesPermission();

        List<ReferenceResponse> references = referenceService
                .getAllReferences(tenantId, applicantId);
        return ResponseEntity.ok(references);
    }

    @GetMapping("/get/{reference-id}")
    public ResponseEntity<?> getReferenceById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("reference-id") UUID referenceId) {

        permissionEvaluator.getReferenceByIdPermission();

        ReferenceResponse reference = referenceService
                .getReferenceById(tenantId, applicantId, referenceId);
        return ResponseEntity.ok(reference);
    }

    @PutMapping("/update/{reference-id}")
    public ResponseEntity<?> updateReference(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("reference-id") UUID referenceId,
            @RequestBody ReferenceRequest referenceRequest) {

        permissionEvaluator.updateReferencePermission();

        ReferenceResponse reference = referenceService
                .updateReference(tenantId, applicantId, referenceId, referenceRequest);
        return ResponseEntity.ok(reference);
    }

    @DeleteMapping("/delete/{reference-id}")
    public ResponseEntity<?> deleteReference(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("reference-id") UUID referenceId) {

        permissionEvaluator.deleteReferencePermission();

        referenceService.deleteReference(tenantId, applicantId, referenceId);
        return ResponseEntity.ok("Reference deleted successfully");
    }
}