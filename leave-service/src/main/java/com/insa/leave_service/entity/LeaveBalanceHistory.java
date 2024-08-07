package com.insa.leave_service.entity;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;

@Entity
@Table(name = "leave_balance_history")

@AllArgsConstructor
@Data
public class LeaveBalanceHistory {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(name = "employee_id")
    private Long employeeId;

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
    private Long tenantId;
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


    public LeaveBalanceHistory(Long employeeId, int totalLeaveDays, int remainingLeaveDays, Long tenantId) {
        this.employeeId = employeeId;
        this.totalLeaveDays = totalLeaveDays;
        this.remainingLeaveDays = remainingLeaveDays;
        this.tenantId = tenantId;
    }

    public void setBudgetYear(Long budgetYearId) {
    }

    // Getters and setters

    // Constructors, getters, setters
}
