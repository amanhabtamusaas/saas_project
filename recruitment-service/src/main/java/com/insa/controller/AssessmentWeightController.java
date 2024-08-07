package com.insa.controller;

import com.insa.dto.request.AssessmentWeightRequest;
import com.insa.dto.response.AssessmentWeightResponse;
import com.insa.service.AssessmentWeightService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessment-weights/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Assessment Weight")
public class AssessmentWeightController {

    private final AssessmentWeightService assessmentWeightService;

    @PostMapping("/add")
    public ResponseEntity<?> createAssessmentWeight(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody AssessmentWeightRequest request) {

        try {
            AssessmentWeightResponse response = assessmentWeightService
                    .createAssessmentWeight(tenantId, request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the assessment weight: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllAssessmentWeights(
            @PathVariable("tenant-id") Long tenantId) {

        try {
            List<AssessmentWeightResponse> responses = assessmentWeightService
                    .getAllAssessmentWeights(tenantId);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the assessment weights: " + e.getMessage());
        }
    }

    @GetMapping("/get/{assessment-weight-id}")
    public ResponseEntity<?> getAssessmentWeightById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("assessment-weight-id") Long assessmentWeightId) {

        try {
            AssessmentWeightResponse response = assessmentWeightService
                    .getAssessmentWeightById(tenantId, assessmentWeightId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the assessment weight: " + e.getMessage());
        }
    }

    @GetMapping("/get/recruitment/{recruitment-id}")
    public ResponseEntity<?> getAssessmentWeightByRecruitment(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("recruitment-id") Long recruitmentId) {

        try {
            AssessmentWeightResponse response = assessmentWeightService
                    .getAssessmentWeightByRecruitment(tenantId, recruitmentId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the assessment weight: " + e.getMessage());
        }
    }

    @PutMapping("/update/{assessment-weight-id}")
    public ResponseEntity<?> updateAssessmentWeight(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("assessment-weight-id") Long assessmentWeightId,
            @RequestBody AssessmentWeightRequest request) {

        try {
            AssessmentWeightResponse response = assessmentWeightService
                    .updateAssessmentWeight(tenantId, assessmentWeightId, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the assessment weight: " + e.getMessage());
        }
    }
}
