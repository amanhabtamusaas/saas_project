package com.organization_service.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayGradeRequest {
    private UUID jobGradeId;
    private Double initialSalary;
    private Double maximumSalary;
    private Integer salaryStep;
    private Double salary;
    private String description;
}
