package com.insa.leave_service.dto.request;

import com.insa.leave_service.enums.Day;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestRequest {

    private Long employeeId;
    private int numberOfDays;
    private LocalDate leaveStart;
    private Day day;
    private LocalDate returnDate;
    private String description;
    private Long leaveTypeId;
    private Long budgetYearId;

}
