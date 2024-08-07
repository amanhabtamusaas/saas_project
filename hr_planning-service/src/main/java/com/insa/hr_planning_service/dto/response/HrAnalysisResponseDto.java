package com.insa.hr_planning_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HrAnalysisResponseDto {
    private Long id;
    private Long budgetYearId;
    private Long workUnitId;
    private Long jobRegistrationId;
    private Long tenantId;
    private Long hrNeedRequestId; // Reference to HrNeedRequest
    private LocalDate analysedOn;
    private LocalDate processedDate;
    private String processedBy;
    private String comment;
}
