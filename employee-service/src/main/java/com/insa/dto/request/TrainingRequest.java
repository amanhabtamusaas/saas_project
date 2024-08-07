package com.insa.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRequest {

    @NotBlank(message = "Training title cannot be blank")
    private String trainingTitle;

    @NotBlank(message = "Institution name cannot be blank")
    private String institution;

    @NotBlank(message = "Sponsored by field cannot be blank")
    private String sponsoredBy;

    @PastOrPresent(message = "Start date must be in the past or present")
    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    @PastOrPresent(message = "End date must be in the past or present")
    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;
}
