package com.recruitment_service.controller;

import com.recruitment_service.config.PermissionEvaluator;
import com.recruitment_service.dto.request.ExamResultRequest;
import com.recruitment_service.dto.response.ExamResultResponse;
import com.recruitment_service.service.ExamResultService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/exam-result/{tenant-id}/{recruitment-id}")
@RequiredArgsConstructor
@Tag(name = "Exam Result")
public class ExamResultController {

    private final ExamResultService examResultService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/{applicant-id}/add")
    public ResponseEntity<?> createExamResult(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId,
            @PathVariable("applicant-id") UUID applicantId,
            @RequestBody ExamResultRequest request) {

        permissionEvaluator.addExamResultPermission();

        ExamResultResponse response = examResultService
                .createExamResult(tenantId, recruitmentId, applicantId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllExamResult(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId) {

        permissionEvaluator.getAllExamResultsPermission();

        List<ExamResultResponse> responses = examResultService
                .getAllExamResult(tenantId, recruitmentId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{applicant-id}/get/{exam-result-id}")
    public ResponseEntity<?> getExamResultById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("exam-result-id") UUID examResultId) {

        permissionEvaluator.getExamResultByIdPermission();

        ExamResultResponse response = examResultService
                .getExamResultById(tenantId, recruitmentId, applicantId, examResultId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{applicant-id}/update/{exam-result-id}")
    public ResponseEntity<?> updateExamResult(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("exam-result-id") UUID examResultId,
            @RequestBody ExamResultRequest request) {

        permissionEvaluator.updateExamResultPermission();

        ExamResultResponse response = examResultService
                .updateExamResult(tenantId, recruitmentId, applicantId, examResultId, request);
        return ResponseEntity.ok(response);
    }
}