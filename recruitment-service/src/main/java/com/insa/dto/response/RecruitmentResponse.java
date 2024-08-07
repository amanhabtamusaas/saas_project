package com.insa.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentResponse extends BaseResponse {
    private Long requesterEmployeeId;
    private Long departmentId;
    private Long jobId;
    private String vacancyNumber;
    private Integer numberOfEmployeesRequested;
    private Integer numberOfEmployeesApproved;
    private String recruitmentType;
    private String recruitmentMode;
    private String recruitmentStatus;
    private String remark;
}
