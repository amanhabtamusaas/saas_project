package com.insa.leave_service.controller;

import com.insa.leave_service.dto.request.LeaveApproveDepartmentRequest;
import com.insa.leave_service.dto.response.LeaveApproveDepartmentResponse;
import com.insa.leave_service.service.LeaveApproveDepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/leave-approve-departments/{tenantId}")
@RequiredArgsConstructor
@Tag(name="Leave Approve Department ")
public class LeaveApproveDepartmentController {

    private final LeaveApproveDepartmentService leaveApproveDepartmentService;

    @PostMapping("/add")
    public ResponseEntity<LeaveApproveDepartmentResponse> createLeaveApproveDepartment(
            @PathVariable("tenantId") Long tenantId,
            @RequestBody LeaveApproveDepartmentRequest request) {
        LeaveApproveDepartmentResponse response = leaveApproveDepartmentService.createLeaveApproveDepartment(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<LeaveApproveDepartmentResponse>> getAllLeaveApproveDepartments(@PathVariable("tenantId") Long tenantId) {
        List<LeaveApproveDepartmentResponse> responses = leaveApproveDepartmentService.getAllLeaveApproveDepartments(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get-by/{id}")
    public ResponseEntity<LeaveApproveDepartmentResponse> getLeaveApproveDepartmentById(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable("id") Long id) {
        LeaveApproveDepartmentResponse response = leaveApproveDepartmentService.getLeaveApproveDepartmentById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LeaveApproveDepartmentResponse> updateLeaveApproveDepartment(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable("id") Long id,
            @RequestBody LeaveApproveDepartmentRequest request) {
        LeaveApproveDepartmentResponse response = leaveApproveDepartmentService.updateLeaveApproveDepartment(tenantId, id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-by/{id}")
    public ResponseEntity<Void> deleteLeaveApproveDepartment(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable("id") Long id) {
        leaveApproveDepartmentService.deleteLeaveApproveDepartment(tenantId, id);
        return ResponseEntity.noContent().build();
    }
}
