package com.training_service.controller;

import com.training_service.config.PermissionEvaluator;
import com.training_service.dto.request.TrainingCourseRequest;
import com.training_service.dto.response.TrainingCourseResponse;
import com.training_service.service.TrainingCourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/training-courses/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Training Course")
public class TrainingCourseController {

    private final TrainingCourseService trainingCourseService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addTrainingCourse(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody TrainingCourseRequest request) {

        permissionEvaluator.addTrainingCoursePermission();

        TrainingCourseResponse response = trainingCourseService
                .addTrainingCourse(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all/{category-id}")
    public ResponseEntity<?> getAllTrainingCourses(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("category-id") UUID categoryId) {

        permissionEvaluator.getAllTrainingCoursesByCategoryIdPermission();

        List<TrainingCourseResponse> responses = trainingCourseService
                .getAllTrainingCourses(tenantId, categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/get/{course-id}")
    public ResponseEntity<?> getTrainingCourseById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("course-id") UUID courseId) {

        permissionEvaluator.getTrainingCourseByIdPermission();

        TrainingCourseResponse response = trainingCourseService
                .getTrainingCourseById(tenantId, courseId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{course-id}")
    public ResponseEntity<?> updateTrainingCourse(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("course-id") UUID courseId,
            @RequestBody TrainingCourseRequest request) {

        permissionEvaluator.updateTrainingCoursePermission();

        TrainingCourseResponse response = trainingCourseService
                .updateTrainingCourse(tenantId, courseId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{course-id}")
    public ResponseEntity<?> deleteTrainingCourse(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("course-id") UUID courseId) {

        permissionEvaluator.deleteTrainingCoursePermission();

        trainingCourseService.deleteTrainingCourse(tenantId, courseId);
        return ResponseEntity.ok("Annual training course deleted successfully");
    }
}