package com.leave_service.controller;

import com.leave_service.config.PermissionEvaluator;
import com.leave_service.dto.response.LeaveBalanceResponse;
import com.leave_service.model.LeaveBalanceHistory;
import com.leave_service.service.LeaveBalanceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/leave-balance/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Leave Balance")
public class LeaveBalanceController {

    private final LeaveBalanceService leaveBalanceService;
    private final PermissionEvaluator permissionEvaluator;

    @GetMapping("/balance/{employeeId}")
    public ResponseEntity<?> calculateLeaveBalance(
            @PathVariable UUID tenantId,
            @PathVariable UUID employeeId) {

        permissionEvaluator.getEmployeeLeaveBalancePermission();

        LeaveBalanceResponse leaveBalanceResponse = leaveBalanceService
                .calculateLeaveBalance(tenantId, employeeId);
        return ResponseEntity.ok(leaveBalanceResponse);
    }

    @GetMapping("/history/{employeeId}")
    public ResponseEntity<?> getLeaveBalanceHistory(
            @PathVariable UUID tenantId,
            @PathVariable UUID employeeId) {

        permissionEvaluator.getAllEmployeeLeaveBalanceHistoriesPermission();

        List<LeaveBalanceHistory> leaveBalanceHistory = leaveBalanceService
                .getLeaveBalanceHistory(employeeId, tenantId);
        return ResponseEntity.ok(leaveBalanceHistory);
    }
}