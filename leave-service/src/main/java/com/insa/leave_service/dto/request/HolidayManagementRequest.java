package com.insa.leave_service.dto.request;

import com.insa.leave_service.entity.Holiday;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayManagementRequest {
    private Long budgetYearId;
    private LocalDate date;
    private Long holidayId;
}
