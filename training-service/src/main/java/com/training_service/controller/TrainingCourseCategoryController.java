package com.training_service.controller;

import com.training_service.config.PermissionEvaluator;
import com.training_service.dto.request.TrainingCourseCategoryRequest;
import com.training_service.dto.response.TrainingCourseCategoryResponse;
import com.training_service.service.TrainingCourseCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/course-categories/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Training Course Category")
public class TrainingCourseCategoryController {

    private final TrainingCourseCategoryService trainingCourseCategoryService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addCourseCategory(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody TrainingCourseCategoryRequest request) {

        permissionEvaluator.addTrainingCourseCategoryPermission();

        TrainingCourseCategoryResponse response = trainingCourseCategoryService
                .addCourseCategory(tenantId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCourseCategories(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllTrainingCourseCategoriesPermission();

        List<TrainingCourseCategoryResponse> responses = trainingCourseCategoryService
                .getAllCourseCategories(tenantId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/get/{category-id}")
    public ResponseEntity<?> getCourseCategoryById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("category-id") UUID categoryId) {

        permissionEvaluator.getTrainingCourseCategoryByIdPermission();

        TrainingCourseCategoryResponse response = trainingCourseCategoryService
                .getCourseCategoryById(tenantId, categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{category-id}")
    public ResponseEntity<?> updateCourseCategory(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("category-id") UUID courseCategoryId,
            @RequestBody TrainingCourseCategoryRequest request) {

        permissionEvaluator.updateTrainingCourseCategoryPermission();

        TrainingCourseCategoryResponse response = trainingCourseCategoryService
                .updateCourseCategory(tenantId, courseCategoryId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{category-id}")
    public ResponseEntity<?> deleteCourseCategory(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("category-id") UUID courseCategoryId) {

        permissionEvaluator.deleteTrainingCourseCategoryPermission();

        trainingCourseCategoryService.deleteCourseCategory(tenantId, courseCategoryId);
        return ResponseEntity.ok("Course Category Deleted Successfully");
    }
}