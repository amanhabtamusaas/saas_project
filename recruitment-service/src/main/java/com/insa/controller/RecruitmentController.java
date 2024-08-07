package com.insa.controller;

import com.insa.dto.request.RecruitmentApproveRequest;
import com.insa.dto.request.RecruitmentRequest;
import com.insa.dto.response.RecruitmentResponse;
import com.insa.enums.RecruitmentStatus;
import com.insa.service.RecruitmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruitments/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Recruitment")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @PostMapping("/add")
    public ResponseEntity<?> createRecruitment(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody RecruitmentRequest request) {

        try {
            RecruitmentResponse response = recruitmentService
                    .createRecruitment(tenantId, request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the recruitment: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllRecruitments(
            @PathVariable("tenant-id") Long tenantId) {

        try {
            List<RecruitmentResponse> responses = recruitmentService
                    .getAllRecruitments(tenantId);
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the recruitments: " + e.getMessage());
        }
    }

    @GetMapping("/get/{recruitment-id}")
    public ResponseEntity<?> getRecruitmentById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("recruitment-id") Long recruitmentId) {

        try {
            RecruitmentResponse response = recruitmentService
                    .getRecruitmentById(tenantId, recruitmentId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the recruitment: " + e.getMessage());
        }
    }

    @GetMapping("/get/vacancy")
    public ResponseEntity<?> getRecruitmentByVacancyNumber(
            @PathVariable("tenant-id") Long tenantId,
            @RequestParam("vacancy-number") String vacancyNumber) {

        try {
            RecruitmentResponse response = recruitmentService
                    .getRecruitmentByVacancyNumber(tenantId, vacancyNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the recruitment: " + e.getMessage());
        }
    }

    @GetMapping("/get/status")
    public ResponseEntity<?> getRecruitmentByStatus(
            @PathVariable("tenant-id") Long tenantId,
            @RequestParam("recruitment-status") RecruitmentStatus recruitmentStatus) {

        try {
            List<RecruitmentResponse> responses = recruitmentService
                    .getRecruitmentByStatus(tenantId, recruitmentStatus);
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the recruitments: " + e.getMessage());
        }
    }

    @PutMapping("/update/{recruitment-id}")
    public ResponseEntity<?> updateRecruitment(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("recruitment-id") Long recruitmentId,
            @RequestBody RecruitmentRequest request) {

        try {
            RecruitmentResponse response = recruitmentService
                    .updateRecruitment(tenantId, recruitmentId, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the recruitment: " + e.getMessage());
        }
    }

    @PutMapping("/approve/{recruitment-id}")
    public ResponseEntity<?> approveRecruitment(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("recruitment-id") Long recruitmentId,
            @RequestBody RecruitmentApproveRequest request) {

        try {
            RecruitmentResponse response = recruitmentService
                    .approveRecruitment(tenantId, recruitmentId, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while approving the recruitment: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{recruitment-id}")
    public ResponseEntity<?> deleteRecruitment(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("recruitment-id") Long recruitmentId) {

        try {
            recruitmentService.deleteRecruitment(tenantId, recruitmentId);
            return ResponseEntity.ok("Recruitment deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the recruitment: " + e.getMessage());
        }
    }
}
