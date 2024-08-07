package com.insa.leave_service.dto.request;

import com.insa.leave_service.enums.Decision;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LeaveApproveDepartmentRequest {
    private Decision decision;
    private int approvedDays;
    @Size(max = 1000)
    private String comment;
    //private int approveDays;
    private Long leaveRequestId;

}
