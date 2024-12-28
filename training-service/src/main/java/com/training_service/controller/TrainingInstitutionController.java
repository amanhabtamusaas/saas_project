package com.training_service.controller;

import com.training_service.config.PermissionEvaluator;
import com.training_service.dto.request.TrainingInstitutionRequest;
import com.training_service.dto.response.TrainingInstitutionResponse;
import com.training_service.service.TrainingInstitutionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/training-institutions/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Training Institution")
public class TrainingInstitutionController {

    private final TrainingInstitutionService trainingInstitutionService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addTrainingInstitution(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody TrainingInstitutionRequest request) {

        permissionEvaluator.addInstitutionPermission();

        TrainingInstitutionResponse response = trainingInstitutionService
                .addTrainingInstitution(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllTrainingInstitutions(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllInstitutionsPermission();

        List<TrainingInstitutionResponse> responses = trainingInstitutionService
                .getAllTrainingInstitutions(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/get/{institution-id}")
    public ResponseEntity<?> getTrainingInstitutionById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("institution-id") UUID institutionId) {

        permissionEvaluator.getInstitutionByIdPermission();

        TrainingInstitutionResponse response = trainingInstitutionService
                .getTrainingInstitutionById(tenantId, institutionId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{institution-id}")
    public ResponseEntity<?> updateTrainingInstitution(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("institution-id") UUID institutionId,
            @RequestBody TrainingInstitutionRequest request) {

        permissionEvaluator.updateInstitutionPermission();

        TrainingInstitutionResponse response = trainingInstitutionService
                .updateTrainingInstitution(tenantId, institutionId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{institution-id}")
    public ResponseEntity<?> deleteTrainingInstitution(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("institution-id") UUID institutionId) {

        permissionEvaluator.deleteInstitutionPermission();

        trainingInstitutionService.deleteTrainingInstitution(tenantId, institutionId);
        return ResponseEntity.ok("Training institution deleted successfully");
    }
}