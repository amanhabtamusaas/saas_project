package com.insa.service;

import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.ExamResultRequest;
import com.insa.dto.response.ExamResultResponse;
import com.insa.entity.Applicant;
import com.insa.entity.ExamResult;
import com.insa.entity.Recruitment;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.ExamResultMapper;
import com.insa.repository.ApplicantRepository;
import com.insa.repository.ExamResultRepository;
import com.insa.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamResultService {

    private final ExamResultRepository examResultRepository;
    private final ApplicantRepository applicantRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final TenantServiceClient tenantServiceClient;
    private final ExamResultMapper examResultMapper;

    @Transactional
    public ExamResultResponse createExamResult(Long tenantId,
                                               Long recruitmentId,
                                               Long applicantId,
                                               ExamResultRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with id '" + recruitmentId + "' in the specified tenant"));
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .filter(rec -> rec.getRecruitment().equals(recruitment))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        boolean examResultExists = examResultRepository.existsByApplicant(applicant);
        if (examResultExists) {
            throw new ResourceExistsException("Exam Result already exists for the specified applicant");
        }
        ExamResult examResult = examResultMapper.mapToEntity(tenant.getId(), recruitment.getId(), request);
        examResult.setTenantId(tenant.getId());
        examResult.setRecruitment(recruitment);
        examResult.setApplicant(applicant);
        examResult = examResultRepository.save(examResult);
        return examResultMapper.mapToDto(examResult);
    }

    public List<ExamResultResponse> getAllExamResult(Long tenantId,
                                                     Long recruitmentId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with id '" + recruitmentId + "' in the specified tenant"));
        List<ExamResult> examResults = examResultRepository.findAll();
        return examResults.stream()
                .filter(exa -> exa.getTenantId ().equals(tenant.getId ()))
                .filter(exa -> exa.getRecruitment().equals(recruitment))
                .map(examResultMapper::mapToDto)
                .toList();
    }

    public ExamResultResponse getExamResultById(Long tenantId,
                                                Long recruitmentId,
                                                Long applicantId,
                                                Long examResultId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with id '" + recruitmentId + "' in the specified tenant"));
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .filter(rec -> rec.getRecruitment().equals(recruitment))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + recruitmentId + "' in the specified tenant"));
        ExamResult examResult = examResultRepository.findById(examResultId)
                .filter(exa -> exa.getTenantId ().equals(tenantId))
                .filter(exa -> exa.getRecruitment().getId().equals(recruitmentId))
                .filter(exa -> exa.getApplicant().equals(applicant))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Exam result not found with id '" + examResultId + " in the specified applicant"));
        return examResultMapper.mapToDto(examResult);
    }

    @Transactional
    public ExamResultResponse updateExamResult(Long tenantId,
                                               Long recruitmentId,
                                               Long applicantId,
                                               Long examResultId,
                                               ExamResultRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with id '" + recruitmentId + "' in the specified tenant"));
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .filter(rec -> rec.getRecruitment().equals(recruitment))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + recruitmentId + "' in the specified tenant"));
        ExamResult examResult = examResultRepository.findById(examResultId)
                .filter(exa -> exa.getTenantId ().equals(tenantId))
                .filter(exa -> exa.getRecruitment().getId().equals(recruitmentId))
                .filter(exa -> exa.getApplicant().equals(applicant))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Exam result not found with id '" + examResultId + " in the specified applicant"));
        examResult = examResultMapper
                .updateExamResult(tenant.getId(), recruitment.getId(), examResult, request);
        examResult = examResultRepository.save(examResult);
        return examResultMapper.mapToDto(examResult);
    }
}
