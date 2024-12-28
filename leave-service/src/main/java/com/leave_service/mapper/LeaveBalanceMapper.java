package com.leave_service.mapper;

import com.leave_service.dto.request.LeaveBalanceRequest;
import com.leave_service.dto.response.LeaveBalanceResponse;
import com.leave_service.model.*;
import com.leave_service.repository.BudgetYearRepository;
//import com.leave_service.repository.LeaveBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeaveBalanceMapper {
    private final BudgetYearRepository budgetYearRepository;
    public LeaveBalance toEntity(LeaveBalanceRequest requestDTO) {
        LeaveBalance leaveBalance = new LeaveBalance();
//        leaveBalance.setEmployeeId(requestDTO.getEmployeeId());
//        leaveBalance.setTotalLeaveDays(requestDTO.getTotalLeaveDays());
//        leaveBalance.setRemainingLeaveDays(requestDTO.getRemainingLeaveDays());
//        leaveBalance.setTenantId(requestDTO.getTenantId());

        // Map BudgetYear if available
        if (requestDTO.getBudgetYearId() != null) {
            BudgetYear budgetYear = budgetYearRepository.findById(requestDTO.getBudgetYearId())
                    .orElseThrow(() -> new RuntimeException("BudgetYear not found with ID: " + requestDTO.getBudgetYearId()));
            leaveBalance.setBudgetYear(budgetYear);
        }

        return leaveBalance;
    }

    public LeaveBalanceResponse mapToDto(LeaveBalance entity) {
        LeaveBalanceResponse response = new LeaveBalanceResponse();
        response.setId(entity.getId());
        response.setEmployeeId(entity.getEmployeeId());
        response.setBudgetYearId(entity.getBudgetYear() != null ? entity.getBudgetYear().getId() : null); // Ensure mapping of budgetYearId
        response.setTotalLeaveDays(entity.getTotalLeaveDays());
        response.setRemainingLeaveDays(entity.getRemainingLeaveDays());
        response.setTenantId(entity.getTenantId());
        return response;
    }
    public void updateLeaveBalance(LeaveBalance leaveBalance, LeaveBalanceRequest requestDTO) {
//        leaveBalance.setEmployeeId(requestDTO.getEmployeeId());
//        leaveBalance.setTotalLeaveDays(requestDTO.getTotalLeaveDays());
//        leaveBalance.setRemainingLeaveDays(requestDTO.getRemainingLeaveDays());
//        leaveBalance.setTenantId(requestDTO.getTenantId());

        // Map BudgetYear if available
        if (requestDTO.getBudgetYearId() != null) {
            BudgetYear budgetYear = budgetYearRepository.findById(requestDTO.getBudgetYearId())
                    .orElseThrow(() -> new RuntimeException("BudgetYear not found with ID: " + requestDTO.getBudgetYearId()));
            leaveBalance.setBudgetYear(budgetYear);
        }
    }
}
