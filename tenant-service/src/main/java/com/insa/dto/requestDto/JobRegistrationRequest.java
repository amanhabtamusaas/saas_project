package com.insa.dto.requestDto;

import com.insa.enums.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long departmentId;
    private Long educationLevelId;
    private Long jobCategoryId;
    private Long jobGradeId;
    private Long workUnitId;
    private Long qualificationId;



    // Getters and Setters
    // Constructors
}

