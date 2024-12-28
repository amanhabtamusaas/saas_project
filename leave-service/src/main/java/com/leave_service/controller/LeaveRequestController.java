package com.leave_service.controller;

import com.leave_service.config.PermissionEvaluator;
import com.leave_service.dto.request.LeaveRequestRequest;
import com.leave_service.dto.response.LeaveRequestResponse;
import com.leave_service.service.LeaveRequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/leave-requests/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Leave Request")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add-request")
    public ResponseEntity<?> createLeaveRequest(
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody LeaveRequestRequest leaveRequestRequest) {

        permissionEvaluator.addLeaveRequestPermission();

        LeaveRequestResponse createdLeaveRequest = leaveRequestService
                .createLeaveRequest(tenantId, leaveRequestRequest);
        return new ResponseEntity<>(createdLeaveRequest, HttpStatus.CREATED);
    }

    @GetMapping("/get-all-request")
    public ResponseEntity<?> getAllLeaveRequests(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllLeaveRequestsPermission();

        List<LeaveRequestResponse> leaveRequests = leaveRequestService.getAllLeaveRequests(tenantId);
        return ResponseEntity.ok(leaveRequests);
    }

    @GetMapping("/get-request-by/{id}")
    public ResponseEntity<?> getLeaveRequestById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.getLeaveRequestByIdPermission();

        LeaveRequestResponse leaveRequest = leaveRequestService.getLeaveRequestById(tenantId, id);
        return ResponseEntity.ok(leaveRequest);
    }

    @PutMapping("/update-request/{id}")
    public ResponseEntity<?> updateLeaveRequest(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id,
            @RequestBody LeaveRequestRequest leaveRequestRequest) {

        permissionEvaluator.updateLeaveRequestPermission();

        LeaveRequestResponse updatedLeaveRequest = leaveRequestService
                .updateLeaveRequest(tenantId, id, leaveRequestRequest);
        return ResponseEntity.ok(updatedLeaveRequest);
    }

    @DeleteMapping("/delete-request/{id}")
    public ResponseEntity<?> deleteLeaveRequest(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.deleteLeaveRequestPermission();

        leaveRequestService.deleteLeaveRequest(tenantId, id);
        return ResponseEntity.ok("Leave Request Deleted Successfully!");
    }
}