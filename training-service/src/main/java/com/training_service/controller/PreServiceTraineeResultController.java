package com.training_service.controller;

import com.training_service.config.PermissionEvaluator;
import com.training_service.dto.request.PreServiceTraineeResultRequest;
import com.training_service.dto.response.PreServiceTraineeResultResponse;
import com.training_service.service.PreServiceTraineeResultService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trainee-results/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Pre-Service Trainee Result")
public class PreServiceTraineeResultController {

    private final PreServiceTraineeResultService traineeResultService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add/{trainee-id}/{course-id}")
    public ResponseEntity<?> addTraineeResult(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("trainee-id") UUID traineeId,
            @PathVariable("course-id") UUID courseId,
            @RequestBody PreServiceTraineeResultRequest request) {

        permissionEvaluator.addPreServiceTraineeResultPermission();

        PreServiceTraineeResultResponse response = traineeResultService
                .addTraineeResult(tenantId, traineeId, courseId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get/course-results/{course-id}")
    public ResponseEntity<?> getTraineeResultsByCourseId(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("course-id") UUID courseId) {

        permissionEvaluator.getAllTraineeResultsByCourseIdPermission();

        List<PreServiceTraineeResultResponse> responses = traineeResultService
                .getTraineesResultByCourseId(tenantId, courseId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{result-id}")
    public ResponseEntity<?> getPreServiceTraineeResult(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("result-id") UUID resultId) {

        permissionEvaluator.getPreServiceTraineeResultByIdPermission();

        PreServiceTraineeResultResponse response = traineeResultService
                .getTraineeResultById(tenantId, resultId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{trainee-id}/{course-id}/{result-id}")
    public ResponseEntity<?> getTraineeCourseResultById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("trainee-id") UUID traineeId,
            @PathVariable("course-id") UUID courseId,
            @PathVariable("result-id") UUID resultId) {

        permissionEvaluator.getPreServiceTraineeResultByIdPermission();

        PreServiceTraineeResultResponse response = traineeResultService
                .getTraineeCourseResultById(tenantId, traineeId, courseId, resultId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{trainee-id}/{course-id}/{result-id}")
    public ResponseEntity<?> updatePreServiceTraineeResult(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("trainee-id") UUID traineeId,
            @PathVariable("course-id") UUID courseId,
            @PathVariable("result-id") UUID resultId,
            @RequestBody PreServiceTraineeResultRequest request) {

        permissionEvaluator.updatePreServiceTraineeResultPermission();

        PreServiceTraineeResultResponse response = traineeResultService
                .updateTraineeResult(tenantId, traineeId, courseId, resultId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{result-id}")
    public ResponseEntity<?> deletePreServiceTraineeResult(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("result-id") UUID resultId) {

        permissionEvaluator.deletePreServiceTraineeResultPermission();

        traineeResultService.deleteTraineeResult(tenantId, resultId);
        return ResponseEntity.ok("Result deleted successfully!");
    }
}