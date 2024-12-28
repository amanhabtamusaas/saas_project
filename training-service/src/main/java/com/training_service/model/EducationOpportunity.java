package com.training_service.model;

import com.training_service.enums.TrainingLocation;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationOpportunity extends Base{

    @NotNull(message = "Budget year id cannot be null")
    private UUID budgetYearId;

    @NotNull(message = "Training location cannot be null")
    @Enumerated(EnumType.STRING)
    private TrainingLocation trainingLocation;

    @NotBlank(message = "Country cannot be blank")
    private String country;

    @NotNull(message = "Education level id cannot be null")
    private UUID educationLevelId;

    @NotNull(message = "Qualification id cannot be null")
    private UUID qualificationId;

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

    @NotNull(message = "Employee id cannot be null")
    private UUID employeeId;

    @NotNull(message = "Total result cannot be null")
    private Double totalResult;

    @AssertTrue(message = "End date must be after or the same day as start date")
    public boolean isEndDateValid() {
        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null cases
        }
        return !endDate.isBefore(startDate);
    }
}
