package com.insa.leave_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Data
public class LeaveBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "employee_id")
    private Long employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_year_id")
    private BudgetYear budgetYear;

    @Column(name = "total_days")
    private int totalLeaveDays;

    @Column(name = "remaining_leave_days")
    private int remainingLeaveDays;

    @Column(name = "tenant_id")
    private Long tenantId;

    // Constructors, getters, and setters

    public LeaveBalance() {
        // Default constructor is needed by Hibernate
    }


    public LeaveBalance(Long employeeId, int totalLeaveDays, int remainingLeaveDays, Long tenantId) {
        this.employeeId = employeeId;
        this.totalLeaveDays = totalLeaveDays;
        this.remainingLeaveDays = remainingLeaveDays;
        this.tenantId = tenantId;
    }

    // Getters and setters

}
