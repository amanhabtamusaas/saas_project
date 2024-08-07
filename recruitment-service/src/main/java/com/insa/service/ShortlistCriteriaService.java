package com.insa.service;

import com.insa.dto.apiDto.EducationLevelDto;
import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.ShortlistCriteriaRequest;
import com.insa.dto.response.ShortlistCriteriaResponse;
import com.insa.entity.ExamResult;
import com.insa.entity.Recruitment;
import com.insa.entity.ShortlistCriteria;
import com.insa.enums.RecruitmentStatus;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.ShortlistCriteriaMapper;
import com.insa.repository.RecruitmentRepository;
import com.insa.repository.ShortlistCriteriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShortlistCriteriaService {

    private final ShortlistCriteriaRepository shortlistCriteriaRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final TenantServiceClient tenantServiceClient;
    private final ShortlistCriteriaMapper shortlistCriteriaMapper;

    @Transactional
    public ShortlistCriteriaResponse createShortlistCriteria(Long tenantId,
                                                             ShortlistCriteriaRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        EducationLevelDto educationLevel = tenantServiceClient
                .getEducationLevelById(request.getEducationLevelId(), tenant.getId());
        Recruitment recruitment = recruitmentRepository.findById(request.getRecruitmentId())
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with id '" + request.getRecruitmentId() + "' in the specified tenant"));
        if (!recruitment.getRecruitmentStatus().equals(RecruitmentStatus.APPROVED)) {
            throw new IllegalStateException("Cannot create shortlist criteria for non-approved recruitment.");
        }
        ShortlistCriteria shortlistCriteria = shortlistCriteriaMapper.mapToEntity(request);
        shortlistCriteria.setTenantId(tenant.getId());
        shortlistCriteria.setEducationLevelId(educationLevel.getId());
        shortlistCriteria.setRecruitment(recruitment);
        shortlistCriteria = shortlistCriteriaRepository.save(shortlistCriteria);
        return shortlistCriteriaMapper.mapToDto(shortlistCriteria);
    }

    public List<ShortlistCriteriaResponse> getAllShortlistCriteria(Long tenantId,
                                                                   Long recruitmentId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with id '" + recruitmentId + "' in the specified tenant"));
        List<ShortlistCriteria> shortlistCriteria = shortlistCriteriaRepository.findAll();
        return shortlistCriteria.stream()
                .filter(sl -> sl.getTenantId().equals(tenant.getId()))
                .filter(sl -> sl.getRecruitment().equals(recruitment))
                .map(shortlistCriteriaMapper::mapToDto)
                .toList();
    }

    public ShortlistCriteriaResponse getShortlistCriteriaById(Long tenantId,
                                                              Long shortlistCriteriaId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        ShortlistCriteria shortlistCriteria = shortlistCriteriaRepository
                .findById(shortlistCriteriaId)
                .filter(sl -> sl.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Shortlist criteria not found with id '" + shortlistCriteriaId + "' in the specified tenant"));
        return shortlistCriteriaMapper.mapToDto(shortlistCriteria);
    }

    @Transactional
    public ShortlistCriteriaResponse updateShortlistCriteria(Long tenantId,
                                                             Long shortlistCriteriaId,
                                                             ShortlistCriteriaRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        EducationLevelDto educationLevel = tenantServiceClient
                .getEducationLevelById(request.getEducationLevelId(), tenant.getId());
        Recruitment recruitment = recruitmentRepository.findById(request.getRecruitmentId())
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with id '" + request.getRecruitmentId() + "' in the specified tenant"));
        ShortlistCriteria shortlistCriteria = shortlistCriteriaRepository
                .findById(shortlistCriteriaId)
                .filter(sl -> sl.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Shortlist criteria not found with id '" + shortlistCriteriaId + "' in the specified tenant"));
        shortlistCriteria = shortlistCriteriaMapper
                .updateShortlistCriteria(shortlistCriteria, request);
        if (request.getEducationLevelId() != null) {
            shortlistCriteria.setEducationLevelId(educationLevel.getId());
        }
        if (request.getRecruitmentId() != null) {
            shortlistCriteria.setRecruitment(recruitment);
        }
        return shortlistCriteriaMapper.mapToDto(shortlistCriteria);
    }

    @Transactional
    public void deleteShortlistCriteria(Long tenantId,
                                        Long shortlistCriteriaId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        ShortlistCriteria shortlistCriteria = shortlistCriteriaRepository
                .findById(shortlistCriteriaId)
                .filter(sl -> sl.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Shortlist criteria not found with id '" + shortlistCriteriaId + "' in the specified tenant"));
        shortlistCriteriaRepository.delete(shortlistCriteria);
    }
}
