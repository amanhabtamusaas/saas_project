package com.insa.leave_service.dto.response;

import com.insa.leave_service.enums.LeaveMonth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveScheduleResponse {
    private Long id;
    private Long employeeId;
    private LeaveMonth leaveMonth;
    private String description;
    private Long tenantId;
}
