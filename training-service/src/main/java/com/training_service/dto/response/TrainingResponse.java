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
public class TrainingResponse extends BaseResponse {

    private String trainingType;
    private UUID departmentId;
    private UUID budgetYearId;
    private UUID courseCategoryId;
    private UUID trainingCourseId;
    private UUID trainingInstitutionId;
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
