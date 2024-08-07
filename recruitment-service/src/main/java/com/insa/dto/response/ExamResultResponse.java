package com.insa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamResultResponse extends BaseResponse {
    private Double writtenExam;
    private Double interview;
    private Double CGPA;
    private Double experience;
    private Double practicalExam;
    private Double other;
    private Double total;
    private Long applicantId;
    private Long recruitmentId;
    private Long assessmentWeightId;
}
