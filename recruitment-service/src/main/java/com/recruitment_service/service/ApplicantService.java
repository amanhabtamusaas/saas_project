package com.recruitment_service.service;

import com.recruitment_service.dto.clientDto.TenantDto;
import com.recruitment_service.dto.request.ApplicantRequest;
import com.recruitment_service.dto.response.ApplicantResponse;
import com.recruitment_service.model.Recruitment;
import com.recruitment_service.model.Applicant;
import com.recruitment_service.enums.RecruitmentStatus;
import com.recruitment_service.mapper.ApplicantMapper;
import com.recruitment_service.repository.ApplicantRepository;
import com.recruitment_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final ApplicantMapper applicantMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public ApplicantResponse createApplicant(UUID tenantId,
                                             ApplicantRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil
                .getRecruitmentById(tenant.getId(), request.getRecruitmentId());
        if (!recruitment.getRecruitmentStatus().equals(RecruitmentStatus.APPROVED)) {
            throw new IllegalStateException("Cannot create applicant for non-approved recruitment.");
        }
        Applicant applicant = applicantMapper.mapToEntity(tenant, recruitment, request);
        applicant = applicantRepository.save(applicant);
        return applicantMapper.mapToDto(applicant);
    }

    public List<ApplicantResponse> getAllApplicants(UUID tenantId,
                                                    UUID recruitmentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil.getRecruitmentById(tenant.getId(), recruitmentId);
        List<Applicant> applicants = applicantRepository
                .findByTenantIdAndRecruitment(tenant.getId(), recruitment);
        return applicants.stream()
                .map(applicantMapper::mapToDto)
                .toList();
    }

    public ApplicantResponse getApplicantById(UUID tenantId,
                                              UUID applicantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        return applicantMapper.mapToDto(applicant);
    }

    @Transactional
    public ApplicantResponse updateApplicant(UUID tenantId,
                                             UUID applicantId,
                                             ApplicantRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        applicant = applicantMapper.updateApplicant(tenant, applicant, request);
        applicant = applicantRepository.save(applicant);
        return applicantMapper.mapToDto(applicant);
    }

    @Transactional
    public void deleteApplicant(UUID tenantId,
                                UUID applicantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        applicantRepository.delete(applicant);
    }
}