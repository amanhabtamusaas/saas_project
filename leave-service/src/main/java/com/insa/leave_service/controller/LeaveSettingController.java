package com.insa.leave_service.controller;

import com.insa.leave_service.dto.request.LeaveSettingRequest;
import com.insa.leave_service.dto.response.LeaveSettingResponse;
import com.insa.leave_service.service.LeaveSettingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-settings/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Leave Setting")
public class LeaveSettingController {

    private final LeaveSettingService leaveSettingService;

    @PostMapping("/create-setting")
    public ResponseEntity<LeaveSettingResponse> createLeaveSetting(
            @PathVariable("tenantId") Long tenantId,
            @RequestBody LeaveSettingRequest leaveSettingRequest) {
        LeaveSettingResponse response = leaveSettingService.createLeaveSetting(tenantId, leaveSettingRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<LeaveSettingResponse>> getAllLeaveSettings(@PathVariable("tenantId") Long tenantId) {
        List<LeaveSettingResponse> responses = leaveSettingService.getAllLeaveSettings(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get-by/{id}")
    public ResponseEntity<LeaveSettingResponse> getLeaveSettingById(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable Long id) {
        LeaveSettingResponse response = leaveSettingService.getLeaveSettingById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LeaveSettingResponse> updateLeaveSetting(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable Long id,
            @RequestBody LeaveSettingRequest leaveSettingRequest) {
        LeaveSettingResponse response = leaveSettingService.updateLeaveSetting(tenantId, id, leaveSettingRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> deleteLeaveSetting(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable Long id) {
        leaveSettingService.deleteLeaveSetting(tenantId, id);
        return ResponseEntity.ok("Leave Setting is Deleted Successfully!");
    }
}
