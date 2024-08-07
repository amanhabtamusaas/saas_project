package com.insa.dto.request;

import jakarta.persistence.Column;
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

    @NotBlank(message = "Location cannot be blank")
    private Long locationId;

    @NotBlank(message = "Job title cannot be blank")
    private String jobTitle;

    @DecimalMin(value = "0.0", message = "Salary must be a non-negative value")
    private Double salary;

    @NotBlank(message = "Responsibility description cannot be blank")
    private String responsibility;

    @NotNull(message = "Employment type cannot be null")
    private String experienceType;

    @Past(message = "Start date must be in the past")
    private LocalDate startDate;

    @PastOrPresent(message = "End date must be in the past or present")
    private LocalDate endDate;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "reason_for_termination")
    private String reasonForTermination;

    private String fileName;
    private String fileType;
    private byte[] fileBytes;
}
