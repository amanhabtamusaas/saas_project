package com.insa.controller;

import com.insa.dto.requestDto.StaffPlanRequest;
import com.insa.dto.responseDto.StaffPlanResponse;
import com.insa.service.StaffPlanService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff-plans/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Staff Plan")
@SecurityRequirement(name = "Keycloak")
public class StaffPlanController {

    private final StaffPlanService staffPlanService;

    @PostMapping("/add-staff-plan")
    public ResponseEntity<StaffPlanResponse> createStaffPlan(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody StaffPlanRequest staffPlanRequest) {
        StaffPlanResponse staffPlan = staffPlanService.createStaffPlan(tenantId, staffPlanRequest);
        return new ResponseEntity<>(staffPlan, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<StaffPlanResponse>> getAllStaffPlans(
            @PathVariable("tenant-id") Long tenantId) {
        List<StaffPlanResponse> staffPlans = staffPlanService.getAllStaffPlans(tenantId);
        return ResponseEntity.ok(staffPlans);
    }

    @GetMapping("/departments/{department-id}")
    public ResponseEntity<List<StaffPlanResponse>> getStaffPlanByDepartmentId(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("department-id") Long departmentId) {
        List<StaffPlanResponse> response = staffPlanService.getStaffPlanByDepartmentId(departmentId, tenantId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-staff-plan/{id}")
    public ResponseEntity<StaffPlanResponse> updateStaffPlan(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable Long id,
            @RequestBody StaffPlanRequest staffPlanRequest) {
        StaffPlanResponse staffPlan = staffPlanService.updateStaffPlan(id, tenantId, staffPlanRequest);
        return ResponseEntity.ok(staffPlan);
    }

    @DeleteMapping("/delete-staff-plan/{id}")
    public ResponseEntity<String> deleteStaffPlan(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable Long id) {
        staffPlanService.deleteStaffPlan(id, tenantId);
        return ResponseEntity.ok("Staff-plan deleted successfully!");
    }
}