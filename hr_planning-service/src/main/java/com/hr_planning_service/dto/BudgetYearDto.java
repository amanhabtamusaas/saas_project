package com.hr_planning_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetYearDto {
    private UUID id;
    private String budgetYear;
    private boolean isActive;
    private String description;
    private UUID tenantId;
}
