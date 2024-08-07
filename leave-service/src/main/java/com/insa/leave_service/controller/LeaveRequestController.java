package com.insa.leave_service.controller;

import com.insa.leave_service.dto.request.LeaveRequestRequest;
import com.insa.leave_service.dto.response.LeaveRequestResponse;
import com.insa.leave_service.service.LeaveRequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-requests/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Leave Request")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    @PostMapping("/add-request")
    public ResponseEntity<LeaveRequestResponse> createLeaveRequest(@PathVariable("tenantId") Long tenantId, @RequestBody LeaveRequestRequest leaveRequestRequest) {
        LeaveRequestResponse createdLeaveRequest = leaveRequestService.createLeaveRequest(tenantId, leaveRequestRequest);
        return new ResponseEntity<>(createdLeaveRequest, HttpStatus.CREATED);
    }

    @GetMapping("/get-all-request")
    public ResponseEntity<List<LeaveRequestResponse>> getAllLeaveRequests(@PathVariable("tenantId") Long tenantId) {
        List<LeaveRequestResponse> leaveRequests = leaveRequestService.getAllLeaveRequests(tenantId);
        return ResponseEntity.ok(leaveRequests);
    }

    @GetMapping("/get-request-by/{id}")
    public ResponseEntity<LeaveRequestResponse> getLeaveRequestById(@PathVariable("tenantId") Long tenantId, @PathVariable Long id) {
        LeaveRequestResponse leaveRequest = leaveRequestService.getLeaveRequestById(tenantId, id);
        return ResponseEntity.ok(leaveRequest);
    }

    @PutMapping("/update-request/{id}")
    public ResponseEntity<LeaveRequestResponse> updateLeaveRequest(@PathVariable("tenantId") Long tenantId, @PathVariable Long id, @RequestBody LeaveRequestRequest leaveRequestRequest) {
        LeaveRequestResponse updatedLeaveRequest = leaveRequestService.updateLeaveRequest(tenantId, id, leaveRequestRequest);
        return ResponseEntity.ok(updatedLeaveRequest);
    }

    @DeleteMapping("/delete-request/{id}")
    public ResponseEntity<String> deleteLeaveRequest(@PathVariable("tenantId") Long tenantId, @PathVariable Long id) {
        leaveRequestService.deleteLeaveRequest(tenantId, id);
        return ResponseEntity.ok("Leave Request Deleted Successfully!");
    }
}
