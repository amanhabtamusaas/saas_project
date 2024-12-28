package com.leave_service.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class HolidayManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "budget-year_id")
    private BudgetYear budgetYear;

    @ManyToOne
    @JoinColumn(name = "holiday_id")
    private Holiday holiday;
    private UUID tenantId;

}
