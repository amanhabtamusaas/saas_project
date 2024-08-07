package com.insa.entity;

import com.insa.enums.ExperienceType;
import com.insa.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortlistCriteria extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shortlist_criteria_id", unique = true, nullable = false)
    private Long id;

    @NotBlank(message = "Criteria name cannot be blank")
    private String criteriaName;

    @NotNull(message = "Gender cannot be null")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "Education  id cannot be null")
    private Long educationLevelId;

    @NotBlank(message = "Training or certificate cannot be blank")
    private String trainingOrCertificate;

    @NotNull(message = "Experience type cannot be null")
    @Enumerated(EnumType.STRING)
    private ExperienceType experienceType;

    @DecimalMin(value = "0.0", message = "CGPA must be a non-negative value")
    @DecimalMax(value = "100.0", message = "CGPA cannot exceed 100")
    private Double CGPA;

    @NotNull(message = "Minimum experience cannot be null")
    private Integer minimumExperience;

    @NotNull(message = "Minimum age cannot be null")
    private Integer minimumAge;

    @NotNull(message = "Maximum age cannot be null")
    private Integer maximumAge;

    private String other;

    @ManyToOne
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;
}
