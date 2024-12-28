package com.hr_planning_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnnualRecruitmentAndPromotionResponse {
    private UUID id;
    private UUID budgetYearId;
    private UUID workUnitId;
    private UUID hrNeedRequestId;
    private UUID tenantId;
    private Integer grandTotal;
    private String remark;
    private LocalDate processedDate;
    private String preparedBy;
    private String comment;
    private Boolean internalRecruitment;
    private Boolean externalRecruitment;
    private Boolean all;
}
