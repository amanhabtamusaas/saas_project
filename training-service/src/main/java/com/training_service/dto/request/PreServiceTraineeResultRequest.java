package com.training_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreServiceTraineeResultRequest {

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;

    @NotNull(message = "Semester cannot be null")
    private String semester;

    @NotNull(message = "Result cannot be null")
    private Double result;

    @NotNull(message = "Decision cannot be null")
    private String decision;

    private String remark;
}
