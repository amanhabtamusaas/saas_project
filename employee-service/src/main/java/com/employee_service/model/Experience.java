package com.employee_service.model;

import com.employee_service.enums.EmploymentType;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Experience extends Base {

    @NotBlank(message = "Institution name cannot be blank")
    private String institution;

    @NotNull(message = "Employment type cannot be null")
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @NotBlank(message = "Job title cannot be blank")
    private String jobTitle;

    @DecimalMin(value = "0.0", message = "Salary must be a non-negative value")
    private Double salary;

    @NotNull(message = "Start date cannot be null")
    @Past(message = "Start date must be in the past")
    private LocalDate startDate;

    @NotNull(message = "Start date cannot be null")
    @PastOrPresent(message = "End date must be in the past or present")
    private LocalDate endDate;

    @Column(name = "duration", nullable = false)
    private String duration;

    @NotBlank(message = "Responsibility description cannot be blank")
    private String responsibility;

    @Column(name = "reason_for_termination")
    private String reasonForTermination;

    private String fileName;
    private String fileType;
    @Lob
    @Column(length = 50000000)
    private byte[] fileBytes;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false, updatable = false)
    private Employee employee;

    @AssertTrue(message = "End date must be after or the same day as start date")
    public boolean isEndDateValid() {
        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null cases
        }
        return !endDate.isBefore(startDate);
    }
}
