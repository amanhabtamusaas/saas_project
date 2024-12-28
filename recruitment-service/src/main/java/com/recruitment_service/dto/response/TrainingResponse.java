package com.recruitment_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private String certificateName;
    private String certificateType;
    private byte[] certificateBytes;
    private String award;
    private UUID applicantId;
}
