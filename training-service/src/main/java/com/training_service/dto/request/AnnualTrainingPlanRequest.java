package com.training_service.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnualTrainingPlanRequest {

    @NotNull(message = "Department id cannot be null")
    private UUID departmentId;

    @NotNull(message = "Budget year id cannot be null")
    private UUID budgetYearId;

    @NotNull(message = "Number of participants cannot be null")
    private Integer numberOfParticipants;

    @NotNull(message = "Number of trainees cannot be null")
    private Integer numberOfDays;

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be in future or present")
    private LocalDate startDate;

    @FutureOrPresent(message = "Start date must be in future or present")
    private LocalDate endDate;

    @NotNull(message = "Cost per person cannot be null")
    @DecimalMin(value = "0.0", message = "Cost per person must be non-negative")
    private Double costPerPerson;

    @NotNull(message = "Round cannot be null")
    private Integer round;

    @NotBlank(message = "Venue cannot be blank")
    private String venue;

    @NotNull(message = "Course category id cannot be null")
    private UUID courseCategoryId;

    @NotNull(message = "AnnualTraining course id cannot be null")
    private UUID trainingCourseId;

    @NotNull(message = "training institution id cannot be null")
    private UUID trainingInstitutionId;

    private String remark;
}
