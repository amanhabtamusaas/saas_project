package com.leave_service.controller;

import com.leave_service.config.PermissionEvaluator;
import com.leave_service.dto.request.LeaveSettingRequest;
import com.leave_service.dto.response.LeaveSettingResponse;
import com.leave_service.service.LeaveSettingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/leave-settings/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Leave Setting")
public class LeaveSettingController {

    private final LeaveSettingService leaveSettingService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/create-setting")
    public ResponseEntity<?> createLeaveSetting(
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody LeaveSettingRequest leaveSettingRequest) {

        permissionEvaluator.addLeaveSettingPermission();

        LeaveSettingResponse response = leaveSettingService
                .createLeaveSetting(tenantId, leaveSettingRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllLeaveSettings(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllLeaveSettingsPermission();

        List<LeaveSettingResponse> responses = leaveSettingService.getAllLeaveSettings(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get-by/{id}")
    public ResponseEntity<?> getLeaveSettingById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.getLeaveSettingByIdPermission();

        LeaveSettingResponse response = leaveSettingService.getLeaveSettingById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLeaveSetting(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id,
            @RequestBody LeaveSettingRequest leaveSettingRequest) {

        permissionEvaluator.updateLeaveSettingPermission();

        LeaveSettingResponse response = leaveSettingService
                .updateLeaveSetting(tenantId, id, leaveSettingRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> deleteLeaveSetting(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.deleteLeaveSettingPermission();

        leaveSettingService.deleteLeaveSetting(tenantId, id);
        return ResponseEntity.ok("Leave Setting is Deleted Successfully!");
    }
}