package com.leave_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveTypeResponse {
    private UUID id;
    private String leaveTypeName;
    private UUID tenantId;
}
