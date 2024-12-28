package com.leave_service.model;
import jakarta.persistence.*;
import com.leave_service.enums.LeaveMonth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class LeaveSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private UUID employeeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "leaveMonth")
    @NotNull
    private LeaveMonth leaveMonth;

    @Size(max = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "budgetYear_id")
    private BudgetYear budgetYear;

    private UUID tenantId;
}
