package com.insa.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentApproveRequest {

    @NotBlank(message = "Vacancy number cannot be blank")
    private String vacancyNumber;

    @Min (value = 0, message = "Total requested employees must be not negative")
    private Integer numberOfEmployeesApproved;

    @NotNull(message = "decision cannot be null")
    private String decision;

    private String remark;
}
