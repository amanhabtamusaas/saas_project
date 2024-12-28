package com.leave_service.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "leave_balance_history")

@AllArgsConstructor
@Data
public class LeaveBalanceHistory {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Setter
    @Getter
    @Column(name = "employee_id")
    private UUID employeeId;

    @Setter
    @Getter
    @Column(name = "total_leave_days")
    private int totalLeaveDays;

    @Setter
    @Getter
    @Column(name = "remaining_leave_days")
    private int remainingLeaveDays;

    @Setter
    @Getter
    @Column(name = "tenant_id")
    private UUID tenantId;

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_year_id")
    private BudgetYear budgetYear;

    @Column(name = "calculation_date")
    private LocalDate calculationDate;

    // Constructors, getters, and setters

    public LeaveBalanceHistory() {
        // Default constructor is needed by Hibernate
    }


    public LeaveBalanceHistory(UUID employeeId, int totalLeaveDays, int remainingLeaveDays, UUID tenantId) {
        this.employeeId = employeeId;
        this.totalLeaveDays = totalLeaveDays;
        this.remainingLeaveDays = remainingLeaveDays;
        this.tenantId = tenantId;
    }

    public void setBudgetYear(UUID budgetYearId) {
    }

    // Getters and setters

    // Constructors, getters, setters
}
