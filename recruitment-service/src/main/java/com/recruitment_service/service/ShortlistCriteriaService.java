package com.recruitment_service.service;

import com.recruitment_service.dto.clientDto.TenantDto;
import com.recruitment_service.dto.request.ShortlistCriteriaRequest;
import com.recruitment_service.dto.response.ShortlistCriteriaResponse;
import com.recruitment_service.model.Recruitment;
import com.recruitment_service.model.ShortlistCriteria;
import com.recruitment_service.enums.RecruitmentStatus;
import com.recruitment_service.exception.ResourceNotFoundException;
import com.recruitment_service.mapper.ShortlistCriteriaMapper;
import com.recruitment_service.repository.ShortlistCriteriaRepository;
import com.recruitment_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShortlistCriteriaService {

    private final ShortlistCriteriaRepository shortlistCriteriaRepository;
    private final ShortlistCriteriaMapper shortlistCriteriaMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public ShortlistCriteriaResponse createShortlistCriteria(UUID tenantId,
                                                             ShortlistCriteriaRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil
                .getRecruitmentById(tenant.getId(), request.getRecruitmentId());
        if (!recruitment.getRecruitmentStatus().equals(RecruitmentStatus.APPROVED)) {
            throw new IllegalStateException(
                    "Cannot create shortlist criteria for non-approved recruitment.");
        }
        ShortlistCriteria shortlistCriteria = shortlistCriteriaMapper
                .mapToEntity(tenant, recruitment, request);
        shortlistCriteria = shortlistCriteriaRepository.save(shortlistCriteria);
        return shortlistCriteriaMapper.mapToDto(shortlistCriteria);
    }

    public List<ShortlistCriteriaResponse> getAllShortlistCriteria(UUID tenantId,
                                                                   UUID recruitmentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil.getRecruitmentById(tenant.getId(), recruitmentId);
        List<ShortlistCriteria> shortlistCriteria = shortlistCriteriaRepository
                .findByTenantIdAndRecruitment(tenant.getId(), recruitment);
        return shortlistCriteria.stream()
                .map(shortlistCriteriaMapper::mapToDto)
                .toList();
    }

    public ShortlistCriteriaResponse getShortlistCriteriaById(UUID tenantId,
                                                              UUID shortlistCriteriaId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        ShortlistCriteria shortlistCriteria = getShortlistById(tenant.getId(), shortlistCriteriaId);
        return shortlistCriteriaMapper.mapToDto(shortlistCriteria);
    }

    @Transactional
    public ShortlistCriteriaResponse updateShortlistCriteria(UUID tenantId,
                                                             UUID shortlistCriteriaId,
                                                             ShortlistCriteriaRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        ShortlistCriteria shortlistCriteria = getShortlistById(tenant.getId(), shortlistCriteriaId);
        shortlistCriteria = shortlistCriteriaMapper
                .updateShortlistCriteria(tenant, shortlistCriteria, request);
        shortlistCriteria = shortlistCriteriaRepository.save(shortlistCriteria);
        return shortlistCriteriaMapper.mapToDto(shortlistCriteria);
    }

    @Transactional
    public void deleteShortlistCriteria(UUID tenantId,
                                        UUID shortlistCriteriaId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        ShortlistCriteria shortlistCriteria = getShortlistById(tenant.getId(), shortlistCriteriaId);
        shortlistCriteriaRepository.delete(shortlistCriteria);
    }

    private ShortlistCriteria getShortlistById(UUID tenantId, UUID shortlistCriteriaId) {

        return shortlistCriteriaRepository
                .findById(shortlistCriteriaId)
                .filter(sl -> sl.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Shortlist criteria not found with id '" + shortlistCriteriaId + "'"));
    }
}