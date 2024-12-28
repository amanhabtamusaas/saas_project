package com.training_service.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternshipStudentRequest {

    @NotNull(message = "Budget year id cannot be null")
    private UUID budgetYearId;

    @NotNull(message = "Semester cannot be null")
    private String semester;

    @NotNull(message = "University id cannot be null")
    private UUID universityId;

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be in future or present")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    @FutureOrPresent(message = "End date must be in future or present")
    private LocalDate endDate;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Middle name cannot be blank")
    private String middleName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    private String IdNumber;

    @NotNull(message = "phoneNumber cannot be null")
    @Pattern(regexp = "\\+?[0-9. ()-]{7,25}", message = "Invalid phoneNumber number")
    private String phoneNumber;

    @NotBlank(message = "Stream cannot be blank")
    private String stream;

    @NotNull(message = "Location id cannot be null")
    private UUID locationId;

    private String remark;
}
