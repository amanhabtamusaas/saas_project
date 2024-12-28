package com.training_service.controller;

import com.training_service.config.PermissionEvaluator;
import com.training_service.dto.request.PreServiceCourseRequest;
import com.training_service.dto.response.PreServiceCourseResponse;
import com.training_service.service.PreServiceCourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pre-service-courses/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Pre-Service Course")
public class PreServiceCourseController {

    private final PreServiceCourseService preServiceCourseService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addPreServiceCourse(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody PreServiceCourseRequest request) {

        permissionEvaluator.addPreServiceCoursePermission();

        PreServiceCourseResponse response = preServiceCourseService
                .addPreServiceCourse(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllPreServiceCourses(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllPreServiceCoursesPermission();

        List<PreServiceCourseResponse> responses = preServiceCourseService
                .getAllPreServiceCourses(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{course-id}")
    public ResponseEntity<?> getPreServiceCourseById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("course-id") UUID courseId) {

        permissionEvaluator.getPreServiceCourseByIdPermission();

        PreServiceCourseResponse response = preServiceCourseService
                .getPreServiceCourseById(tenantId, courseId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/trainee-courses/{trainee-id}")
    public ResponseEntity<?> getPreServiceCoursesByTraineeId(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("trainee-id") UUID traineeId) {

        permissionEvaluator.getPreServiceCoursesByTraineeIdPermission();

        List<PreServiceCourseResponse> responses = preServiceCourseService
                .getCoursesByTraineeId(tenantId, traineeId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/course-type/{course-type-id}")
    public ResponseEntity<?> getPreServiceCoursesByCourseTypeId(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("course-type-id") UUID courseTypeId) {

        permissionEvaluator.getPreServiceCoursesByCourseTypeIdPermission();

        List<PreServiceCourseResponse> responses = preServiceCourseService
                .getPreServiceCoursesByCourseTypeId(tenantId, courseTypeId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/update/{course-id}")
    public ResponseEntity<?> updatePreServiceCourse(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("course-id") UUID courseId,
            @RequestBody PreServiceCourseRequest request) {

        permissionEvaluator.updatePreServiceCoursePermission();

        PreServiceCourseResponse response = preServiceCourseService
                .updatePreServiceCourse(tenantId, courseId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/remove-trainee-course/{trainee-id}/{course-id}")
    public ResponseEntity<?> removeCoursesFromTrainee(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("trainee-id") UUID traineeId,
            @PathVariable("course-id") UUID courseId) {

        permissionEvaluator.removeCourseByTraineeIdPermission();

        preServiceCourseService.removeCourseFromTrainee(tenantId, traineeId, courseId);
        return ResponseEntity.ok("Course removed successfully!");
    }

    @DeleteMapping("/delete/{course-id}")
    public ResponseEntity<?> deletePreServiceCourse(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("course-id") UUID courseId) {

        permissionEvaluator.deletePreServiceCoursePermission();

        preServiceCourseService.deletePreServiceCourse(tenantId, courseId);
        return ResponseEntity.ok("Pre-service course deleted successfully!");
    }
}