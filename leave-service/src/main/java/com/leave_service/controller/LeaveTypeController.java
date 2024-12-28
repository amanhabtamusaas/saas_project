package com.leave_service.controller;

import com.leave_service.config.PermissionEvaluator;
import com.leave_service.dto.request.LeaveTypeRequest;
import com.leave_service.dto.response.LeaveTypeResponse;
import com.leave_service.service.LeaveTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/leave-types/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Leave Type")
public class LeaveTypeController {

    private final LeaveTypeService leaveTypeService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/create-leave-type")
    public ResponseEntity<?> createLeaveType(
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody LeaveTypeRequest leaveTypeRequest) {

        permissionEvaluator.addLeaveTypePermission();

        LeaveTypeResponse leaveTypeResponse = leaveTypeService
                .createLeaveType(tenantId, leaveTypeRequest);
        return ResponseEntity.ok(leaveTypeResponse);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllLeaveTypes(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllLeaveTypesPermission();

        List<LeaveTypeResponse> leaveTypeResponses = leaveTypeService.getAllLeaveTypes(tenantId);
        return ResponseEntity.ok(leaveTypeResponses);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getLeaveTypeById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.getLeaveTypeByIdPermission();

        LeaveTypeResponse leaveTypeResponse = leaveTypeService.getLeaveTypeById(tenantId, id);
        return ResponseEntity.ok(leaveTypeResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLeaveType(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id,
            @RequestBody LeaveTypeRequest leaveTypeRequest) {

        permissionEvaluator.updateLeaveTypePermission();

        LeaveTypeResponse leaveTypeResponse = leaveTypeService
                .updateLeaveType(tenantId, id, leaveTypeRequest);
        return ResponseEntity.ok(leaveTypeResponse);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> deleteLeaveType(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.deleteLeaveTypePermission();

        leaveTypeService.deleteLeaveType(tenantId, id);
        return ResponseEntity.ok("Leave Type removed successfully!");
    }
}