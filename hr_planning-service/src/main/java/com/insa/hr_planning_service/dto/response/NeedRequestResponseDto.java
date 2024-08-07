package com.insa.hr_planning_service.dto.response;

import com.insa.hr_planning_service.enums.EmploymentType;
import com.insa.hr_planning_service.enums.HowToBeFilled;
import com.insa.hr_planning_service.enums.WhenToBe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeedRequestResponseDto {

    private Long id;
    private Integer noOfPosition;
    private EmploymentType employmentType;
    private HowToBeFilled howToBeFilled;
    private WhenToBe whenToBe;
    private String remark;
    private Long budgetYearId;
    private Long departmentId;
    private Long staffPlanId;
    private Long tenantId;
    private LocalDateTime createdAt;
    private String createdBy;

}
