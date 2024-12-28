package com.recruitment_service.dto.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentRequest {
    @NotNull(message = "Requester employee id cannot be null")
    private String requesterEmployeeId;

    @NotNull(message = "Department id cannot be null")
    private UUID departmentId;

    @NotNull(message = "Job id cannot be null")
    private UUID jobId;

    @Min(value = 1, message = "Number of requested employees must be minimum 1")
    private Integer numberOfEmployeesRequested;

    @NotNull(message = "Recruitment type cannot be null")
    private String recruitmentType;

    @NotNull(message = "Recruitment mode cannot be null")
    private String recruitmentMode;

    private String remark;
}
