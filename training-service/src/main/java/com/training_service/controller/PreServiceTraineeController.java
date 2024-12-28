package com.training_service.controller;

import com.training_service.config.PermissionEvaluator;
import com.training_service.dto.request.PreServiceTraineeRequest;
import com.training_service.dto.response.PreServiceCourseResponse;
import com.training_service.dto.response.PreServiceTraineeResponse;
import com.training_service.service.PreServiceTraineeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/pre-service-trainees/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Pre-Service Trainee")
public class PreServiceTraineeController {

    private final PreServiceTraineeService preServiceTraineeService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createPreServiceTrainee(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestPart("trainee") PreServiceTraineeRequest request,
            @RequestPart(value = "image", required = false) MultipartFile file) throws IOException {

        permissionEvaluator.addPreServiceTraineePermission();

        PreServiceTraineeResponse response = preServiceTraineeService
                .createPreServiceTrainee(tenantId, request, file);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllPreServiceTrainees(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllPreServiceTraineesPermission();

        List<PreServiceTraineeResponse> responses = preServiceTraineeService
                .getAllPreServiceTrainees(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{trainee-id}")
    public ResponseEntity<?> getPreServiceTraineeById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("trainee-id") UUID traineeId) {

        permissionEvaluator.getPreServiceTraineeByIdPermission();

        PreServiceTraineeResponse response = preServiceTraineeService
                .getPreServiceTraineeById(tenantId, traineeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all/{year-id}")
    public ResponseEntity<?> getPreServiceTraineesByYear(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("year-id") UUID yearId) {

        permissionEvaluator.getPreServiceTraineesByBudgetYearIdPermission();

        List<PreServiceTraineeResponse> responses = preServiceTraineeService
                .getPreServiceTraineesByYear(tenantId, yearId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/download-image/{trainee-id}")
    public ResponseEntity<?> TraineeProfileImageById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("trainee-id") UUID traineeId) {

        permissionEvaluator.downloadPreServiceTraineeImagePermission();

        PreServiceTraineeResponse response = preServiceTraineeService
                .getPreServiceTraineeById(tenantId, traineeId);
        if (response == null || response.getImageType() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Trainee profile image not found");
        }
        MediaType mediaType = MediaType.valueOf(response.getImageType());
        byte[] profileImage = preServiceTraineeService
                .getTraineeProfileImageById(tenantId, traineeId, mediaType);
        if (profileImage == null || profileImage.length == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Trainee profile image not found");
        }
        return ResponseEntity.ok().contentType(mediaType).body(profileImage);
    }

    @PutMapping("/add-courses/{trainee-id}")
    public ResponseEntity<?> addCoursesToTrainee(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("trainee-id") UUID traineeId,
            @RequestBody Set<UUID> courseIds) {

        permissionEvaluator.addCoursesToPreServiceTraineePermission();

        List<PreServiceCourseResponse> response = preServiceTraineeService
                .addCoursesToTrainee(tenantId, traineeId, courseIds);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{trainee-id}")
    public ResponseEntity<?> updatePreServiceTrainee(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("trainee-id") UUID traineeId,
            @RequestPart("trainee") PreServiceTraineeRequest request,
            @RequestPart(value = "image", required = false) MultipartFile file) throws IOException {

        permissionEvaluator.updatePreServiceTraineePermission();

        PreServiceTraineeResponse response = preServiceTraineeService
                .updatePreServiceTrainee(tenantId, traineeId, request, file);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{trainee-id}")
    public ResponseEntity<?> deletePreServiceTrainee(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("trainee-id") UUID traineeId) {

        permissionEvaluator.deletePreServiceTraineePermission();

        preServiceTraineeService.deletePreServiceTrainee(tenantId, traineeId);
        return ResponseEntity.ok("Pre-service trainee deleted successfully");
    }
}