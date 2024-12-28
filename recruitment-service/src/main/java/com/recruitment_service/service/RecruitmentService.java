package com.recruitment_service.service;

import com.recruitment_service.dto.clientDto.TenantDto;
import com.recruitment_service.dto.request.RecruitmentApproveRequest;
import com.recruitment_service.dto.request.RecruitmentRequest;
import com.recruitment_service.dto.response.RecruitmentResponse;
import com.recruitment_service.model.Recruitment;
import com.recruitment_service.enums.RecruitmentStatus;
import com.recruitment_service.mapper.RecruitmentMapper;
import com.recruitment_service.repository.RecruitmentRepository;
import com.recruitment_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final RecruitmentMapper recruitmentMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public RecruitmentResponse createRecruitment(UUID tenantId,
                                                 RecruitmentRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = recruitmentMapper.mapToEntity(tenant, request);
        recruitment = recruitmentRepository.save(recruitment);
        return recruitmentMapper.mapToDto(recruitment);
    }

    public List<RecruitmentResponse> getAllRecruitments(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Recruitment> recruitments = recruitmentRepository.findAll();
        return recruitments.stream()
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .map(recruitmentMapper::mapToDto)
                .toList();
    }

    public RecruitmentResponse getRecruitmentById(UUID tenantId,
                                                  UUID recruitmentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil.getRecruitmentById(tenant.getId(), recruitmentId);
        return recruitmentMapper.mapToDto(recruitment);
    }

    public RecruitmentResponse getRecruitmentByVacancyNumber(UUID tenantId,
                                                             String vacancyNumber) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil
                .getRecruitmentByVacancyNumber(tenant.getId(), vacancyNumber);
        return recruitmentMapper.mapToDto(recruitment);
    }

    public List<RecruitmentResponse> getRecruitmentsByStatus(UUID tenantId,
                                                            RecruitmentStatus recruitmentStatus) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Recruitment> recruitments = recruitmentRepository
                .findByRecruitmentStatus(recruitmentStatus);
        return recruitments.stream()
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .map(recruitmentMapper::mapToDto)
                .toList();
    }

    @Transactional
    public RecruitmentResponse updateRecruitment(UUID tenantId,
                                                 UUID recruitmentId,
                                                 RecruitmentRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil.getRecruitmentById(tenant.getId(), recruitmentId);
        if (recruitment.getRecruitmentStatus().equals(RecruitmentStatus.APPROVED)) {
            throw new IllegalStateException(
                    "Recruitment has already been approved. Updating an approved recruitment is not possible.");
        }
        if (recruitment.getRecruitmentStatus().equals(RecruitmentStatus.REJECTED)) {
            throw new IllegalStateException(
                    "Recruitment has already been rejected. Updating a rejected recruitment is not possible.");
        }
        recruitment = recruitmentMapper.updateRecruitment(tenant, recruitment, request);
        recruitment = recruitmentRepository.save(recruitment);
        return recruitmentMapper.mapToDto(recruitment);
    }

    @Transactional
    public RecruitmentResponse approveRecruitment(UUID tenantId,
                                                  UUID recruitmentId,
                                                  RecruitmentApproveRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil.getRecruitmentById(tenant.getId(), recruitmentId);
        recruitment = recruitmentMapper.approveRecruitment(recruitment, request);
        recruitment = recruitmentRepository.save(recruitment);
        return recruitmentMapper.mapToDto(recruitment);
    }

    @Transactional
    public void deleteRecruitment(UUID tenantId,
                                  UUID recruitmentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil.getRecruitmentById(tenant.getId(), recruitmentId);
        recruitmentRepository.delete(recruitment);
    }
}