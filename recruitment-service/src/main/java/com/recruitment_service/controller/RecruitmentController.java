package com.recruitment_service.controller;

import com.recruitment_service.config.PermissionEvaluator;
import com.recruitment_service.dto.request.RecruitmentApproveRequest;
import com.recruitment_service.dto.request.RecruitmentRequest;
import com.recruitment_service.dto.response.RecruitmentResponse;
import com.recruitment_service.enums.RecruitmentStatus;
import com.recruitment_service.service.RecruitmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recruitments/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Recruitment")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createRecruitment(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody RecruitmentRequest request) {

        permissionEvaluator.addRecruitmentPermission();

        RecruitmentResponse response = recruitmentService
                .createRecruitment(tenantId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllRecruitments(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllRecruitmentsPermission();

        List<RecruitmentResponse> responses = recruitmentService
                .getAllRecruitments(tenantId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/get/{recruitment-id}")
    public ResponseEntity<?> getRecruitmentById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId) {

        permissionEvaluator.getRecruitmentByIdPermission();

        RecruitmentResponse response = recruitmentService
                .getRecruitmentById(tenantId, recruitmentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get/vacancy")
    public ResponseEntity<?> getRecruitmentByVacancyNumber(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("vacancy-number") String vacancyNumber) {

        permissionEvaluator.getRecruitmentByVacancyNumberPermission();

        RecruitmentResponse response = recruitmentService
                .getRecruitmentByVacancyNumber(tenantId, vacancyNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get/status")
    public ResponseEntity<?> getRecruitmentsByStatus(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("recruitment-status") RecruitmentStatus recruitmentStatus) {

        permissionEvaluator.getRecruitmentsByStatusPermission();

        List<RecruitmentResponse> responses = recruitmentService
                .getRecruitmentsByStatus(tenantId, recruitmentStatus);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping("/update/{recruitment-id}")
    public ResponseEntity<?> updateRecruitment(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId,
            @RequestBody RecruitmentRequest request) {

        permissionEvaluator.updateRecruitmentPermission();

        RecruitmentResponse response = recruitmentService
                .updateRecruitment(tenantId, recruitmentId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/approve/{recruitment-id}")
    public ResponseEntity<?> approveRecruitment(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId,
            @RequestBody RecruitmentApproveRequest request) {

        permissionEvaluator.approveRecruitmentPermission();

        RecruitmentResponse response = recruitmentService
                .approveRecruitment(tenantId, recruitmentId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{recruitment-id}")
    public ResponseEntity<?> deleteRecruitment(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId) {

        permissionEvaluator.deleteRecruitmentPermission();

        recruitmentService.deleteRecruitment(tenantId, recruitmentId);
        return ResponseEntity.ok("Recruitment deleted successfully");
    }
}