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
public class TrainingResponse extends BaseResponse {

    private String trainingType;
    private Long departmentId;
    private Long budgetYearId;
    private Long courseCategoryId;
    private Long trainingCourseId;
    private Long trainingInstitutionId;
    private Integer numberOfParticipants;
    private Integer numberOfDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double costPerPerson;
    private String sponsoredBy;
    private String trainingLocation;
    private String venue;
    private String reason;
    private String trainingStatus;
    private String remark;
}
