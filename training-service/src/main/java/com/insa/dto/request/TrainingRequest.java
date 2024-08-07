package com.insa.dto.request;

import com.insa.enums.TrainingType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRequest {

    @NotNull(message = "Training type cannot be null")
    private String trainingType;

    @NotNull(message = "Department id cannot be null")
    private Long departmentId;

    @NotNull(message = "Budget year id cannot be null")
    private Long budgetYearId;

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

    @NotBlank(message = "Sponsored by cannot be blank")
    private String sponsoredBy;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "AnnualTraining location cannot be null")
    private String trainingLocation;

    @NotBlank(message = "reason cannot be blank")
    private String reason;

    @NotBlank(message = "Venue cannot be blank")
    private String venue;

    @NotNull(message = "Course category id cannot be null")
    private Long courseCategoryId;

    @NotNull(message = "AnnualTraining course id cannot be null")
    private Long trainingCourseId;

    @NotNull(message = "training institution id cannot be null")
    private Long trainingInstitutionId;

    private String remark;
}
