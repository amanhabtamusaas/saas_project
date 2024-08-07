package com.insa.leave_service.dto.request;

import com.insa.leave_service.enums.LeaveMonth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveScheduleRequest {
    private Long employeeId;
    private LeaveMonth leaveMonth;
    private String description;
    //private Long tenantId;
}
