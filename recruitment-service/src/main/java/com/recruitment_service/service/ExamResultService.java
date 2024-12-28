package com.recruitment_service.service;

import com.recruitment_service.dto.clientDto.TenantDto;
import com.recruitment_service.dto.request.ExamResultRequest;
import com.recruitment_service.dto.response.ExamResultResponse;
import com.recruitment_service.model.Applicant;
import com.recruitment_service.model.ExamResult;
import com.recruitment_service.model.Recruitment;
import com.recruitment_service.exception.ResourceExistsException;
import com.recruitment_service.exception.ResourceNotFoundException;
import com.recruitment_service.mapper.ExamResultMapper;
import com.recruitment_service.repository.ExamResultRepository;
import com.recruitment_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamResultService {

    private final ExamResultRepository examResultRepository;
    private final ExamResultMapper examResultMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public ExamResultResponse createExamResult(UUID tenantId,
                                               UUID recruitmentId,
                                               UUID applicantId,
                                               ExamResultRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil.getRecruitmentById(tenant.getId(), recruitmentId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        boolean examResultExists = examResultRepository.existsByApplicant(applicant);
        if (examResultExists) {
            throw new ResourceExistsException("Exam Result already exists for the specified applicant");
        }
        ExamResult examResult = examResultMapper.mapToEntity(tenant, recruitment, applicant, request);
        examResult = examResultRepository.save(examResult);
        return examResultMapper.mapToDto(examResult);
    }

    public List<ExamResultResponse> getAllExamResult(UUID tenantId,
                                                     UUID recruitmentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil.getRecruitmentById(tenant.getId(), recruitmentId);
        List<ExamResult> examResults = examResultRepository
                .findByTenantIdAndRecruitment(tenant.getId(), recruitment);
        return examResults.stream()
                .map(examResultMapper::mapToDto)
                .toList();
    }

    public ExamResultResponse getExamResultById(UUID tenantId,
                                                UUID recruitmentId,
                                                UUID applicantId,
                                                UUID examResultId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil.getRecruitmentById(tenant.getId(), recruitmentId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        ExamResult examResult = getExamResultById(tenant.getId(), recruitment, applicant, examResultId);
        return examResultMapper.mapToDto(examResult);
    }

    @Transactional
    public ExamResultResponse updateExamResult(UUID tenantId,
                                               UUID recruitmentId,
                                               UUID applicantId,
                                               UUID examResultId,
                                               ExamResultRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil.getRecruitmentById(tenant.getId(), recruitmentId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        ExamResult examResult = getExamResultById(tenant.getId(), recruitment, applicant, examResultId);
        examResult = examResultMapper.updateExamResult(tenant, recruitment, examResult, request);
        examResult = examResultRepository.save(examResult);
        return examResultMapper.mapToDto(examResult);
    }

    private ExamResult getExamResultById(UUID tenantId, Recruitment recruitment,
                                         Applicant applicant, UUID examResultId) {

        return examResultRepository.findById(examResultId)
                .filter(exa -> exa.getTenantId().equals(tenantId))
                .filter(exa -> exa.getRecruitment().equals(recruitment))
                .filter(exa -> exa.getApplicant().equals(applicant))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Exam result not found with id '" + examResultId + "'"));
    }
}