package com.insa.controller;

import com.insa.dto.request.TrainingCourseCategoryRequest;
import com.insa.dto.response.TrainingCourseCategoryResponse;
import com.insa.service.TrainingCourseCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course-categories/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Training Course Category")
public class TrainingCourseCategoryController {

    private final TrainingCourseCategoryService trainingCourseCategoryService;

    @PostMapping("/add")
    public ResponseEntity<?> addCourseCategory(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody TrainingCourseCategoryRequest request) {

        try {
            TrainingCourseCategoryResponse response = trainingCourseCategoryService
                    .addCourseCategory(tenantId, request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the course category: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCourseCategories(
            @PathVariable("tenant-id") Long tenantId) {

        try {
            List<TrainingCourseCategoryResponse> responses = trainingCourseCategoryService
                    .getAllCourseCategories(tenantId);
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the course categories: " + e.getMessage());
        }
    }

    @GetMapping("/get/{category-id}")
    public ResponseEntity<?> getCourseCategoryById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("category-id") Long categoryId) {

        try {
            TrainingCourseCategoryResponse response = trainingCourseCategoryService
                    .getCourseCategoryById(tenantId, categoryId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the course category: " + e.getMessage());
        }
    }

    @PutMapping("/update/{category-id}")
    public ResponseEntity<?> updateCourseCategory(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("category-id") Long courseCategoryId,
            @RequestBody TrainingCourseCategoryRequest request) {

        try {
            TrainingCourseCategoryResponse response = trainingCourseCategoryService
                    .updateCourseCategory(tenantId, courseCategoryId, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the course category: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{category-id}")
    public ResponseEntity<?> deleteCourseCategory(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("category-id") Long courseCategoryId) {

        try {
            trainingCourseCategoryService.deleteCourseCategory(tenantId, courseCategoryId);
            return ResponseEntity.ok("Course Category Deleted Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the course category: " + e.getMessage());
        }
    }
}
