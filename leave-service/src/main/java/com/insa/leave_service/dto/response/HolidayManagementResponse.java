package com.insa.leave_service.dto.response;

import com.insa.leave_service.entity.Holiday;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayManagementResponse {
    private Long id;
    private Long budgetYearId;
    private Long holidayId;
    private LocalDate date;
    private Long tenantId;

}

