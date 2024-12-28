package com.leave_service.dto.request;

import com.leave_service.enums.LeaveMonth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveScheduleRequest {
    private UUID employeeId;
    private LeaveMonth leaveMonth;
    private String description;
    //private UUID tenantId;
}
