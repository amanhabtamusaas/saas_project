package com.recruitment_service.dto.request;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationRequest {
    @NotNull(message = "Education level id cannot be null")
    private UUID educationLevelId;

    @NotNull(message = "Education type cannot be null")
    private String educationType;

    @NotNull(message = "Field of study id cannot be null")
    private UUID fieldOfStudyId;

    @NotBlank(message = "Institution name cannot be blank")
    private String institution;

    @PastOrPresent(message = "Start date must be in the past or present")
    private LocalDate startDate;

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
}
