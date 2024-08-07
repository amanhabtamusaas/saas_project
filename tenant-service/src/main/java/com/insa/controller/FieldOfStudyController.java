package com.insa.controller;

import com.insa.dto.requestDto.FieldOfStudyRequest;
import com.insa.dto.responseDto.FieldOfStudyResponse;
import com.insa.service.FieldOfStudyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/field-of-studies/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Field Of Study")
@SecurityRequirement(name = "Keycloak")
public class FieldOfStudyController {

    private final FieldOfStudyService fieldOfStudyService;

    @PostMapping("/add")
    public ResponseEntity<FieldOfStudyResponse> createFieldOfStudy(
            @PathVariable("tenantId") Long tenantId,
            @RequestBody FieldOfStudyRequest fieldOfStudyRequest) {

        FieldOfStudyResponse fieldOfStudy = fieldOfStudyService.createFieldOfStudy(tenantId, fieldOfStudyRequest);
        return new ResponseEntity<>(fieldOfStudy, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<FieldOfStudyResponse>> getAllFieldOfStudies(
            @PathVariable("tenantId") Long tenantId) {
        List<FieldOfStudyResponse> fieldOfStudies = fieldOfStudyService.getAllFieldOfStudies(tenantId);
        return ResponseEntity.ok(fieldOfStudies);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<FieldOfStudyResponse> getFieldOfStudyById(
            @PathVariable Long id,
            @PathVariable("tenantId") Long tenantId) {

        FieldOfStudyResponse fieldOfStudy = fieldOfStudyService.getFieldOfStudyById(id, tenantId);
        return ResponseEntity.ok(fieldOfStudy);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FieldOfStudyResponse> updateFieldOfStudy(
            @PathVariable Long id,
            @PathVariable("tenantId") Long tenantId,
            @RequestBody FieldOfStudyRequest fieldOfStudyRequest) {

        FieldOfStudyResponse fieldOfStudy = fieldOfStudyService.updateFieldOfStudy(id, tenantId, fieldOfStudyRequest);
        return ResponseEntity.ok(fieldOfStudy);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFieldOfStudy(
            @PathVariable Long id,
            @PathVariable("tenantId") Long tenantId) {

        fieldOfStudyService.deleteFieldOfStudy(id, tenantId);
        return ResponseEntity.ok("Field Of Study deleted successfully!");
    }
}

