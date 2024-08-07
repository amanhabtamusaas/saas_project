package com.insa.controller;

import com.insa.dto.request.UniversityRequest;
import com.insa.dto.response.UniversityResponse;
import com.insa.service.UniversityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;

@RestController
@RequestMapping("/api/universities/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "University")
public class UniversityController {

    private final UniversityService universityService;

    @PostMapping("/add")
    public ResponseEntity<?> addUniversity(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody UniversityRequest request) {

        try {
            UniversityResponse response = universityService
                    .addUniversity(tenantId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the university: " + e.getMessage());
        }
    }

    @GetMapping("get-all")
    public ResponseEntity<?> getAllUniversities(
            @PathVariable("tenant-id") Long tenantId) {

        try {
            List<UniversityResponse> responses = universityService
                    .getAllUniversities(tenantId);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the universities: " + e.getMessage());
        }
    }

    @GetMapping("/get/{university-id}")
    public ResponseEntity<?> getUniversityById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("university-id") Long universityId) {

        try {
            UniversityResponse response = universityService
                    .getUniversityById(tenantId, universityId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the university: " + e.getMessage());
        }
    }

    @PutMapping("/update/{university-id}")
    public ResponseEntity<?> updateUniversity(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("university-id") Long universityId,
            @RequestBody UniversityRequest request) {

        try {
            UniversityResponse response = universityService
                    .updateUniversity(tenantId, universityId, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the university: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{university-id}")
    public ResponseEntity<?> deleteUniversity(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("university-id") Long universityId) {

        try {
            universityService.deleteUniversity(tenantId, universityId);
            return ResponseEntity.ok("University deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the university: " + e.getMessage());
        }
    }
}
