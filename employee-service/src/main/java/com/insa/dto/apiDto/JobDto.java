package com.insa.dto.apiDto;

import com.insa.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {
    private Long id;
    private String jobTitle;
    private String jobCode;
    private String reportsTo;
    private String jobType;
    private Integer minExperience;
    private String duties;
    private String language;
    private String skills;
    private String description;
    private String alternativeExperience;
    private String relativeExperience;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Long tenantId;
    private Long educationLevelId;
    private Long jobCategoryId;
    private Long jobGradeId;
    private Long workUnitId;
    private Long qualificationId;
}
