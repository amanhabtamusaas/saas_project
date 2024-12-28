package com.recruitment_service.service;

import com.recruitment_service.dto.clientDto.TenantDto;
import com.recruitment_service.dto.request.AssessmentWeightRequest;
import com.recruitment_service.dto.response.AssessmentWeightResponse;
import com.recruitment_service.model.AssessmentWeight;
import com.recruitment_service.model.Recruitment;
import com.recruitment_service.enums.RecruitmentStatus;
import com.recruitment_service.exception.ResourceExistsException;
import com.recruitment_service.exception.ResourceNotFoundException;
import com.recruitment_service.mapper.AssessmentWeightMapper;
import com.recruitment_service.repository.AssessmentWeightRepository;
import com.recruitment_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssessmentWeightService {

    private final AssessmentWeightRepository assessmentWeightRepository;
    private final AssessmentWeightMapper assessmentWeightMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public AssessmentWeightResponse createAssessmentWeight(UUID tenantId,
                                                           AssessmentWeightRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil
                .getRecruitmentById(tenant.getId(), request.getRecruitmentId());
        if (!recruitment.getRecruitmentStatus().equals(RecruitmentStatus.APPROVED)) {
            throw new IllegalStateException("Cannot create assessment weight for non-approved recruitment.");
        }
        boolean assessmentWeightExists = assessmentWeightRepository.existsByRecruitment(recruitment);
        if (assessmentWeightExists) {
            throw new ResourceExistsException("Assessment weight already exists for the given recruitment.");
        }
        AssessmentWeight assessmentWeight = assessmentWeightMapper.mapToEntity(tenant, recruitment, request);
        assessmentWeight = assessmentWeightRepository.save(assessmentWeight);
        return assessmentWeightMapper.mapToDto(assessmentWeight);
    }

    public List<AssessmentWeightResponse> getAllAssessmentWeights(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<AssessmentWeight> assessmentWeights = assessmentWeightRepository.findAll();
        return assessmentWeights.stream()
                .filter(ass -> ass.getTenantId().equals(tenant.getId()))
                .map(assessmentWeightMapper::mapToDto)
                .toList();
    }

    public AssessmentWeightResponse getAssessmentWeightById(UUID tenantId,
                                                            UUID assessmentWeightId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        AssessmentWeight assessmentWeight = getAssessmentById(tenant.getId(), assessmentWeightId);
        return assessmentWeightMapper.mapToDto(assessmentWeight);
    }

    public AssessmentWeightResponse getAssessmentWeightByRecruitmentId(UUID tenantId,
                                                                     UUID recruitmentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        AssessmentWeight assessmentWeight = assessmentWeightRepository.findByRecruitmentId(recruitmentId)
                .filter(ass -> ass.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Assessment weight not found in the specified recruitment '" + recruitmentId + "'"));
        return assessmentWeightMapper.mapToDto(assessmentWeight);
    }

    @Transactional
    public AssessmentWeightResponse updateAssessmentWeight(UUID tenantId,
                                                           UUID assessmentWeightId,
                                                           AssessmentWeightRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        AssessmentWeight assessmentWeight = getAssessmentById(tenant.getId(), assessmentWeightId);
        assessmentWeight = assessmentWeightMapper.updateAssessmentWeight(tenant, assessmentWeight, request);
        assessmentWeight = assessmentWeightRepository.save(assessmentWeight);
        return assessmentWeightMapper.mapToDto(assessmentWeight);
    }

    private AssessmentWeight getAssessmentById(UUID tenantId, UUID assessmentWeightId) {

        return assessmentWeightRepository.findById(assessmentWeightId)
                .filter(ass -> ass.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Assessment weight not found with id '" + assessmentWeightId + "'"));
    }
}