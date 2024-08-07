package com.insa.controller;

import com.insa.dto.requestDto.JobGradeRequestDto;
import com.insa.dto.responseDto.JobGradeResponseDto;
import com.insa.service.JobGradeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-grades/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Job Grade")
@SecurityRequirement(name = "Keycloak")
public class JobGradeController {

    private final JobGradeService jobGradeService;

    @PostMapping("/add")
    public ResponseEntity<JobGradeResponseDto> createJobGrade(
            @PathVariable("tenantId") Long tenantId,
            @RequestBody JobGradeRequestDto jobGradeRequestDto) {

        JobGradeResponseDto jobGrade = jobGradeService.createJobGrade(tenantId, jobGradeRequestDto);
        return new ResponseEntity<>(jobGrade, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<JobGradeResponseDto>> getAllJobGrades(
            @PathVariable("tenantId") Long tenantId) {
        List<JobGradeResponseDto> jobGrades = jobGradeService.getAllJobGrades(tenantId);
        return ResponseEntity.ok(jobGrades);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<JobGradeResponseDto> getJobGradeById(
            @PathVariable Long id,
            @PathVariable("tenantId") Long tenantId) {

        JobGradeResponseDto jobGrade = jobGradeService.getJobGradeById(id, tenantId);
        return ResponseEntity.ok(jobGrade);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<JobGradeResponseDto> updateJobGrade(
            @PathVariable Long id,
            @PathVariable("tenantId") Long tenantId,
            @RequestBody JobGradeRequestDto jobGradeRequestDto) {

        JobGradeResponseDto jobGrade = jobGradeService.updateJobGrade(id, tenantId, jobGradeRequestDto);
        return ResponseEntity.ok(jobGrade);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJobGrade(
            @PathVariable Long id,
            @PathVariable("tenantId") Long tenantId) {

        jobGradeService.deleteJobGrade(id, tenantId);
        return ResponseEntity.ok("Job Grade deleted successfully!");
    }
}
