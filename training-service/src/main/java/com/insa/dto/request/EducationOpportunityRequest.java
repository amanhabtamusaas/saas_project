package com.insa.dto.request;

import com.insa.enums.TrainingLocation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationOpportunityRequest {

    @NotNull(message = "Budget year id cannot be null")
    private Long budgetYearId;

    @NotNull(message = "Training location cannot be null")
    private String trainingLocation;

    @NotBlank(message = "Country cannot be blank")
    private String country;

    @NotNull(message = "Education level id cannot be null")
    private Long educationLevelId;

    @NotNull(message = "Qualification id cannot be null")
    private Long qualificationId;

    @NotBlank(message = "Sponsor cannot be blank")
    private String sponsor;

    @NotBlank(message = "Institution cannot be blank")
    private String institution;

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be in future or present")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    @FutureOrPresent(message = "End date must be in future or present")
    private LocalDate endDate;

    @NotNull(message = "Letter date cannot be null")
    @PastOrPresent(message = "Letter date must be in past or present")
    private LocalDate letterDate;

    @NotBlank(message = "Letter reference number cannot be blank")
    private String letterReferenceNumber;

    private String remark;

    @NotBlank(message = "Employee id cannot be blank")
    private String employeeId;

    @NotNull(message = "Total result cannot be null")
    private Double totalResult;
}
