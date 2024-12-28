package com.recruitment_service.model;

import com.recruitment_service.enums.ExperienceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "applicant_experience")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Experience extends Base {

    @NotBlank(message = "Institution name cannot be blank")
    private String institution;

    @NotBlank(message = "Location cannot be blank")
    private UUID locationId;

    @NotBlank(message = "Job title cannot be blank")
    private String jobTitle;

    @DecimalMin(value = "0.0", message = "Salary must be a non-negative value")
    private Double salary;

    @NotBlank(message = "Responsibility description cannot be blank")
    private String responsibility;

    @NotNull(message = "Employment type cannot be null")
    @Enumerated(EnumType.STRING)
    private ExperienceType experienceType;

    @NotNull(message = "Start date cannot be null")
    @Past(message = "Start date must be in the past")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    @PastOrPresent(message = "End date must be in the past or present")
    private LocalDate endDate;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "reason_for_termination")
    private String reasonForTermination;

    private String fileName;
    private String fileType;
    @Lob
    @Column(length = 50000000)
    private byte[] fileBytes;

    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false, updatable = false)
    private Applicant applicant;

    @AssertTrue(message = "End date must be after or the same day as start date")
    public boolean isEndDateValid() {
        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null cases
        }
        return !endDate.isBefore(startDate);
    }
}
