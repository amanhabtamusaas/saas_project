package com.recruitment_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

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
    private UUID applicantId;
    private UUID recruitmentId;
    private UUID assessmentWeightId;
}
