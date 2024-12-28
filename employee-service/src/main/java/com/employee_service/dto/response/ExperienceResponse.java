package com.employee_service.dto.response;

import com.employee_service.enums.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceResponse extends BaseResponse{
    private String institution;
    private String employmentType;
    private String jobTitle;
    private Double salary;
    private LocalDate startDate;
    private LocalDate endDate;
    private String duration;
    private String responsibility;
    private String reasonForTermination;
    private String fileName;
    private String fileType;
    private byte[] fileBytes;
    private UUID employeeId;
}
