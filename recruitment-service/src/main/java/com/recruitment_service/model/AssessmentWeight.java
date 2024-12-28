package com.recruitment_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true, exclude = "recruitment")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentWeight extends Base {

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

    @Min(value = 100, message = "Total score must be 100")
    private Double total;

    @OneToOne
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @OneToMany(mappedBy = "assessmentWeight", fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<ExamResult> examResults;
}
