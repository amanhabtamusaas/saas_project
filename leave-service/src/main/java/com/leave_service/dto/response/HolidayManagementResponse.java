package com.leave_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayManagementResponse {
    private UUID id;
    private UUID budgetYearId;
    private UUID holidayId;
    private LocalDate date;
    private UUID tenantId;

}

