package com.leave_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayManagementRequest {
    private UUID budgetYearId;
    private LocalDate date;
    private UUID holidayId;
}
