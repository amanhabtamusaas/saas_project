package com.leave_service.dto.response;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Data
@NoArgsConstructor
public class LeaveBalanceResponse {
    // Getters and Setters
    private UUID id;
    private UUID employeeId;
    private UUID budgetYearId;
    private int totalLeaveDays;
    private int remainingLeaveDays;
    private UUID tenantId;

    // Constructor
    public LeaveBalanceResponse(UUID id, UUID employeeId, UUID budgetYearId, int totalLeaveDays, int remainingLeaveDays, UUID tenantId) {
        this.id = id;
        this.employeeId = employeeId;
        this.budgetYearId = budgetYearId;
        this.totalLeaveDays = totalLeaveDays;
        this.remainingLeaveDays = remainingLeaveDays;
        this.tenantId = tenantId;
    }



}
