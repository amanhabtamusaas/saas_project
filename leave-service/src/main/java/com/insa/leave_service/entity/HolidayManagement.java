package com.insa.leave_service.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class HolidayManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(nullable = false)
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "budget-year_id")
    private BudgetYear budgetYear;
    @ManyToOne
    @JoinColumn(name = "holiday_id")
    private Holiday holiday;
    private Long tenantId;

}
