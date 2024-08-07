package com.insa.dto.apiDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetYearDto {
    private Long id;
    private String budgetYear;
    private boolean isActive;
    private String description;
    private Long tenantId;
}
