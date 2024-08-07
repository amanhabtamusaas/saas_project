package com.insa.controller;

import com.insa.dto.request.PreServiceTraineeRequest;
import com.insa.dto.response.PreServiceCourseResponse;
import com.insa.dto.response.PreServiceTraineeResponse;
import com.insa.service.PreServiceTraineeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/pre-service-trainees/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Pre-Service Trainee")
public class PreServiceTraineeController {

    private final PreServiceTraineeService preServiceTraineeService;

    @PostMapping("/add")
    public ResponseEntity<?> createPreServiceTrainee(
            @PathVariable("tenant-id") Long tenantId,
            @RequestPart("trainee") PreServiceTraineeRequest request,
            @RequestPart(value = "image", required = false ) MultipartFile file) {

        try {
            PreServiceTraineeResponse response = preServiceTraineeService
                    .createPreServiceTrainee(tenantId, request, file);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the pre-service trainee: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllPreServiceTrainees(
            @PathVariable("tenant-id") Long tenantId) {

        try {
            List<PreServiceTraineeResponse> responses = preServiceTraineeService
                    .getAllPreServiceTrainees(tenantId);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the pre-service trainees: " + e.getMessage());
        }
    }

    @GetMapping("/get/{trainee-id}")
    public ResponseEntity<?> getPreServiceTraineeById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("trainee-id") Long traineeId) {

        try {
            PreServiceTraineeResponse response = preServiceTraineeService
                    .getPreServiceTraineeById(tenantId, traineeId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the pre-service trainee: " + e.getMessage());
        }
    }

    @GetMapping("/get-all/{year-id}")
    public ResponseEntity<?> getPreServiceTraineesByYear(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("year-id") Long yearId) {

        try {
            List<PreServiceTraineeResponse> responses = preServiceTraineeService
                    .getPreServiceTraineesByYear(tenantId, yearId);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the pre-service trainees: " + e.getMessage());
        }
    }

    @GetMapping("/download-image/{trainee-id}")
    public ResponseEntity<?> getTraineeProfileImageById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("trainee-id") Long traineeId) {

        try {
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
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while downloading the profile image: " + e.getMessage());
        }
    }

    @PutMapping("/add-courses/{trainee-id}")
    public ResponseEntity<?> addCoursesToTrainee(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("trainee-id") Long traineeId,
            @RequestBody Set<Long> courseIds) {

        try {
            List<PreServiceCourseResponse> response = preServiceTraineeService
                    .addCoursesToTrainee(tenantId, traineeId, courseIds);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the courses to trainee: " + e.getMessage());
        }
    }

    @PutMapping("/update/{trainee-id}")
    public ResponseEntity<?> updatePreServiceTrainee(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("trainee-id") Long traineeId,
            @RequestPart("trainee") PreServiceTraineeRequest request,
            @RequestPart(value = "image", required = false ) MultipartFile file) {

        try {
            PreServiceTraineeResponse response = preServiceTraineeService
                    .updatePreServiceTrainee(tenantId, traineeId, request, file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the pre-service trainee: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{trainee-id}")
    public ResponseEntity<?> deletePreServiceTrainee(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("trainee-id") Long traineeId) {

        try {
            preServiceTraineeService.deletePreServiceTrainee(tenantId, traineeId);
            return ResponseEntity.ok("pre-service trainee deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the pre-service trainee: " + e.getMessage());
        }
    }
}
