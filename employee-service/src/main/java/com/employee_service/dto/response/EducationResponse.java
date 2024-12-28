package com.employee_service.dto.response;

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
public class EducationResponse extends BaseResponse{
    private UUID educationLevelId;
    private String educationType;
    private UUID fieldOfStudyId;
    private String institution;
    private LocalDate startDate;
    private LocalDate endDate;
    private String award;
    private Double result;
    private String fileName;
    private String fileType;
    private byte[] fileBytes;
    private UUID employeeId;
}
