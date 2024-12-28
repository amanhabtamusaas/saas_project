package com.training_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationOpportunityResponse extends BaseResponse {

    private UUID budgetYearId;
    private UUID employeeId;
    private String trainingLocation;
    private String country;
    private UUID educationLevelId;
    private UUID qualificationId;
    private String sponsor;
    private String institution;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate letterDate;
    private String letterReferenceNumber;
    private String remark;
    private Double totalResult;
}
