package com.insa.leave_service.controller;

import com.insa.leave_service.dto.request.LeaveTypeRequest;
import com.insa.leave_service.dto.response.LeaveTypeResponse;
import com.insa.leave_service.service.LeaveTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-types/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Leave Type")
public class LeaveTypeController {

    private final LeaveTypeService leaveTypeService;

    @PostMapping("/create-leave-type")
    public ResponseEntity<LeaveTypeResponse> createLeaveType(@PathVariable("tenantId") Long tenantId, @RequestBody LeaveTypeRequest leaveTypeRequest) {
        LeaveTypeResponse leaveTypeResponse = leaveTypeService.createLeaveType(tenantId, leaveTypeRequest);
        return ResponseEntity.ok(leaveTypeResponse);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<LeaveTypeResponse>> getAllLeaveTypes(@PathVariable("tenantId") Long tenantId) {
        List<LeaveTypeResponse> leaveTypeResponses = leaveTypeService.getAllLeaveTypes(tenantId);
        return ResponseEntity.ok(leaveTypeResponses);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<LeaveTypeResponse> getLeaveTypeById(@PathVariable("tenantId") Long tenantId, @PathVariable Long id) {
        LeaveTypeResponse leaveTypeResponse = leaveTypeService.getLeaveTypeById(tenantId, id);
        return ResponseEntity.ok(leaveTypeResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LeaveTypeResponse> updateLeaveType(@PathVariable("tenantId") Long tenantId, @PathVariable Long id, @RequestBody LeaveTypeRequest leaveTypeRequest) {
        LeaveTypeResponse leaveTypeResponse = leaveTypeService.updateLeaveType(tenantId, id, leaveTypeRequest);
        return ResponseEntity.ok(leaveTypeResponse);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> deleteLeaveType(@PathVariable("tenantId") Long tenantId, @PathVariable Long id) {
        leaveTypeService.deleteLeaveType(tenantId, id);
        return ResponseEntity.ok("Holiday remove successfully !");
    }
}
