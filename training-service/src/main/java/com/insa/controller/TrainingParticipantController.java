package com.insa.controller;

import com.insa.dto.request.TrainingParticipantRequest;
import com.insa.dto.response.TrainingParticipantResponse;
import com.insa.service.TrainingParticipantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training-participants/{tenant-id}/{training-id}")
@RequiredArgsConstructor
@Tag(name = "Training participant")
public class TrainingParticipantController {

    private final TrainingParticipantService trainingParticipantService;

    @PostMapping("/add")
    public ResponseEntity<?> addTrainingParticipant(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("training-id") Long trainingId,
            @RequestBody TrainingParticipantRequest request) {

        try {
            TrainingParticipantResponse response = trainingParticipantService
                    .addTrainingParticipant(tenantId, trainingId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the training participant: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllTrainingParticipants(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("training-id") Long trainingId) {

        try {
            List<TrainingParticipantResponse> responses = trainingParticipantService
                    .getAllTrainingParticipants(tenantId, trainingId);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the training participants: " + e.getMessage());
        }
    }

    @GetMapping("/get/{participant-id}")
    public ResponseEntity<?> getTrainingParticipantById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("training-id") Long trainingId,
            @PathVariable("participant-id") Long participantId) {

        try {
            TrainingParticipantResponse response = trainingParticipantService
                    .getTrainingParticipantById(tenantId, trainingId, participantId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the training participant: " + e.getMessage());
        }
    }

    @PutMapping("/update/{participant-id}")
    public ResponseEntity<?> updateTrainingParticipant(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("training-id") Long trainingId,
            @PathVariable("participant-id") Long participantId,
            @RequestBody TrainingParticipantRequest request) {

        try {
            TrainingParticipantResponse response = trainingParticipantService
                    .updateTrainingParticipant(tenantId, trainingId, participantId, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the training participant: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{participant-id}")
    public ResponseEntity<?> deleteTrainingParticipant(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("training-id") Long trainingId,
            @PathVariable("participant-id") Long participantId) {

        try {
            trainingParticipantService
                    .deleteTrainingParticipant(tenantId, trainingId, participantId);
            return ResponseEntity.ok("Training participant deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the training participant: " + e.getMessage());
        }
    }
}
