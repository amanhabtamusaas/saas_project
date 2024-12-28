package com.organization_service.dto.requestDto;

import com.organization_service.enums.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobRegistrationRequest {

    private String jobTitle;
    private String jobCode;
    private ReportsTo reportsTo;
    private JobType jobType;
    @NotNull(message = "Minimum experience cannot be null")
    @Min(value = 0, message = "Minimum experience must be greater than or equal to 0")
    private Integer minExperience;
    private String duties;
    private String language;
    private String skills;
    private String description;
    private String alternativeExperience;
    private String relativeExperience;
    private UUID departmentId;
    private UUID educationLevelId;
    private UUID jobCategoryId;
    private UUID jobGradeId;
    private UUID workUnitId;
    private UUID qualificationId;



    // Getters and Setters
    // Constructors
}

