package com.insa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private Long internId;
}
