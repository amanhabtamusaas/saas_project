package com.recruitment_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "applicant_training")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training extends Base{

    @NotBlank(message = "Training title cannot be blank")
    private String trainingTitle;

    @NotBlank(message = "Institution name cannot be blank")
    private String institution;

    @NotBlank(message = "Sponsored by field cannot be blank")
    private String sponsoredBy;

    @NotNull(message = "Start date cannot be null")
    @PastOrPresent(message = "Start date must be in the past or present")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    @PastOrPresent(message = "End date must be in the past or present")
    private LocalDate endDate;

    private String certificateName;
    private String certificateType;
    @Lob
    @Column(length = 50000000)
    private byte[] certificateBytes;

    private String award;

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
