package com.insa.dto.request;

import com.insa.entity.PreServiceCourse;
import com.insa.entity.PreServiceTrainee;
import com.insa.enums.Decision;
import com.insa.enums.Semester;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
