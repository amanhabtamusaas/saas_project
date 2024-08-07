package com.insa.leave_service.dto.response;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
public class LeaveBalanceResponse {
    // Getters and Setters
    private Long id;
    private Long employeeId;
    private Long budgetYearId;
    private int totalLeaveDays;
    private int remainingLeaveDays;
    private Long tenantId;

    // Constructor
    public LeaveBalanceResponse(Long id, Long employeeId, Long budgetYearId, int totalLeaveDays, int remainingLeaveDays, Long tenantId) {
        this.id = id;
        this.employeeId = employeeId;
        this.budgetYearId = budgetYearId;
        this.totalLeaveDays = totalLeaveDays;
        this.remainingLeaveDays = remainingLeaveDays;
        this.tenantId = tenantId;
    }



}
