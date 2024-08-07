package com.insa.leave_service.controller;

import com.insa.leave_service.dto.request.LeaveScheduleRequest;
import com.insa.leave_service.dto.response.LeaveScheduleResponse;
import com.insa.leave_service.service.LeaveScheduleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-schedules/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Leave Schedule")
public class LeaveScheduleController {

    private final LeaveScheduleService leaveScheduleService;

    @PostMapping("/add")
    public ResponseEntity<LeaveScheduleResponse> createLeaveSchedule(
            @PathVariable("tenantId") Long tenantId,
            @RequestBody LeaveScheduleRequest request) {
        LeaveScheduleResponse response = leaveScheduleService.createLeaveSchedule(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<LeaveScheduleResponse>> getAllLeaveSchedules(@PathVariable("tenantId") Long tenantId) {
        List<LeaveScheduleResponse> responses = leaveScheduleService.getAllLeaveSchedules(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get-by/{id}")
    public ResponseEntity<LeaveScheduleResponse> getLeaveScheduleById(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable("id") Long id) {
        LeaveScheduleResponse response = leaveScheduleService.getLeaveScheduleById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LeaveScheduleResponse> updateLeaveSchedule(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable("id") Long id,
            @RequestBody LeaveScheduleRequest request) {
        LeaveScheduleResponse response = leaveScheduleService.updateLeaveSchedule(tenantId, id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLeaveSchedule(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable("id") Long id) {
        leaveScheduleService.deleteLeaveSchedule(tenantId, id);
        return ResponseEntity.ok("deleted successfully !");
    }
}
