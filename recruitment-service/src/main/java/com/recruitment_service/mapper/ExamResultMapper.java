package com.recruitment_service.mapper;

import com.recruitment_service.dto.clientDto.TenantDto;
import com.recruitment_service.dto.request.ExamResultRequest;
import com.recruitment_service.dto.response.ExamResultResponse;
import com.recruitment_service.model.Applicant;
import com.recruitment_service.model.AssessmentWeight;
import com.recruitment_service.model.ExamResult;
import com.recruitment_service.exception.ResourceNotFoundException;
import com.recruitment_service.model.Recruitment;
import com.recruitment_service.repository.AssessmentWeightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExamResultMapper {

    private final AssessmentWeightRepository assessmentWeightRepository;

    public ExamResult mapToEntity(TenantDto tenant,
                                  Recruitment recruitment,
                                  Applicant applicant,
                                  ExamResultRequest request) {

        AssessmentWeight assessmentWeight = assessmentWeightRepository
                .findById(request.getAssessmentWeightId())
                .filter(ass -> ass.getTenantId().equals(tenant.getId()))
                .filter(ass -> ass.getRecruitment().equals(recruitment))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Assessment weight not found with id '" + request.getAssessmentWeightId() + "'"));

        ExamResult examResult = new ExamResult();
        examResult.setTenantId(tenant.getId());
        examResult.setRecruitment(recruitment);
        examResult.setApplicant(applicant);
        examResult.setAssessmentWeight(assessmentWeight);;
        if (request.getWrittenExam() <= assessmentWeight.getWrittenExam()) {
            examResult.setWrittenExam(request.getWrittenExam());
        } else {
            throw new IllegalArgumentException(
                    "Written exam score must be less than or equal to " +
                            assessmentWeight.getWrittenExam());
        }
        if (request.getInterview() <= assessmentWeight.getInterview()) {
            examResult.setInterview(request.getInterview());
        } else {
            throw new IllegalArgumentException(
                    "Interview score must be less than or equal to " +
                            assessmentWeight.getInterview());
        }
        if (request.getCGPA() <= assessmentWeight.getCGPA()) {
            examResult.setCGPA(request.getCGPA());
        } else {
            throw new IllegalArgumentException(
                    "CGPA score must be less than or equal to " +
                            assessmentWeight.getCGPA());
        }
        if (request.getExperience() <= assessmentWeight.getExperience()) {
            examResult.setExperience(request.getExperience());
        } else {
            throw new IllegalArgumentException(
                    "Experience score must be less than or equal to " +
                            assessmentWeight.getExperience());
        }
        if (request.getPracticalExam() <= assessmentWeight.getPracticalExam()) {
            examResult.setPracticalExam(request.getPracticalExam());
        } else {
            throw new IllegalArgumentException(
                    "Practical exam score must be less than or equal to " +
                            assessmentWeight.getPracticalExam());
        }
        if (request.getOther() <= assessmentWeight.getOther()) {
            examResult.setOther(request.getOther());
        } else {
            throw new IllegalArgumentException(
                    "Other score must be less than or equal to " +
                            assessmentWeight.getOther());
        }

        examResult.setTotal(request.getWrittenExam() + request.getInterview() +
                request.getCGPA() + request.getExperience() + request.getPracticalExam() + request.getOther());

        return examResult;
    }

    public ExamResultResponse mapToDto(ExamResult examResult) {

        ExamResultResponse response = new ExamResultResponse();
        response.setId(examResult.getId());
        response.setWrittenExam(examResult.getWrittenExam());
        response.setInterview(examResult.getInterview());
        response.setCGPA(examResult.getCGPA());
        response.setExperience(examResult.getExperience());
        response.setPracticalExam(examResult.getPracticalExam());
        response.setOther(examResult.getOther());
        response.setTotal(examResult.getTotal());
        response.setApplicantId(examResult.getApplicant().getId());
        response.setRecruitmentId(examResult.getRecruitment().getId());
        response.setAssessmentWeightId(examResult.getAssessmentWeight().getId());
        response.setTenantId(examResult.getTenantId());
        response.setCreatedAt(examResult.getCreatedAt());
        response.setCreatedBy(examResult.getCreatedBy());
        response.setUpdatedAt(examResult.getUpdatedAt());
        response.setUpdatedBy(examResult.getUpdatedBy());

        return response;
    }

    public ExamResult updateExamResult(TenantDto tenant,
                                       Recruitment recruitment,
                                       ExamResult examResult,
                                       ExamResultRequest request) {

        AssessmentWeight assessmentWeight = assessmentWeightRepository
                .findById(request.getAssessmentWeightId())
                .filter(ass -> ass.getTenantId().equals(tenant.getId()))
                .filter(ass -> ass.getRecruitment().equals(recruitment))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Assessment weight not found with id '" + request.getAssessmentWeightId() + "'"));

        if (request.getWrittenExam() != null &&
                request.getWrittenExam() <= assessmentWeight.getWrittenExam()){
            examResult.setWrittenExam(request.getWrittenExam());
        } else {
            throw new IllegalArgumentException(
                    "Written exam score must be less than or equal to " +
                            assessmentWeight.getWrittenExam());
        }
        if (request.getInterview() != null &&
                request.getInterview() <= assessmentWeight.getInterview()){
            examResult.setInterview(request.getInterview());
        } else {
            throw new IllegalArgumentException(
                    "Interview score must be less than or equal to " +
                            assessmentWeight.getInterview());
        }
        if (request.getCGPA() != null &&
                request.getCGPA() <= assessmentWeight.getCGPA()){
            examResult.setCGPA(request.getCGPA());
        } else {
            throw new IllegalArgumentException(
                    "CGPA score must be less than or equal to " +
                            assessmentWeight.getCGPA());
        }
        if (request.getExperience() != null &&
                request.getExperience() <= assessmentWeight.getExperience()){
            examResult.setExperience(request.getExperience());
        } else {
            throw new IllegalArgumentException(
                    "Experience score must be less than or equal to " +
                            assessmentWeight.getExperience());
        }
        if (request.getPracticalExam() != null &&
                request.getPracticalExam() <= assessmentWeight.getPracticalExam()){
            examResult.setPracticalExam(request.getPracticalExam());
        } else {
            throw new IllegalArgumentException(
                    "Practical exam score must be less than or equal to " +
                            assessmentWeight.getPracticalExam());
        }
        if (request.getOther() != null &&
                request.getOther() <= assessmentWeight.getOther()){
            examResult.setOther(request.getOther());
        } else {
            throw new IllegalArgumentException(
                    "Other score must be less than or equal to " +
                            assessmentWeight.getOther());
        }

        examResult.setTotal(request.getWrittenExam() + request.getInterview() +
                request.getCGPA() + request.getExperience() + request.getPracticalExam() + request.getOther());

        return examResult;
    }
}
