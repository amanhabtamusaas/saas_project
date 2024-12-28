package com.employee_service.dto.request;

import com.employee_service.enums.EmploymentType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceRequest {

    @NotBlank(message = "Institution name cannot be blank")
    private String institution;

    @NotNull(message = "Employment type cannot be null")
    private String employmentType;

    @NotBlank(message = "Job title cannot be blank")
    private String jobTitle;

    @DecimalMin(value = "0.0", message = "Salary must be a non-negative value")
    private Double salary;

    @Past(message = "Start date must be in the past")
    private LocalDate startDate;

    @PastOrPresent(message = "End date must be in the past or present")
    private LocalDate endDate;

    @NotBlank(message = "Responsibility description cannot be blank")
    private String responsibility;

    private String reasonForTermination;
}
