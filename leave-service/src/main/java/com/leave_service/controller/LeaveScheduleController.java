package com.leave_service.controller;

import com.leave_service.config.PermissionEvaluator;
import com.leave_service.dto.request.LeaveScheduleRequest;
import com.leave_service.dto.response.LeaveScheduleResponse;
import com.leave_service.service.LeaveScheduleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/leave-schedules/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Leave Schedule")
public class LeaveScheduleController {

    private final LeaveScheduleService leaveScheduleService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createLeaveSchedule(
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody LeaveScheduleRequest request) {

        permissionEvaluator.addLeaveSchedulePermission();

        LeaveScheduleResponse response = leaveScheduleService.createLeaveSchedule(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllLeaveSchedules(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllLeaveSchedulesPermission();

        List<LeaveScheduleResponse> responses = leaveScheduleService.getAllLeaveSchedules(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get-by/{id}")
    public ResponseEntity<?> getLeaveScheduleById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("id") UUID id) {

        permissionEvaluator.getLeaveScheduleByIdPermission();

        LeaveScheduleResponse response = leaveScheduleService.getLeaveScheduleById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLeaveSchedule(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("id") UUID id,
            @RequestBody LeaveScheduleRequest request) {

        permissionEvaluator.updateLeaveSchedulePermission();

        LeaveScheduleResponse response = leaveScheduleService.updateLeaveSchedule(tenantId, id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLeaveSchedule(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("id") UUID id) {

        permissionEvaluator.deleteLeaveSchedulePermission();

        leaveScheduleService.deleteLeaveSchedule(tenantId, id);
        return ResponseEntity.ok("Deleted successfully!");
    }
}