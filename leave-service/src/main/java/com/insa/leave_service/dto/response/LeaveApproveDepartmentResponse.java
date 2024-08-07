package com.insa.leave_service.dto.response;

import lombok.Data;

@Data
public class LeaveApproveDepartmentResponse {
    private Long id;
    private String decision;
    private int approvedDays;
    private String comment;
   // private int approveDays;
    private Long leaveRequestId;
    private Long tenantId;
}
