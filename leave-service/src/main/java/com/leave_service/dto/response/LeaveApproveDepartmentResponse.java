package com.leave_service.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class LeaveApproveDepartmentResponse {
    private UUID id;
    private String decision;
    private int approvedDays;
    private String comment;
   // private int approveDays;
    private UUID leaveRequestId;
    private UUID tenantId;
}
