package com.employee_service.controller;

import com.employee_service.config.PermissionEvaluator;
import com.employee_service.dto.request.TrainingRequest;
import com.employee_service.dto.response.TrainingResponse;
import com.employee_service.service.TrainingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trainings/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Training")
@Slf4j
public class TrainingController {

    private final TrainingService trainingService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/{employee-id}/add")
    public ResponseEntity<?> addTraining(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @RequestPart("training") TrainingRequest trainingRequest,
            @RequestPart("certificate") MultipartFile file) throws IOException {

        permissionEvaluator.addTrainingPermission();

        TrainingResponse training = trainingService
                .addTraining(tenantId, employeeId, trainingRequest, file);
        return new ResponseEntity<>(training, HttpStatus.CREATED);
    }

    @GetMapping("/{employee-id}/get-all")
    public ResponseEntity<?> getAllTrainings(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId) {

        permissionEvaluator.getAllTrainingsPermission();

        List<TrainingResponse> trainings = trainingService
                .getAllTrainings(tenantId, employeeId);
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/get/employee-trainings")
    public ResponseEntity<?> getEmployeeTrainings(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("employee-id") String employeeId) {

        permissionEvaluator.getTrainingsByEmployeeIdPermission();

        List<TrainingResponse> trainings = trainingService
                .getEmployeeTrainings(tenantId, employeeId);
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/{employee-id}/get/{training-id}")
    public ResponseEntity<?> getTrainingById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("training-id") UUID trainingId) {

        permissionEvaluator.getTrainingByIdPermission();

        TrainingResponse training = trainingService
                .getTrainingById(tenantId, employeeId, trainingId);
        return ResponseEntity.ok(training);
    }

    @GetMapping("/{employee-id}/download-certificate/{training-id}")
    public ResponseEntity<?> getTrainingCertificateById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("training-id") UUID trainingId) {

        permissionEvaluator.downloadTrainingCertificatePermission();

        TrainingResponse training = trainingService
                .getTrainingById(tenantId, employeeId, trainingId);
        if (training == null || training.getFileType() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee training certificate not found");
        }
        MediaType mediaType = MediaType.valueOf(training.getFileType());
        byte[] documentBytes = trainingService
                .getTrainingCertificateById(tenantId, employeeId, trainingId, mediaType);
        return ResponseEntity.ok().contentType(mediaType).body(documentBytes);
    }

    @PutMapping("/{employee-id}/update/{training-id}")
    public ResponseEntity<?> updateTraining(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("training-id") UUID trainingId,
            @RequestPart("training") TrainingRequest trainingRequest,
            @RequestPart(value = "document", required = false) MultipartFile file) throws IOException {

        permissionEvaluator.updateTrainingPermission();

        TrainingResponse training = trainingService
                .updateTraining(tenantId, employeeId, trainingId, trainingRequest, file);
        return ResponseEntity.ok(training);
    }

    @DeleteMapping("/{employee-id}/delete/{training-id}")
    public ResponseEntity<?> deleteTraining(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("training-id") UUID trainingId) {

        permissionEvaluator.deleteTrainingPermission();

        trainingService.deleteTraining(tenantId, employeeId, trainingId);
        return ResponseEntity.ok("Training deleted successfully");
    }
}