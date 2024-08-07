package com.insa.controller;

import com.insa.dto.request.AnnualTrainingPlanRequest;
import com.insa.dto.response.AnnualTrainingPlanResponse;
import com.insa.service.AnnualTrainingPlanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/annual-training-plans/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Annual Training Plan")
public class AnnualTrainingPlanController {

    private final AnnualTrainingPlanService annualTrainingPlanService;

    @PostMapping("/add")
    public ResponseEntity<?> addAnnualTrainingPlan(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody AnnualTrainingPlanRequest request) {

        try {
            AnnualTrainingPlanResponse response = annualTrainingPlanService
                    .addAnnualTrainingPlan(tenantId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the training plan: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllAnnualTrainingPlans(
            @PathVariable("tenant-id") Long tenantId) {

        try {
            List<AnnualTrainingPlanResponse> responses = annualTrainingPlanService
                    .getAllAnnualTrainingPlans(tenantId);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the training plans: " + e.getMessage());
        }
    }

    @GetMapping("/get/{training-plan-id}")
    public ResponseEntity<?> getAnnualTrainingPlanById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("training-plan-id") Long trainingPlanId) {

        try {
            AnnualTrainingPlanResponse response = annualTrainingPlanService
                    .getAnnualTrainingPlanById(tenantId, trainingPlanId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the training plan: " + e.getMessage());
        }
    }

    @GetMapping("/get/department/{department-id}")
    public ResponseEntity<?> getAnnualTrainingPlansByDepartment(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("department-id") Long departmentId) {

        try {
            List<AnnualTrainingPlanResponse> responses = annualTrainingPlanService
                    .getAnnualTrainingPlansByDepartment(tenantId, departmentId);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the training plans: " + e.getMessage());
        }
    }

    @PutMapping("/update/{training-plan-id}")
    public ResponseEntity<?> updateAnnualTrainingPlan(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("training-plan-id") Long trainingPlanId,
            @RequestBody AnnualTrainingPlanRequest request) {

        try {
            AnnualTrainingPlanResponse response = annualTrainingPlanService
                    .updateAnnualTrainingPlan(tenantId, trainingPlanId, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the training plan: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{training-plan-id}")
    public ResponseEntity<?> deleteAnnualTrainingPlan(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("training-plan-id") Long trainingPlanId) {

        try {
            annualTrainingPlanService.deleteAnnualTrainingPlan(tenantId, trainingPlanId);
            return ResponseEntity.ok("Annual training plan deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the training plan: " + e.getMessage());
        }
    }
}
