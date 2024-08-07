package com.insa.controller;

import com.insa.dto.request.PreServiceTraineeResultRequest;
import com.insa.dto.response.PreServiceTraineeResultResponse;
import com.insa.service.PreServiceTraineeResultService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainee-results/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Pre-Service Trainee Result")
public class PreServiceTraineeResultController {

    private final PreServiceTraineeResultService traineeResultService;

    @PostMapping("/add/{trainee-id}/{course-id}")
    public ResponseEntity<?> addTraineeResult(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("trainee-id") Long traineeId,
            @PathVariable("course-id") Long courseId,
            @RequestBody PreServiceTraineeResultRequest request) {

        try {
            PreServiceTraineeResultResponse response = traineeResultService
                    .addTraineeResult(tenantId, traineeId, courseId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the result: " + e.getMessage());
        }
    }

    @GetMapping("/get/course-results/{course-id}")
    public ResponseEntity<?> getTraineeResultsByCourse(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("course-id") Long courseId) {

        try {
            List<PreServiceTraineeResultResponse> responses = traineeResultService
                    .getTraineesResultByCourse(tenantId, courseId);
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the results: " + e.getMessage());
        }
    }

    @GetMapping("/get/{result-id}")
    public ResponseEntity<?> getPreServiceTraineeResult(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("result-id") Long resultId) {

        try {
            PreServiceTraineeResultResponse response = traineeResultService
                    .getTraineeResultById(tenantId, resultId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the result: " + e.getMessage());
        }
    }

    @GetMapping("/get/{trainee-id}/{course-id}/{result-id}")
    public ResponseEntity<?> getTraineeCourseResult(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("trainee-id") Long traineeId,
            @PathVariable("course-id") Long courseId,
            @PathVariable("result-id") Long resultId) {

        try {
            PreServiceTraineeResultResponse response = traineeResultService
                    .getTraineeCourseResult(tenantId, traineeId, courseId, resultId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the result: " + e.getMessage());
        }
    }

    @PutMapping("/update/{trainee-id}/{course-id}/{result-id}")
    public ResponseEntity<?> updatePreServiceTraineeResult(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("trainee-id") Long traineeId,
            @PathVariable("course-id") Long courseId,
            @PathVariable("result-id") Long resultId,
            @RequestBody PreServiceTraineeResultRequest request) {

        try {
            PreServiceTraineeResultResponse response = traineeResultService
                    .updateTraineeResult(tenantId,traineeId,  courseId, resultId, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the result: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{result-id}")
    public ResponseEntity<?> deletePreServiceTraineeResult(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("result-id") Long resultId) {

        try {
            traineeResultService.deleteTraineeResult(tenantId, resultId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Result deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the result: " + e.getMessage());
        }
    }
}
