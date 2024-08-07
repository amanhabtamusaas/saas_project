package com.insa.leave_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "budget_years")
public class BudgetYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Start Year is required")
    @Column(name = "Start Year", nullable = false)
    private String budgetYear;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    @Column(name = "description")
    private String description;
    @NotNull(message = "Tenant ID is required")
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;
    @OneToMany(mappedBy = "budgetYear", fetch = FetchType.LAZY)
    private List<HolidayManagement> holidayManagements;
    @OneToMany(mappedBy = "budgetYear", fetch = FetchType.LAZY)
    private List<LeaveBalance> leaveBalances;
    @OneToMany(mappedBy = "budgetYear", fetch = FetchType.LAZY)
    private List<LeaveBalanceHistory> leaveBalanceHistories;
    @OneToMany(mappedBy = "budgetYear", fetch = FetchType.LAZY)
    private List<LeaveRequest> leaveRequests;

}
