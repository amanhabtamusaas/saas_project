package com.insa.leave_service.dto.response;

import com.insa.leave_service.enums.Day;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestResponse {
    private Long id;
    private Long employeeId;
    private int numberOfDays;
    private LocalDate leaveStart;
    private Day day;
    private LocalDate returnDate;
    private String description;
    private Long leaveTypeId;
    private Long budgetYearId;
    private Long tenantId;
}
