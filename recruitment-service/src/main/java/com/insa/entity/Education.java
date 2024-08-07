package com.insa.entity;

import com.insa.enums.EducationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "applicant_education")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Education extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_id", unique = true, nullable = false)
    private Long id;

    @NotNull(message = "Education level id cannot be null")
    private Long educationLevelId;

    @NotNull(message = "Education type cannot be null")
    @Enumerated(EnumType.STRING)
    private EducationType educationType;

    @NotNull(message = "Field of study id cannot be null")
    private Long fieldOfStudyId;

    @NotBlank(message = "Institution name cannot be blank")
    private String institution;

    @NotNull(message = "Start date cannot be null")
    @PastOrPresent(message = "Start date must be in the past or present")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    @PastOrPresent(message = "End date must be in the past or present")
    private LocalDate endDate;

    @DecimalMin(value = "0.0", message = "Result must be a non-negative value")
    @DecimalMax(value = "100.0", message = "Result cannot exceed 100")
    private Double result;

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
