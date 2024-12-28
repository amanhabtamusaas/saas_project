package com.leave_service.dto.response;

import com.leave_service.enums.EmploymentType;
import com.leave_service.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveSettingResponse {
    private UUID id;
    private Gender gender;
    private EmploymentType employmentType;
    private int minimumDays;
    private int maximumDays;
    private String remark;
    private UUID leaveTypeId;
    private Boolean toBalance;
    private Boolean escapeSunday;
    private Boolean escapeSaturday;
    private Boolean escapeHoliday;
    private UUID tenantId;
}
