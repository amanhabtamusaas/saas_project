package com.insa.controller;

import com.insa.dto.request.InternshipPlacementRequest;
import com.insa.dto.request.InternshipStudentRequest;
import com.insa.dto.response.InternshipStudentResponse;
import com.insa.service.InternshipStudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internship-students/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Internship Student")
public class InternshipStudentController {

    private final InternshipStudentService internshipStudentService;

    @PostMapping("/add")
    public ResponseEntity<?> addInternshipStudent(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody InternshipStudentRequest request) {

        try {
            InternshipStudentResponse response = internshipStudentService
                    .addInternshipStudent(tenantId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the internship student: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllInternshipStudents(
            @PathVariable("tenant-id") Long tenantId) {

        try {
            List<InternshipStudentResponse> responses = internshipStudentService
                    .getAllInternshipStudents(tenantId);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the internship students: " + e.getMessage());
        }
    }

    @GetMapping("/get/{intern-id}")
    public ResponseEntity<?> getInternshipStudentById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("intern-id") Long internId) {

        try {
            InternshipStudentResponse response = internshipStudentService
                    .getInternshipStudentById(tenantId, internId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the internship student: " + e.getMessage());
        }
    }

    @GetMapping("/get-all/{budget-year-id}")
    public ResponseEntity<?> getInternshipStudentsByYearAndSemester(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("budget-year-id") Long yearId,
            @RequestParam("Semester") String semester) {

        try {
            List<InternshipStudentResponse> responses = internshipStudentService
                    .getInternshipStudentsByYearAndSemester(tenantId, yearId, semester);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the internship students: " + e.getMessage());
        }
    }

    @PutMapping("/update/{intern-id}")
    public ResponseEntity<?> updateInternshipStudent(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("intern-id") Long internId,
            @RequestBody InternshipStudentRequest request) {

        try {
            InternshipStudentResponse response = internshipStudentService
                    .updateInternshipStudent(tenantId, internId, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the internship student: " + e.getMessage());
        }
    }

    @PutMapping("/assign-department/{intern-id}")
    public ResponseEntity<?> assignInternToPlacementDepartment(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("intern-id") Long internId,
            @RequestBody InternshipPlacementRequest request) {

        try {
            InternshipStudentResponse response = internshipStudentService
                    .assignInternToPlacementDepartment(tenantId, internId, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while placing the internship student: " + e.getMessage());
        }
    }

    @PutMapping("assign-status/{intern-id}")
    public ResponseEntity<?> assignInternshipStatus(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("intern-id") Long internId,
            @RequestParam("status") String internshipStatus) {

        try {
            InternshipStudentResponse response = internshipStudentService
                    .assignInternshipStatus(tenantId, internId, internshipStatus);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while assigning the internship student status: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{intern-id}")
    public ResponseEntity<?> deleteInternshipStudent(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("intern-id") Long internId) {

        try {
            internshipStudentService.deleteInternshipStudent(tenantId, internId);
            return ResponseEntity.ok("Internship student deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the internship student: " + e.getMessage());
        }
    }
}
