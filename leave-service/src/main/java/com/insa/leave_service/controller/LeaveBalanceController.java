package com.insa.leave_service.controller;

import com.insa.leave_service.dto.response.LeaveBalanceResponse;
import com.insa.leave_service.entity.LeaveBalanceHistory;
import com.insa.leave_service.service.LeaveBalanceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/leave-balance/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Leave Balance")
public class LeaveBalanceController {

    private final LeaveBalanceService leaveBalanceService;


    @GetMapping("/balance/{employeeId}")
    public ResponseEntity<LeaveBalanceResponse> calculateLeaveBalance(
            @PathVariable Long tenantId,
            @PathVariable Long employeeId) {
        LeaveBalanceResponse leaveBalanceResponse = leaveBalanceService.calculateLeaveBalance(tenantId, employeeId);
        return ResponseEntity.ok(leaveBalanceResponse);
    }

    @GetMapping("/history/{employeeId}")
    public ResponseEntity<List<LeaveBalanceHistory>> getLeaveBalanceHistory(
            @PathVariable Long tenantId,
            @PathVariable Long employeeId) {
        List<LeaveBalanceHistory> leaveBalanceHistory = leaveBalanceService.getLeaveBalanceHistory(employeeId, tenantId);
        return ResponseEntity.ok(leaveBalanceHistory);
    }
}
