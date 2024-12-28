package com.leave_service.controller;

import com.leave_service.config.PermissionEvaluator;
import com.leave_service.dto.request.LeaveApproveDepartmentRequest;
import com.leave_service.dto.response.LeaveApproveDepartmentResponse;
import com.leave_service.service.LeaveApproveDepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/leave-approve-departments/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Leave Approve Department")
public class LeaveApproveDepartmentController {

    private final LeaveApproveDepartmentService leaveApproveDepartmentService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createLeaveApproveDepartment(
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody LeaveApproveDepartmentRequest request) {

        permissionEvaluator.addDepartmentApproveLeavePermission();

        LeaveApproveDepartmentResponse response = leaveApproveDepartmentService
                .createLeaveApproveDepartment(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllLeaveApproveDepartments(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllDepartmentApprovedLeavesPermission();

        List<LeaveApproveDepartmentResponse> responses = leaveApproveDepartmentService
                .getAllLeaveApproveDepartments(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get-by/{id}")
    public ResponseEntity<?> getLeaveApproveDepartmentById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("id") UUID id) {

        permissionEvaluator.getDepartmentApprovedLeaveByIdPermission();

        LeaveApproveDepartmentResponse response = leaveApproveDepartmentService
                .getLeaveApproveDepartmentById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLeaveApproveDepartment(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("id") UUID id,
            @RequestBody LeaveApproveDepartmentRequest request) {

        permissionEvaluator.updateDepartmentApprovedLeavePermission();

        LeaveApproveDepartmentResponse response = leaveApproveDepartmentService
                .updateLeaveApproveDepartment(tenantId, id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-by/{id}")
    public ResponseEntity<?> deleteLeaveApproveDepartment(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("id") UUID id) {

        permissionEvaluator.deleteDepartmentApprovedLeavePermission();

        leaveApproveDepartmentService.deleteLeaveApproveDepartment(tenantId, id);
        return ResponseEntity.noContent().build();
    }
}