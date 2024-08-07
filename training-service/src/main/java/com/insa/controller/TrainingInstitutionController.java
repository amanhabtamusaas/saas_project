package com.insa.controller;

import com.insa.dto.request.TrainingInstitutionRequest;
import com.insa.dto.response.TrainingCourseResponse;
import com.insa.dto.response.TrainingInstitutionResponse;
import com.insa.service.TrainingInstitutionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training-institutions/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Training Institution")
public class TrainingInstitutionController {

    private final TrainingInstitutionService trainingInstitutionService;

    @PostMapping("/add")
    public ResponseEntity<?> addTrainingInstitution(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody TrainingInstitutionRequest request) {

        try {
            TrainingInstitutionResponse response = trainingInstitutionService
                    .addTrainingInstitution(tenantId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the training institution: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllTrainingInstitution(
            @PathVariable("tenant-id") Long tenantId) {

        try {
            List<TrainingInstitutionResponse> responses = trainingInstitutionService
                    .getAllTrainingInstitutions(tenantId);
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the training institutions: " + e.getMessage());
        }
    }

    @GetMapping("/get/{institution-id}")
    public ResponseEntity<?> getTrainingInstitutionById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("institution-id") Long institutionId) {

        try {
            TrainingInstitutionResponse response = trainingInstitutionService
                    .getTrainingInstitutionById(tenantId, institutionId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the training institution: " + e.getMessage());
        }
    }

    @PutMapping("/update/{institution-id}")
    public ResponseEntity<?> updateTrainingInstitution(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("institution-id") Long institutionId,
            @RequestBody TrainingInstitutionRequest request) {

        try {
            TrainingInstitutionResponse response = trainingInstitutionService
                    .updateTrainingInstitution(tenantId, institutionId, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the training institution: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{institution-id}")
    public ResponseEntity<?> deleteTrainingInstitution(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("institution-id") Long institutionId) {

        try {
            trainingInstitutionService.deleteTrainingInstitution(tenantId, institutionId);
            return ResponseEntity.ok("Training institution deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the training institution: " + e.getMessage());
        }
    }
}
