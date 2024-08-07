package com.insa.controller;

import com.insa.dto.request.TrainingApproveRequest;
import com.insa.dto.request.TrainingRequest;
import com.insa.dto.response.TrainingResponse;
import com.insa.enums.TrainingStatus;
import com.insa.service.TrainingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainings/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Training")
public class TrainingController {

    private final TrainingService trainingService;

    @PostMapping("/add")
    public ResponseEntity<?> addTraining(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody TrainingRequest request) {

        try {
            TrainingResponse response = trainingService
                    .addTraining(tenantId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the training: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllTrainings(
            @PathVariable("tenant-id") Long tenantId) {

        try {
            List<TrainingResponse> responses = trainingService
                    .getAllTrainings(tenantId);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the trainings: " + e.getMessage());
        }
    }

    @GetMapping("/get/{training-id}")
    public ResponseEntity<?> getTrainingById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("training-id") Long trainingId) {

        try {
            TrainingResponse response = trainingService
                    .getTrainingById(tenantId, trainingId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the training: " + e.getMessage());
        }
    }

    @GetMapping("/get/status")
    public ResponseEntity<?> getTrainingsByStatus(
            @PathVariable("tenant-id") Long tenantId,
            @RequestParam("training-status") String trainingStatus) {

        try {
            List<TrainingResponse> responses = trainingService
                    .getTrainingsByStatus(tenantId, trainingStatus);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the trainings: " + e.getMessage());
        }
    }

    @PutMapping("/update/{training-id}")
    public ResponseEntity<?> updateTraining(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("training-id") Long trainingId,
            @RequestBody TrainingRequest request) {

        try {
            TrainingResponse response = trainingService
                    .updateTraining(tenantId, trainingId, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the training: " + e.getMessage());
        }
    }

    @PutMapping("/approve/{training-id}")
    public ResponseEntity<?> approveTraining(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("training-id") Long trainingId,
            @RequestBody TrainingApproveRequest request) {

        try {
            TrainingResponse response = trainingService
                    .approveTraining(tenantId, trainingId, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while approving the training: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{training-id}")
    public ResponseEntity<?> deleteTraining(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("training-id") Long trainingId) {

        try {
            trainingService.deleteTraining(tenantId, trainingId);
            return ResponseEntity.ok("Training deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the training: " + e.getMessage());
        }
    }
}
