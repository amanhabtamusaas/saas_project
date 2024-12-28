package com.recruitment_service.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentResponse extends BaseResponse {
    private UUID requesterEmployeeId;
    private UUID departmentId;
    private UUID jobId;
    private String vacancyNumber;
    private Integer numberOfEmployeesRequested;
    private Integer numberOfEmployeesApproved;
    private String recruitmentType;
    private String recruitmentMode;
    private String recruitmentStatus;
    private String remark;
}
