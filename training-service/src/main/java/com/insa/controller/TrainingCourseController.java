package com.insa.controller;

import com.insa.dto.request.TrainingCourseRequest;
import com.insa.dto.response.TrainingCourseResponse;
import com.insa.service.TrainingCourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training-courses/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Training Course")
public class TrainingCourseController {

    private final TrainingCourseService trainingCourseService;

    @PostMapping("/add")
    public ResponseEntity<?> addTrainingCourse(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody TrainingCourseRequest request) {

        try {
            TrainingCourseResponse response = trainingCourseService
                    .addTrainingCourse(tenantId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the training course: " + e.getMessage());
        }
    }

    @GetMapping("/get-all/{category-id}")
    public ResponseEntity<?> getAllTrainingCourses(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("category-id") Long categoryId) {

        try {
            List<TrainingCourseResponse> responses = trainingCourseService
                    .getAllTrainingCourses(tenantId, categoryId);
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the training courses: " + e.getMessage());
        }
    }

    @GetMapping("/get/{course-id}")
    public ResponseEntity<?> getTrainingCourseById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("course-id") Long courseId) {

        try {
            TrainingCourseResponse response = trainingCourseService
                    .getTrainingCourseById(tenantId, courseId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the training course: " + e.getMessage());
        }
    }

    @PutMapping("/update/{course-id}")
    public ResponseEntity<?> updateTrainingCourse(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("course-id") Long courseId,
            @RequestBody TrainingCourseRequest request) {

        try {
            TrainingCourseResponse response = trainingCourseService
                    .updateTrainingCourse(tenantId, courseId, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the training course: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{course-id}")
    public ResponseEntity<?> deleteTrainingCourse(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("course-id") Long courseId) {

        try {
            trainingCourseService.deleteTrainingCourse(tenantId, courseId);
            return ResponseEntity.ok("AnnualTraining course deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the training course: " + e.getMessage());
        }
    }
}
