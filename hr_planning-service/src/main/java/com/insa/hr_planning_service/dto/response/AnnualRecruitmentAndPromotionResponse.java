package com.insa.hr_planning_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnnualRecruitmentAndPromotionResponse {
    private Long id;
    private Long budgetYearId;
    private Long workUnitId;
    private Long hrNeedRequestId;
    private Long tenantId;
    private Integer grandTotal;
    private String remark;
    private LocalDate processedDate;
    private String preparedBy;
    private String comment;
    private Boolean internalRecruitment;
    private Boolean externalRecruitment;
    private Boolean all;
}
