package com.insa.controller;

import com.insa.dto.request.TrainingRequest;
import com.insa.dto.response.TrainingResponse;
import com.insa.service.TrainingService;
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

@RestController
@RequestMapping("/api/trainings/{tenant-id}")
@RequiredArgsConstructor
@Tag (name = "Training")
@Slf4j
public class TrainingController {

    private final TrainingService trainingService;

    @PostMapping("/{employee-id}/add")
    public ResponseEntity<?> addTraining(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @RequestPart("training") TrainingRequest trainingRequest,
            @RequestPart("certificate") MultipartFile file) throws IOException {

        try {
            TrainingResponse training = trainingService
                    .addTraining (tenantId, employeeId, trainingRequest, file);
            return new ResponseEntity<> (training, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the training: " + e.getMessage());
        }
    }

    @GetMapping("/{employee-id}/get-all")
    public ResponseEntity<?> getAllTrainings(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId) {

        try {
            List<TrainingResponse> trainings = trainingService
                    .getAllTrainings (tenantId, employeeId);
            return ResponseEntity.ok (trainings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the trainings: " + e.getMessage());
        }
    }

    @GetMapping("/get/employee-trainings")
    public ResponseEntity<?> getEmployeeTrainings(
            @PathVariable("tenant-id") Long tenantId,
            @RequestParam("employee-id") String employeeId) {

        try {
            List<TrainingResponse> trainings = trainingService
                    .getEmployeeTrainings (tenantId, employeeId);
            return ResponseEntity.ok (trainings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the trainings: " + e.getMessage());
        }
    }

    @GetMapping("/{employee-id}/get/{training-id}")
    public ResponseEntity<?> getTrainingById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("training-id") Long trainingId) {

        try {
            TrainingResponse training = trainingService
                    .getTrainingById (tenantId, employeeId, trainingId);
            return ResponseEntity.ok (training);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the training: " + e.getMessage());
        }
    }

    @GetMapping("/{employee-id}/download-certificate/{training-id}")
    public ResponseEntity<?> getTrainingCertificateById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("training-id") Long trainingId) {

        try {
            TrainingResponse training = trainingService
                    .getTrainingById (tenantId, employeeId, trainingId);
            if (training == null || training.getFileType() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Employee training certificate not found");
            }
            MediaType mediaType = MediaType.valueOf (training.getFileType ());
            byte[] documentBytes = trainingService
                    .getTrainingCertificateById (tenantId, employeeId, trainingId, mediaType);
            return ResponseEntity.ok().contentType(mediaType).body(documentBytes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while downloading the certificate: " + e.getMessage());
        }
    }

    @PutMapping("/{employee-id}/update/{training-id}")
    public ResponseEntity<?> updateTraining(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("training-id") Long trainingId,
            @RequestPart("training") TrainingRequest trainingRequest,
            @RequestPart(value = "document", required = false) MultipartFile file) throws IOException {

        try {
            TrainingResponse training = trainingService
                    .updateTraining (tenantId, employeeId, trainingId, trainingRequest, file);
            return ResponseEntity.ok (training);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the training: " + e.getMessage());
        }
    }

    @DeleteMapping("/{employee-id}/delete/{training-id}")
    public ResponseEntity<?> deleteTraining(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("training-id") Long trainingId) {

        try {
            trainingService.deleteTraining (tenantId, employeeId, trainingId);
            return ResponseEntity.ok ("Training deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the training: " + e.getMessage());
        }
    }
}
