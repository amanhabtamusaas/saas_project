package com.insa.hr_planning_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HrAnalysisRequestDto {
    private Long budgetYearId;
    private Long workUnitId;
    private Long jobRegistrationId;
    private Long hrNeedRequestId;
    private String processedBy;
    private String comment;
}
