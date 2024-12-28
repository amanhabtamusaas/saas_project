package com.employee_service.controller;

import com.employee_service.config.PermissionEvaluator;
import com.employee_service.dto.request.ReferenceRequest;
import com.employee_service.dto.response.ReferenceResponse;
import com.employee_service.service.ReferenceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/references/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Reference")
public class ReferenceController {

    private final ReferenceService referenceService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/{employee-id}/add")
    public ResponseEntity<?> addReference(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @RequestBody ReferenceRequest referenceRequest) {

        permissionEvaluator.addReferencePermission();

        ReferenceResponse reference = referenceService
                .addReference(tenantId, employeeId, referenceRequest);
        return new ResponseEntity<>(reference, HttpStatus.CREATED);
    }

    @GetMapping("/{employee-id}/get-all")
    public ResponseEntity<?> getAllReferences(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId) {

        permissionEvaluator.getAllReferencesPermission();

        List<ReferenceResponse> references = referenceService
                .getAllReferences(tenantId, employeeId);
        return ResponseEntity.ok(references);
    }

    @GetMapping("/get/employee-references")
    public ResponseEntity<?> getEmployeeReferences(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("employee-id") String employeeId) {

        permissionEvaluator.getReferencesByEmployeeIdPermission();

        List<ReferenceResponse> references = referenceService
                .getEmployeeReferences(tenantId, employeeId);
        return ResponseEntity.ok(references);
    }

    @GetMapping("/{employee-id}/get/{reference-id}")
    public ResponseEntity<?> getReferenceById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("reference-id") UUID referenceId) {

        permissionEvaluator.getReferenceByIdPermission();

        ReferenceResponse reference = referenceService
                .getReferenceById(tenantId, employeeId, referenceId);
        return ResponseEntity.ok(reference);
    }

    @PutMapping("/{employee-id}/update/{reference-id}")
    public ResponseEntity<?> updateReference(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("reference-id") UUID referenceId,
            @RequestBody ReferenceRequest referenceRequest) {

        permissionEvaluator.updateReferencePermission();

        ReferenceResponse reference = referenceService
                .updateReference(tenantId, employeeId, referenceId, referenceRequest);
        return ResponseEntity.ok(reference);
    }

    @DeleteMapping("/{employee-id}/delete/{reference-id}")
    public ResponseEntity<?> deleteReference(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("reference-id") UUID referenceId) {

        permissionEvaluator.deleteReferencePermission();

        referenceService.deleteReference(tenantId, employeeId, referenceId);
        return ResponseEntity.ok("Reference deleted successfully");
    }
}