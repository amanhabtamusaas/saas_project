package com.recruitment_service.service;

import com.recruitment_service.dto.clientDto.TenantDto;
import com.recruitment_service.dto.request.ReferenceRequest;
import com.recruitment_service.dto.response.ReferenceResponse;
import com.recruitment_service.model.Applicant;
import com.recruitment_service.model.Reference;
import com.recruitment_service.exception.ResourceNotFoundException;
import com.recruitment_service.mapper.ReferenceMapper;
import com.recruitment_service.repository.ReferenceRepository;
import com.recruitment_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReferenceService {

    private final ReferenceRepository referenceRepository;
    private final ReferenceMapper referenceMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public ReferenceResponse addReference(UUID tenantId,
                                          UUID applicantId,
                                          ReferenceRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Reference reference = referenceMapper.mapToEntity(tenant, applicant, request);
        reference = referenceRepository.save(reference);
        return referenceMapper.mapToDto(reference);
    }

    public List<ReferenceResponse> getAllReferences(UUID tenantId,
                                                    UUID applicantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        List<Reference> references = referenceRepository
                .findByTenantIdAndApplicant(tenant.getId(), applicant);
        return references.stream()
                .map(referenceMapper::mapToDto)
                .toList();
    }

    public ReferenceResponse getReferenceById(UUID tenantId,
                                              UUID applicantId,
                                              UUID referenceId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Reference reference = getReferenceById(tenant.getId(), applicant, referenceId);
        return referenceMapper.mapToDto(reference);
    }

    @Transactional
    public ReferenceResponse updateReference(UUID tenantId,
                                             UUID applicantId,
                                             UUID referenceId,
                                             ReferenceRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Reference reference = getReferenceById(tenant.getId(), applicant, referenceId);
        reference = referenceMapper.updateReference(reference, request);
        reference = referenceRepository.save(reference);
        return referenceMapper.mapToDto(reference);
    }

    @Transactional
    public void deleteReference(UUID tenantId,
                                UUID applicantId,
                                UUID referenceId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Reference reference = getReferenceById(tenant.getId(), applicant, referenceId);
        referenceRepository.delete(reference);
    }

    private Reference getReferenceById(UUID tenantId, Applicant applicant, UUID referenceId) {

        return referenceRepository.findById(referenceId)
                .filter(ref -> ref.getTenantId().equals(tenantId))
                .filter(ref -> ref.getApplicant().equals(applicant))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reference not found with id '" + referenceId + "'"));
    }
}