package com.insa.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayGradeRequest {
    private Long jobGradeId;
    private Double initialSalary;
    private Double maximumSalary;
    private Integer salaryStep;
    private Double salary;
    private String description;
}
