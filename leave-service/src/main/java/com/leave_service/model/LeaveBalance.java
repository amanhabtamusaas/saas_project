package com.leave_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Data
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Column(name = "employee_id")
    private UUID employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_year_id")
    private BudgetYear budgetYear;

    @Column(name = "total_days")
    private int totalLeaveDays;

    @Column(name = "remaining_leave_days")
    private int remainingLeaveDays;

    @Column(name = "tenant_id")
    private UUID tenantId;

    // Constructors, getters, and setters

    public LeaveBalance() {
        // Default constructor is needed by Hibernate
    }


    public LeaveBalance(UUID employeeId, int totalLeaveDays, int remainingLeaveDays, UUID tenantId) {
        this.employeeId = employeeId;
        this.totalLeaveDays = totalLeaveDays;
        this.remainingLeaveDays = remainingLeaveDays;
        this.tenantId = tenantId;
    }

    // Getters and setters

}
