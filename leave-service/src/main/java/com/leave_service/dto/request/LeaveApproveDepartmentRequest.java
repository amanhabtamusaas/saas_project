package com.leave_service.dto.request;

import com.leave_service.enums.Decision;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class LeaveApproveDepartmentRequest {
    private Decision decision;
    private int approvedDays;
    @Size(max = 1000)
    private String comment;
    //private int approveDays;
    private UUID leaveRequestId;

}
