package com.insa.service;

import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.AssessmentWeightRequest;
import com.insa.dto.response.AssessmentWeightResponse;
import com.insa.entity.Advertisement;
import com.insa.entity.AssessmentWeight;
import com.insa.entity.Recruitment;
import com.insa.enums.RecruitmentStatus;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.AssessmentWeightMapper;
import com.insa.repository.AssessmentWeightRepository;
import com.insa.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssessmentWeightService {

    private final AssessmentWeightRepository assessmentWeightRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final TenantServiceClient tenantServiceClient;
    private final AssessmentWeightMapper assessmentWeightMapper;

    @Transactional
    public AssessmentWeightResponse createAssessmentWeight(Long tenantId,
                                                           AssessmentWeightRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Recruitment recruitment = recruitmentRepository.findById(request.getRecruitmentId())
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with id '" + request.getRecruitmentId() + "' in the specified tenant"));
        if (!recruitment.getRecruitmentStatus().equals(RecruitmentStatus.APPROVED)) {
            throw new IllegalStateException("Cannot create assessment weight for non-approved recruitment.");
        }
        boolean assessmentWeightExists = assessmentWeightRepository.existsByRecruitment(recruitment);
        if (assessmentWeightExists) {
            throw new ResourceExistsException("Assessment weight already exists for the given recruitment.");
        }
        AssessmentWeight assessmentWeight = assessmentWeightMapper.mapToEntity(request);
        assessmentWeight.setTenantId(tenant.getId());
        assessmentWeight.setRecruitment(recruitment);
        assessmentWeight = assessmentWeightRepository.save(assessmentWeight);
        return assessmentWeightMapper.mapToDto(assessmentWeight);
    }

    public List<AssessmentWeightResponse> getAllAssessmentWeights(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<AssessmentWeight> assessmentWeights = assessmentWeightRepository.findAll();
        return assessmentWeights.stream()
                .filter(ass -> ass.getTenantId().equals(tenant.getId()))
                .map(assessmentWeightMapper::mapToDto)
                .toList();
    }

    public AssessmentWeightResponse getAssessmentWeightById(Long tenantId,
                                                            Long assessmentWeightId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        AssessmentWeight assessmentWeight = assessmentWeightRepository
                .findById(assessmentWeightId)
                .filter(ass -> ass.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Assessment weight not found with id '" + assessmentWeightId + "' with the specified tenant"));
        return assessmentWeightMapper.mapToDto(assessmentWeight);
    }

    public AssessmentWeightResponse getAssessmentWeightByRecruitment(Long tenantId,
                                                                     Long recruitmentId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        AssessmentWeight assessmentWeight = assessmentWeightRepository
                .findByRecruitmentId(recruitmentId)
                .filter(ass -> ass.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Assessment weight not found in the specified recruitment '" + recruitmentId + "'"));
        return assessmentWeightMapper.mapToDto(assessmentWeight);
    }

    @Transactional
    public AssessmentWeightResponse updateAssessmentWeight(Long tenantId,
                                                           Long assessmentWeightId,
                                                           AssessmentWeightRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Recruitment recruitment = recruitmentRepository.findById(request.getRecruitmentId())
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with id '" + request.getRecruitmentId() + "' in the specified tenant"));
        AssessmentWeight assessmentWeight = assessmentWeightRepository
                .findById(assessmentWeightId)
                .filter(ass -> ass.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Assessment weight not found with id '" + assessmentWeightId + "' with the specified tenant"));
        assessmentWeight = assessmentWeightMapper
                .updateAssessmentWeight(assessmentWeight, request);
        if (request.getRecruitmentId() != null) {
            assessmentWeight.setRecruitment(recruitment);
        }
        assessmentWeight = assessmentWeightRepository.save(assessmentWeight);
        return assessmentWeightMapper.mapToDto(assessmentWeight);
    }
}
