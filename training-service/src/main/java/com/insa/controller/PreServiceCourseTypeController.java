package com.insa.controller;

import com.insa.dto.request.PreServiceCourseTypeRequest;
import com.insa.dto.response.PreServiceCourseTypeResponse;
import com.insa.service.PreServiceCourseTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course-types/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Pre-Service Course Type")
public class PreServiceCourseTypeController {

    private final PreServiceCourseTypeService preServiceCourseTypeService;

    @PostMapping("/add")
    public ResponseEntity<?> addCourseType(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody PreServiceCourseTypeRequest request) {

        try {
            PreServiceCourseTypeResponse response = preServiceCourseTypeService
                    .addCourseType(tenantId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the course type: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCourseTypes(
            @PathVariable("tenant-id") Long tenantId) {

        try {
            List<PreServiceCourseTypeResponse> responses = preServiceCourseTypeService
                    .getAllCourseTypes(tenantId);
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the course types: " + e.getMessage());
        }
    }

    @GetMapping("/get/{course-type-id}")
    public ResponseEntity<?> getCourseType(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("course-type-id") Long courseTypeId) {

        try {
            PreServiceCourseTypeResponse response = preServiceCourseTypeService
                    .getCourseType(tenantId, courseTypeId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the course type: " + e.getMessage());
        }
    }

    @PutMapping("/update/{course-type-id}")
    public ResponseEntity<?> updateCourseType(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("course-type-id") Long courseTypeId,
            @RequestBody PreServiceCourseTypeRequest request) {

        try {
            PreServiceCourseTypeResponse response = preServiceCourseTypeService
                    .updateCourseType(tenantId, courseTypeId, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the course type: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{course-type-id}")
    public ResponseEntity<?> deleteCourseType(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("course-type-id") Long courseTypeId) {

        try {
            preServiceCourseTypeService.deleteCourseType(tenantId, courseTypeId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Course type deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the course type: " + e.getMessage());
        }
    }
}
