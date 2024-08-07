package com.insa.controller;

import com.insa.dto.requestDto.QualificationRequest;
import com.insa.dto.responseDto.QualificationResponse;
import com.insa.service.QualificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qualifications/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Qualification")
@SecurityRequirement(name = "Keycloak")
public class QualificationController {

    private final QualificationService qualificationService;

    @PostMapping("/add")
    public ResponseEntity<QualificationResponse> createQualification(
            @PathVariable("tenantId") Long tenantId,
            @RequestBody QualificationRequest qualificationRequest) {

        QualificationResponse qualification = qualificationService.createQualification(tenantId, qualificationRequest);
        return new ResponseEntity<>(qualification, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<QualificationResponse>> getAllQualifications(
            @PathVariable("tenantId") Long tenantId) {
        List<QualificationResponse> qualifications = qualificationService.getAllQualifications(tenantId);
        return ResponseEntity.ok(qualifications);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<QualificationResponse> getQualificationById(
            @PathVariable Long id,
            @PathVariable("tenantId") Long tenantId) {

        QualificationResponse qualification = qualificationService.getQualificationById(id, tenantId);
        return ResponseEntity.ok(qualification);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<QualificationResponse> updateQualification(
            @PathVariable Long id,
            @PathVariable("tenantId") Long tenantId,
            @RequestBody QualificationRequest qualificationRequest) {

        QualificationResponse qualification = qualificationService.updateQualification(id, tenantId, qualificationRequest);
        return ResponseEntity.ok(qualification);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQualification(
            @PathVariable Long id,
            @PathVariable("tenantId") Long tenantId) {

        qualificationService.deleteQualification(id, tenantId);
        return ResponseEntity.ok("Qualification deleted successfully!");
    }
}

