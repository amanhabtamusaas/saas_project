package com.insa.dto.response;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnualTrainingPlanResponse extends BaseResponse{

    private Long departmentId;
    private Long budgetYearId;
    private Integer numberOfParticipants;
    private Integer numberOfDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double costPerPerson;
    private Integer round;
    private String venue;
    private Long courseCategoryId;
    private Long trainingCourseId;
    private Long trainingInstitutionId;
    private String remark;
}
