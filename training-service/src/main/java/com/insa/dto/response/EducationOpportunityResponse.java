package com.insa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationOpportunityResponse extends BaseResponse {

    private Long budgetYearId;
    private Long employeeId;
    private String trainingLocation;
    private String country;
    private Long educationLevelId;
    private Long qualificationId;
    private String sponsor;
    private String institution;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate letterDate;
    private String letterReferenceNumber;
    private String remark;
    private Double totalResult;
}
