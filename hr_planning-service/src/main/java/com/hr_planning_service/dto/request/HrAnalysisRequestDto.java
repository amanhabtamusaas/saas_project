package com.hr_planning_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HrAnalysisRequestDto {
    private UUID budgetYearId;
    private UUID workUnitId;
    private UUID jobRegistrationId;
    private UUID hrNeedRequestId;
    private String processedBy;
    private String comment;
}
