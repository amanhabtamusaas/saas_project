package com.insa.leave_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveTypeResponse {
    private Long id;
    private String leaveTypeName;
    private Long tenantId;
}
