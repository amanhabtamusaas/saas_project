package com.recruitment_service.controller;

import com.recruitment_service.config.PermissionEvaluator;
import com.recruitment_service.dto.request.AssessmentWeightRequest;
import com.recruitment_service.dto.response.AssessmentWeightResponse;
import com.recruitment_service.service.AssessmentWeightService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/assessment-weights/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Assessment Weight")
public class AssessmentWeightController {

    private final AssessmentWeightService assessmentWeightService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createAssessmentWeight(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody AssessmentWeightRequest request) {

        permissionEvaluator.addAssessmentWeightPermission();

        AssessmentWeightResponse response = assessmentWeightService
                .createAssessmentWeight(tenantId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllAssessmentWeights(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllAssessmentWeightsPermission();

        List<AssessmentWeightResponse> responses = assessmentWeightService
                .getAllAssessmentWeights(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{assessment-weight-id}")
    public ResponseEntity<?> getAssessmentWeightById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("assessment-weight-id") UUID assessmentWeightId) {

        permissionEvaluator.getAssessmentWeightByIdPermission();

        AssessmentWeightResponse response = assessmentWeightService
                .getAssessmentWeightById(tenantId, assessmentWeightId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/recruitment/{recruitment-id}")
    public ResponseEntity<?> getAssessmentWeightByRecruitmentId(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId) {

        permissionEvaluator.getAssessmentWeightByRecruitmentIdPermission();

        AssessmentWeightResponse response = assessmentWeightService
                .getAssessmentWeightByRecruitmentId(tenantId, recruitmentId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{assessment-weight-id}")
    public ResponseEntity<?> updateAssessmentWeight(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("assessment-weight-id") UUID assessmentWeightId,
            @RequestBody AssessmentWeightRequest request) {

        permissionEvaluator.updateAssessmentWeightPermission();

        AssessmentWeightResponse response = assessmentWeightService
                .updateAssessmentWeight(tenantId, assessmentWeightId, request);
        return ResponseEntity.ok(response);
    }
}