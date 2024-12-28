package com.training_service.dto.response;

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
public class InternshipPaymentResponse extends BaseResponse {

    private String referenceLetter;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double paymentAmount;
    private String remark;
    private UUID internId;
}
