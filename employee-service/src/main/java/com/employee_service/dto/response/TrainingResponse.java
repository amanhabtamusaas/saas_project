package com.employee_service.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
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
public class TrainingResponse extends BaseResponse {
    private String trainingTitle;
    private String institution;
    private String sponsoredBy;
    private LocalDate startDate;
    private LocalDate endDate;
    private UUID employeeId;
    private String fileName;
    private String fileType;
    private byte[] fileBytes;
}
