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
@RequestMapping("/api/applicant-references/{tenant-id}/{applicant-id}")
@RequiredArgsConstructor
@Tag(name = "Applicant Reference")
public class ReferenceController {

    private final ReferenceService referenceService;

    @PostMapping("/add")
    public ResponseEntity<?> addReference(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("applicant-id") Long applicantId,
            @RequestBody ReferenceRequest referenceRequest) {

        try {
            ReferenceResponse reference = referenceService
                    .addReference (tenantId, applicantId, referenceRequest);
            return new ResponseEntity<> (reference, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the reference: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllReferences(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("applicant-id") Long applicantId) {

        try {
            List<ReferenceResponse> references = referenceService
                    .getAllReferences (tenantId, applicantId);
            return ResponseEntity.ok (references);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the references: " + e.getMessage());
        }
    }

    @GetMapping("/get/{reference-id}")
    public ResponseEntity<?> getReferenceById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("applicant-id") Long applicantId,
            @PathVariable("reference-id") Long referenceId) {

        try {
            ReferenceResponse reference = referenceService
                    .getReferenceById (tenantId, applicantId, referenceId);
            return ResponseEntity.ok (reference);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the reference: " + e.getMessage());
        }
    }

    @PutMapping("/update/{reference-id}")
    public ResponseEntity<?> updateReference(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("applicant-id") Long applicantId,
            @PathVariable("reference-id") Long referenceId,
            @RequestBody ReferenceRequest referenceRequest) {

        try {
            ReferenceResponse reference = referenceService
                    .updateReference (tenantId, applicantId, referenceId, referenceRequest);
            return ResponseEntity.ok (reference);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the reference: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{reference-id}")
    public ResponseEntity<?> deleteReference(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("applicant-id") Long applicantId,
            @PathVariable("reference-id") Long referenceId) {

        try {
            referenceService.deleteReference (tenantId, applicantId, referenceId);
            return ResponseEntity.ok ("Reference deleted successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the reference: " + e.getMessage());
        }
    }
}

