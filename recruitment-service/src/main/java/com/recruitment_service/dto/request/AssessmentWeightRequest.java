package com.recruitment_service.dto.request;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentWeightRequest {
    @Min(value = 0, message = "Written score cannot be negative")
    @Max(value = 100, message = "Written score cannot exceed 100")
    private Double writtenExam;

    @Min(value = 0, message = "Interview score cannot be negative")
    @Max(value = 100, message = "Interview score cannot exceed 100")
    private Double interview;

    @Min(value = 0, message = "CGPA score cannot be negative")
    @Max(value = 100, message = "CGPA score cannot exceed 100")
    private Double CGPA;

    @Min(value = 0, message = "Experience score cannot be negative")
    @Max(value = 100, message = "Experience score cannot exceed 100")
    private Double experience;

    @Min(value = 0, message = "Experience score cannot be negative")
    @Max(value = 100, message = "Experience score cannot exceed 100")
    private Double practicalExam;

    @Min(value = 0, message = "Other score cannot be negative")
    @Max(value = 100, message = "Other score cannot exceed 100")
    private Double other;

    @NotNull(message = "Recruitment cannot be null")
    private UUID recruitmentId;
}
