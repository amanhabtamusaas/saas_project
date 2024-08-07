package com.insa.controller;

import com.insa.dto.request.ExamResultRequest;
import com.insa.dto.response.ExamResultResponse;
import com.insa.service.ExamResultService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam-result/{tenant-id}/{recruitment-id}")
@RequiredArgsConstructor
@Tag(name = "Exam Result")
public class ExamResultController {

    private final ExamResultService examResultService;

    @PostMapping("/{applicant-id}/add")
    public ResponseEntity<?> createExamResult(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("recruitment-id") Long recruitmentId,
            @PathVariable("applicant-id") Long applicantId,
            @RequestBody ExamResultRequest request) {

        try {
            ExamResultResponse response = examResultService
                    .createExamResult(tenantId, recruitmentId, applicantId, request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the exam result: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllExamResult(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("recruitment-id") Long recruitmentId) {

        try {
            List<ExamResultResponse> responses = examResultService
                    .getAllExamResult(tenantId, recruitmentId);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the exam results: " + e.getMessage());
        }
    }

    @GetMapping("/{applicant-id}/get/{exam-result-id}")
    public ResponseEntity<?> getExamResultById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("recruitment-id") Long recruitmentId,
            @PathVariable("applicant-id") Long applicantId,
            @PathVariable("exam-result-id") Long examResultId) {

        try {
            ExamResultResponse response = examResultService
                    .getExamResultById(tenantId, recruitmentId, applicantId, examResultId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the exam result: " + e.getMessage());
        }
    }

    @PutMapping("/{applicant-id}/update/{exam-result-id}")
    public ResponseEntity<?> updateExamResult(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("recruitment-id") Long recruitmentId,
            @PathVariable("applicant-id") Long applicantId,
            @PathVariable("exam-result-id") Long examResultId,
            @RequestBody ExamResultRequest request) {

        try {
            ExamResultResponse response = examResultService
                    .updateExamResult(tenantId, recruitmentId,
                            applicantId, examResultId, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the exam result: " + e.getMessage());
        }
    }
}
