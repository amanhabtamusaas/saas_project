package com.leave_service.dto.request;

import com.leave_service.enums.EmploymentType;
import com.leave_service.enums.Gender;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveSettingRequest {
    @NotNull
    private Gender gender;
    @NotNull
    private EmploymentType employmentType;
    @NotNull
    private int minimumDays;
    @NotNull
    private int maximumDays;
    @Size(max = 255)
    private String remark;
    @NotNull
    private UUID leaveTypeId;
    private Boolean toBalance;
    private Boolean escapeSunday;
    private Boolean escapeSaturday;
    private Boolean escapeHoliday;

}
