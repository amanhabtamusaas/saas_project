package com.insa.leave_service.controller;

import com.insa.leave_service.dto.request.LeaveApproveHRRequest;
import com.insa.leave_service.dto.response.LeaveApproveHRResponse;
import com.insa.leave_service.service.LeaveApproveHRService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-approve-hr/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Leave ApproveHR")
public class LeaveApproveHRController {

    private final LeaveApproveHRService leaveApproveHRService;

    @PostMapping("/add")
    public ResponseEntity<LeaveApproveHRResponse> createLeaveApproveHR(
            @PathVariable("tenantId") Long tenantId,
            @RequestBody LeaveApproveHRRequest leaveApproveHRRequest) {
        LeaveApproveHRResponse response = leaveApproveHRService.createLeaveApproveHR(tenantId, leaveApproveHRRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<LeaveApproveHRResponse>> getAllLeaveApproveHRs(@PathVariable("tenantId") Long tenantId) {
        List<LeaveApproveHRResponse> responses = leaveApproveHRService.getAllLeaveApproveHRs(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get-by/{id}")
    public ResponseEntity<LeaveApproveHRResponse> getLeaveApproveHRById(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable("id") Long id) {
        LeaveApproveHRResponse response = leaveApproveHRService.getLeaveApproveHRById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LeaveApproveHRResponse> updateLeaveApproveHR(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable("id") Long id,
            @RequestBody LeaveApproveHRRequest leaveApproveHRRequest) {
        LeaveApproveHRResponse response = leaveApproveHRService.updateLeaveApproveHR(tenantId, id, leaveApproveHRRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-by/{id}")
    public ResponseEntity<Void> deleteLeaveApproveHR(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable("id") Long id) {
        leaveApproveHRService.deleteLeaveApproveHR(tenantId, id);
        return ResponseEntity.noContent().build();
    }
}
