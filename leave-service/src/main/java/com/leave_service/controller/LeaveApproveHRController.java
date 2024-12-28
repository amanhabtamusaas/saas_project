package com.leave_service.controller;

import com.leave_service.config.PermissionEvaluator;
import com.leave_service.dto.request.LeaveApproveHRRequest;
import com.leave_service.dto.response.LeaveApproveHRResponse;
import com.leave_service.service.LeaveApproveHRService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/leave-approve-hr/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Leave Approve HR")
public class LeaveApproveHRController {

    private final LeaveApproveHRService leaveApproveHRService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createLeaveApproveHR(
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody LeaveApproveHRRequest leaveApproveHRRequest) {

        permissionEvaluator.addHrApproveLeavePermission();

        LeaveApproveHRResponse response = leaveApproveHRService
                .createLeaveApproveHR(tenantId, leaveApproveHRRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllLeaveApproveHRs(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllHrApprovedLeavesPermission();

        List<LeaveApproveHRResponse> responses = leaveApproveHRService.getAllLeaveApproveHRs(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get-by/{id}")
    public ResponseEntity<?> getLeaveApproveHRById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("id") UUID id) {

        permissionEvaluator.getHrApprovedLeaveByIdPermission();

        LeaveApproveHRResponse response = leaveApproveHRService.getLeaveApproveHRById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLeaveApproveHR(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("id") UUID id,
            @RequestBody LeaveApproveHRRequest leaveApproveHRRequest) {

        permissionEvaluator.updateHrApprovedLeavePermission();

        LeaveApproveHRResponse response = leaveApproveHRService
                .updateLeaveApproveHR(tenantId, id, leaveApproveHRRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-by/{id}")
    public ResponseEntity<?> deleteLeaveApproveHR(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("id") UUID id) {

        permissionEvaluator.deleteHrApprovedLeavePermission();

        leaveApproveHRService.deleteLeaveApproveHR(tenantId, id);
        return ResponseEntity.noContent().build();
    }
}