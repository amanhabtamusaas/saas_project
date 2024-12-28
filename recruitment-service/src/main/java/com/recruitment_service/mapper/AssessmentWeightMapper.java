package com.recruitment_service.mapper;

import com.recruitment_service.dto.clientDto.TenantDto;
import com.recruitment_service.dto.request.AssessmentWeightRequest;
import com.recruitment_service.dto.response.AssessmentWeightResponse;
import com.recruitment_service.model.AssessmentWeight;
import com.recruitment_service.model.Recruitment;
import com.recruitment_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssessmentWeightMapper {

    private final ValidationUtil validationUtil;

    public AssessmentWeight mapToEntity(TenantDto tenant,
                                        Recruitment recruitment,
                                        AssessmentWeightRequest request) {

        AssessmentWeight assessmentWeight = new AssessmentWeight();
        assessmentWeight.setTenantId(tenant.getId());
        assessmentWeight.setRecruitment(recruitment);
        assessmentWeight.setWrittenExam(request.getWrittenExam());
        assessmentWeight.setInterview(request.getInterview());
        assessmentWeight.setCGPA(request.getCGPA());
        assessmentWeight.setExperience(request.getExperience());
        assessmentWeight.setPracticalExam(request.getPracticalExam());
        assessmentWeight.setOther(request.getOther());

        assessmentWeight.setTotal(calculateTotalScore(
                request.getWrittenExam(), request.getInterview(),
                request.getCGPA(), request.getExperience(), request.getPracticalExam(), request.getOther()));

        return assessmentWeight;
    }

    public AssessmentWeightResponse mapToDto(AssessmentWeight assessmentWeight) {

        AssessmentWeightResponse response = new AssessmentWeightResponse();
        response.setId(assessmentWeight.getId());
        response.setWrittenExam(assessmentWeight.getWrittenExam());
        response.setInterview(assessmentWeight.getInterview());
        response.setCGPA(assessmentWeight.getCGPA());
        response.setExperience(assessmentWeight.getExperience());
        response.setPracticalExam(assessmentWeight.getPracticalExam());
        response.setOther(assessmentWeight.getOther());
        response.setTotal(assessmentWeight.getTotal());
        response.setRecruitmentId(assessmentWeight.getRecruitment().getId());
        response.setTenantId(assessmentWeight.getTenantId());
        response.setCreatedAt(assessmentWeight.getCreatedAt());
        response.setCreatedBy(assessmentWeight.getCreatedBy());
        response.setUpdatedAt(assessmentWeight.getUpdatedAt());
        response.setUpdatedBy(assessmentWeight.getUpdatedBy());

        return response;
    }

    public AssessmentWeight updateAssessmentWeight(TenantDto tenant,
                                                   AssessmentWeight assessmentWeight,
                                                   AssessmentWeightRequest request) {

        Recruitment recruitment = validationUtil
                .getRecruitmentById(tenant.getId(), request.getRecruitmentId());

        if (request.getRecruitmentId() != null) {
            assessmentWeight.setRecruitment(recruitment);
        }
        if (request.getWrittenExam() != null) {
            assessmentWeight.setWrittenExam(request.getWrittenExam());
        }
        if (request.getInterview() != null) {
            assessmentWeight.setInterview(request.getInterview());
        }
        if (request.getCGPA() != null) {
            assessmentWeight.setCGPA(request.getCGPA());
        }
        if (request.getExperience() != null) {
            assessmentWeight.setExperience(request.getExperience());
        }
        if (request.getPracticalExam() != null) {
            assessmentWeight.setPracticalExam(request.getPracticalExam());
        }
        if (request.getOther() != null) {
            assessmentWeight.setOther(request.getOther());
        }

        assessmentWeight.setTotal(calculateTotalScore(
                request.getWrittenExam(), request.getInterview(),
                request.getCGPA(), request.getExperience(), request.getPracticalExam(), request.getOther()));

        return assessmentWeight;
    }

    private Double calculateTotalScore(Double written, Double interview,
                                       Double CGPA, Double experience,
                                       Double practicalExam, Double other) {

        double total = written + interview + CGPA + experience + practicalExam + other;
        if (total < 100 || total > 100) {
            throw new IllegalArgumentException("Total score must be 100");
        }
        return total;
    }
}
