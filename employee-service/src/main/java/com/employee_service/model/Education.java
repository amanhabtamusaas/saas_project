package com.employee_service.model;

import com.employee_service.enums.EducationType;
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
public class Education extends Base {

    @NotNull(message = "Education level id cannot be null")
    private UUID educationLevelId;

    @NotNull(message = "Education type cannot be null")
    @Enumerated(EnumType.STRING)
    private EducationType educationType;

    @NotNull(message = "Field of study id cannot be blank")
    private UUID fieldOfStudyId;

    @NotBlank(message = "Institution name cannot be blank")
    private String institution;

    @PastOrPresent(message = "Start date must be in the past or present")
    private LocalDate startDate;

    @PastOrPresent(message = "End date must be in the past or present")
    private LocalDate endDate;

    @Column(name = "award")
    private String award;

    @DecimalMin(value = "0.0", message = "Result must be a non-negative value")
    @DecimalMax(value = "100.0", message = "Result cannot exceed 100")
    private Double result;

    private String fileName;
    private String fileType;
    @Lob
    @Column(length = 50000000)
    private byte[] fileBytes;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false, updatable = false)
    private Employee employee;
}
