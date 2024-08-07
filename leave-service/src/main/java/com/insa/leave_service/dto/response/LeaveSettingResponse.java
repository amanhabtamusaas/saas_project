package com.insa.leave_service.dto.response;

import com.insa.leave_service.enums.EmploymentType;
import com.insa.leave_service.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveSettingResponse {
    private Long id;
    private Gender gender;
    private EmploymentType employmentType;
    private int minimumDays;
    private int maximumDays;
    private String remark;
    private Long leaveTypeId;
    private Boolean toBalance;
    private Boolean escapeSunday;
    private Boolean escapeSaturday;
    private Boolean escapeHoliday;
    private Long tenantId;
}
