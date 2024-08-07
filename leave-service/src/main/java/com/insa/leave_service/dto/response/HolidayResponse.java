package com.insa.leave_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayResponse {
    private Long id;

    private String holidayName;

//    private LocalDate date;

    private Long tenantId;
}
