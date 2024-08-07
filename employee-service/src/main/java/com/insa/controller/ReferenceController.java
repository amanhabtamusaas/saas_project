package com.insa.controller;

import com.insa.dto.request.ReferenceRequest;
import com.insa.dto.response.ReferenceResponse;
import com.insa.service.ReferenceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/references/{tenant-id}")
@RequiredArgsConstructor
@Tag (name = "Reference")
public class ReferenceController {

    private final ReferenceService referenceService;

    @PostMapping("/{employee-id}/add")
    public ResponseEntity<?> addReference(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @RequestBody ReferenceRequest referenceRequest) {

        try {
            ReferenceResponse reference = referenceService
                    .addReference (tenantId, employeeId, referenceRequest);
            return new ResponseEntity<> (reference, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the reference: " + e.getMessage());
        }
    }

    @GetMapping("/{employee-id}/get-all")
    public ResponseEntity<?> getAllReferences(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId) {

        try {
            List<ReferenceResponse> references = referenceService
                    .getAllReferences (tenantId, employeeId);
            return ResponseEntity.ok (references);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the references: " + e.getMessage());
        }
    }

    @GetMapping("/get/employee-references")
    public ResponseEntity<?> getEmployeeReferences(
            @PathVariable("tenant-id") Long tenantId,
            @RequestParam("employee-id") String employeeId) {

        try {
            List<ReferenceResponse> references = referenceService
                    .getEmployeeReferences (tenantId, employeeId);
            return ResponseEntity.ok (references);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the references: " + e.getMessage());
        }
    }

    @GetMapping("/{employee-id}/get/{reference-id}")
    public ResponseEntity<?> getReferenceById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("reference-id") Long referenceId) {

        try {
            ReferenceResponse reference = referenceService
                    .getReferenceById (tenantId, employeeId, referenceId);
            return ResponseEntity.ok (reference);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the reference: " + e.getMessage());
        }
    }

    @PutMapping("/{employee-id}/update/{reference-id}")
    public ResponseEntity<?> updateReference(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("reference-id") Long referenceId,
            @RequestBody ReferenceRequest referenceRequest) {

        try {
            ReferenceResponse reference = referenceService
                    .updateReference (tenantId, employeeId, referenceId, referenceRequest);
            return ResponseEntity.ok (reference);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the reference: " + e.getMessage());
        }
    }

    @DeleteMapping("/{employee-id}/delete/{reference-id}")
    public ResponseEntity<?> deleteReference(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("reference-id") Long referenceId) {

        try {
            referenceService.deleteReference (tenantId, employeeId, referenceId);
            return ResponseEntity.ok ("Reference deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the reference: " + e.getMessage());
        }
    }
}
