package com.recruitment_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true, exclude = "applicant")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamResult extends Base {

    @Min(value = 0, message = "Written exam score cannot be negative")
    @Max(value = 100, message = "Written exam score cannot exceed 100")
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

    @Min(value = 0, message = "Practical exam score cannot be negative")
    @Max(value = 100, message = "Practical exam score cannot exceed 100")
    private Double practicalExam;

    @Min(value = 0, message = "Other score cannot be negative")
    @Max(value = 100, message = "Other score cannot exceed 100")
    private Double other;

    @Min(value = 0, message = "Total score cannot be negative")
    @Max(value = 100, message = "Total score cannot exceed 100")
    private Double total;

    @OneToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    @ManyToOne
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @ManyToOne
    @JoinColumn(name = "assessment_weight_id")
    private AssessmentWeight assessmentWeight;
}
