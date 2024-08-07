package com.insa.controller;


import com.insa.dto.request.PreServiceCourseRequest;
import com.insa.dto.response.PreServiceCourseResponse;
import com.insa.service.PreServiceCourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pre-service-courses/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Pre-Service Course")
public class PreServiceCourseController {

    private final PreServiceCourseService preServiceCourseService;

    @PostMapping("/add")
    public ResponseEntity<?> addPreServiceCourse(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody PreServiceCourseRequest request) {

        try {
            PreServiceCourseResponse response = preServiceCourseService
                    .addPreServiceCourse(tenantId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the pre-service course: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllPreServiceCourses(
            @PathVariable("tenant-id") Long tenantId) {

        try {
            List<PreServiceCourseResponse> responses = preServiceCourseService
                    .getAllPreServiceCourses(tenantId);
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the pre-service courses: " + e.getMessage());
        }
    }

    @GetMapping("/get/{course-id}")
    public ResponseEntity<?> getPreServiceCourse(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("course-id") Long courseId) {

        try {
            PreServiceCourseResponse response = preServiceCourseService
                    .getPreServiceCourse(tenantId, courseId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the pre-service course: " + e.getMessage());
        }
    }

    @GetMapping("/get/trainee-courses/{trainee-id}")
    public ResponseEntity<?> getPreServiceCoursesByTrainee(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("trainee-id") Long traineeId) {

        try {
            List<PreServiceCourseResponse> responses = preServiceCourseService
                    .getCoursesByTrainee(tenantId, traineeId);
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the pre-service courses: " + e.getMessage());
        }
    }

    @GetMapping("/get/course-type{course-type-id}")
    public ResponseEntity<?> getPreServiceCoursesByCourseType(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("course-type-id") Long courseTypeId) {

        try {
            List<PreServiceCourseResponse> responses = preServiceCourseService
                    .getPreServiceCoursesByCourseType(tenantId, courseTypeId);
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the pre-service courses: " + e.getMessage());
        }
    }

    @PutMapping("/update/{course-id}")
    public ResponseEntity<?> updatePreServiceCourse(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("course-id") Long courseId,
            @RequestBody PreServiceCourseRequest request) {

        try {
            PreServiceCourseResponse response = preServiceCourseService
                    .updatePreServiceCourse(tenantId, courseId, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the pre-service course: " + e.getMessage());
        }
    }

    @DeleteMapping("/remove-trainee-course/{trainee-id}/{course-id}")
    public ResponseEntity<?> removeCoursesFromTrainee(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("trainee-id") Long traineeId,
            @PathVariable("course-id") Long courseId) {

        try {
            preServiceCourseService.removeCourseFromTrainee(tenantId, traineeId, courseId);
            return ResponseEntity.ok("Course removed successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while removing the pre-service course: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{course-id}")
    public ResponseEntity<?> deletePreServiceCourse(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("course-id") Long courseId) {

        try {
            preServiceCourseService.deletePreServiceCourse(tenantId, courseId);
            return ResponseEntity.ok("Pre-service course deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the pre-service course: " + e.getMessage());
        }
    }
}
