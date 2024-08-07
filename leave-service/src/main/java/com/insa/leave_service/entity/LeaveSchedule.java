package com.insa.leave_service.entity;
import jakarta.persistence.*;
import com.insa.leave_service.enums.LeaveMonth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Year;

@Entity
@Data
@NoArgsConstructor
public class LeaveSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long employeeId;
    @Enumerated(EnumType.STRING)
    @Column(name = "leaveMonth")
    @NotNull
    private LeaveMonth leaveMonth;
    @Size(max = 1000)
    private String description;
    @ManyToOne
    @JoinColumn(name = "budgetYear_id")
    private BudgetYear budgetYear;
    private Long tenantId;
}
