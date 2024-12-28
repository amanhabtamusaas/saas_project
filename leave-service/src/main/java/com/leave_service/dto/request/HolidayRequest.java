package com.leave_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayRequest {
    @NotBlank(message = "Holiday name is required")
    @Size(max = 255, message = "Holiday name cannot exceed 255 characters")
    private String holidayName;

//    @NotNull(message = "Date is required")
//    private LocalDate date;
}
