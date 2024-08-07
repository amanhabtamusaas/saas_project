package com.insa.hr_planning_service.dto.request;

import com.insa.hr_planning_service.enums.EmploymentType;
import com.insa.hr_planning_service.enums.HowToBeFilled;
import com.insa.hr_planning_service.enums.WhenToBe;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeedRequestRequestDto {

    private Integer noOfPosition;
    private EmploymentType employmentType;
    private HowToBeFilled howToBeFilled;
    private WhenToBe whenToBe;
    private String remark;
    private Long budgetYearId;
    private Long departmentId;
    private Long staffPlanId;


}
