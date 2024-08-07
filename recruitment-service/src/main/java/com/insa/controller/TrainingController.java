package com.insa.controller;

import com.insa.dto.request.TrainingRequest;
import com.insa.dto.response.TrainingResponse;
import com.insa.service.TrainingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/applicant-trainings/{tenant-id}/{applicant-id}")
@RequiredArgsConstructor
@Tag(name = "Applicant Training")
public class TrainingController {

    private final TrainingService trainingService;

    @PostMapping("/add")
    public ResponseEntity<?> addTraining(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("applicant-id") Long applicantId,
            @RequestPart("training") TrainingRequest request,
            @RequestPart("certificate")MultipartFile file) {

        try {
            TrainingResponse training = trainingService
                    .addTraining (tenantId, applicantId, request, file);
            return new ResponseEntity<> (training, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the training: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllTrainings(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("applicant-id") Long applicantId) {

        try {
            List<TrainingResponse> trainings = trainingService
                    .getAllTrainings (tenantId, applicantId);
            return ResponseEntity.ok (trainings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the trainings: " + e.getMessage());
        }
    }

    @GetMapping("/get/{training-id}")
    public ResponseEntity<?> getTrainingById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("applicant-id") Long applicantId,
            @PathVariable("training-id") Long trainingId) {

        try {
            TrainingResponse training = trainingService
                    .getTrainingById (tenantId, applicantId, trainingId);
            return ResponseEntity.ok (training);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the training: " + e.getMessage());
        }
    }

    @GetMapping("/download-certificate/{training-id}")
    public ResponseEntity<?> getTrainingCertificateById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("applicant-id") Long applicantId,
            @PathVariable("training-id") Long trainingId) {

        try {
            TrainingResponse training = trainingService
                    .getTrainingById (tenantId, applicantId, trainingId);
            if (training == null || training.getCertificateType() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Applicant training certificate not found");
            }
            MediaType mediaType = MediaType.valueOf (training.getCertificateType ());
            byte[] documentBytes = trainingService
                    .getTrainingCertificateById (tenantId, applicantId, trainingId, mediaType);
            return ResponseEntity.ok().contentType(mediaType).body(documentBytes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while downloading the certificate: " + e.getMessage());
        }
    }

    @PutMapping("/update/{training-id}")
    public ResponseEntity<?> updateTraining(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("applicant-id") Long applicantId,
            @PathVariable("training-id") Long trainingId,
            @RequestPart(value = "training", required = false) TrainingRequest request,
            @RequestPart("certificate")MultipartFile file) {

        try {
            TrainingResponse training = trainingService
                    .updateTraining (tenantId, applicantId, trainingId, request, file);
            return ResponseEntity.ok (training);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the training: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{training-id}")
    public ResponseEntity<?> deleteTraining(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("applicant-id") Long applicantId,
            @PathVariable("training-id") Long trainingId) {

        try {
            trainingService.deleteTraining (tenantId, applicantId, trainingId);
            return ResponseEntity.ok ("Training deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the training: " + e.getMessage());
        }
    }
}
