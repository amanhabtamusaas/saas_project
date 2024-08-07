package com.insa.service;

import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.ApplicantRequest;
import com.insa.dto.response.ApplicantResponse;
import com.insa.entity.Recruitment;
import com.insa.entity.Applicant;
import com.insa.enums.RecruitmentStatus;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.ApplicantMapper;
import com.insa.repository.AdvertisementRepository;
import com.insa.repository.RecruitmentRepository;
import com.insa.repository.ApplicantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final TenantServiceClient tenantServiceClient;
    private final ApplicantMapper applicantMapper;

    @Transactional
    public ApplicantResponse createApplicant(Long tenantId,
                                             ApplicantRequest request)  {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Recruitment recruitment = recruitmentRepository.findById(request.getRecruitmentId())
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with id '" + request.getRecruitmentId() + "' in the specified tenant"));
        if (!recruitment.getRecruitmentStatus().equals(RecruitmentStatus.APPROVED)) {
            throw new IllegalStateException("Cannot create applicant for non-approved recruitment.");
        }
        Applicant applicant = applicantMapper.mapToEntity(request);
        applicant.setTenantId(tenant.getId());
        applicant.setRecruitment(recruitment);
        applicant = applicantRepository.save(applicant);
        return applicantMapper.mapToDto(applicant);
    }

    public List<ApplicantResponse> getAllApplicants(Long tenantId,
                                                    Long recruitmentId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with id '" + recruitmentId + "' in the specified tenant"));
        List<Applicant> applicants = applicantRepository.findAll();
        return applicants.stream()
                .filter(app -> app.getTenantId().equals(tenant.getId()))
                .filter(app -> app.getRecruitment().equals(recruitment))
                .map(applicantMapper::mapToDto)
                .toList();
    }

    public ApplicantResponse getApplicantById(Long tenantId,
                                              Long applicantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(app -> app.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        return applicantMapper.mapToDto(applicant);
    }

    @Transactional
    public ApplicantResponse updateApplicant(Long tenantId,
                                             Long applicantId,
                                             ApplicantRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Recruitment recruitment = recruitmentRepository.findById(request.getRecruitmentId())
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with id '" + request.getRecruitmentId() + "' in the specified tenant"));
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(app -> app.getTenantId().equals(tenant.getId()))
                .filter(app -> app.getRecruitment().equals(recruitment))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        applicant = applicantMapper.updateApplicant(applicant, request);
        if (request.getRecruitmentId() != null) {
            applicant.setRecruitment(recruitment);
        }
        applicant = applicantRepository.save(applicant);
        return applicantMapper.mapToDto(applicant);
    }

    @Transactional
    public void deleteApplicant(Long tenantId,
                                Long applicantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(app -> app.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        applicantRepository.delete(applicant);
    }
}
