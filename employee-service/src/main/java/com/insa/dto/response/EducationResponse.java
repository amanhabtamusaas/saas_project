package com.insa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationResponse extends BaseResponse{
    private Long educationLevelId;
    private String educationType;
    private Long fieldOfStudyId;
    private String institution;
    private LocalDate startDate;
    private LocalDate endDate;
    private String award;
    private Double result;
    private String fileName;
    private String fileType;
    private byte[] fileBytes;
    private Long employeeId;
}
