package com.insa.leave_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDto {
    private Long experienceId;
    private Long tenantId;
    private String institution;
    private String employmentType;
    private String jobTitle;
    private Double salary;
    private LocalDate startDate;
    private LocalDate endDate;
    private String duration;
    private String responsibility;
    private String reasonForTermination;
    private String fileName;
    private String fileType;
    private byte[] fileBytes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Long employeeId;
}
