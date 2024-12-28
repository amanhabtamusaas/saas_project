package com.training_service.dto.response;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AnnualTrainingPlanResponse extends BaseResponse{

    private UUID departmentId;
    private UUID budgetYearId;
    private Integer numberOfParticipants;
    private Integer numberOfDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double costPerPerson;
    private Integer round;
    private String venue;
    private UUID courseCategoryId;
    private UUID trainingCourseId;
    private UUID trainingInstitutionId;
    private String remark;
}
