package com.recruitment_service.dto.clientDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {
    private UUID id;
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
    private UUID tenantId;
    private UUID educationLevelId;
    private UUID jobCategoryId;
    private UUID jobGradeId;
    private UUID workUnitId;
    private UUID qualificationId;
}
