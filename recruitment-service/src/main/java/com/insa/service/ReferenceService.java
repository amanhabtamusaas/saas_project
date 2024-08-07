package com.insa.service;

import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.ReferenceRequest;
import com.insa.dto.response.ReferenceResponse;
import com.insa.entity.Applicant;
import com.insa.entity.Reference;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.ReferenceMapper;
import com.insa.repository.ApplicantRepository;
import com.insa.repository.ReferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReferenceService {

    private final ReferenceRepository referenceRepository;
    private final ApplicantRepository applicantRepository;
    private final TenantServiceClient tenantServiceClient;
    private final ReferenceMapper referenceMapper;

    @Transactional
    public ReferenceResponse addReference(Long tenantId,
                                          Long applicantId,
                                          ReferenceRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(emp -> emp.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Reference reference = referenceMapper.mapToEntity(request);
        reference.setTenantId(tenant.getId());
        reference.setApplicant(applicant);
        reference = referenceRepository.save(reference);
        return referenceMapper.mapToDto(reference);
    }

    public List<ReferenceResponse> getAllReferences(Long tenantId,
                                                    Long applicantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(emp -> emp.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        List<Reference> references = referenceRepository.findAll();
        return references.stream()
                .filter(ref -> ref.getTenantId().equals(tenant.getId()))
                .filter(ref -> ref.getApplicant().equals(applicant))
                .map(referenceMapper::mapToDto)
                .toList();
    }

    public ReferenceResponse getReferenceById(Long tenantId,
                                              Long applicantId,
                                              Long referenceId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(emp -> emp.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Reference reference = referenceRepository.findById(referenceId)
                .filter(ref -> ref.getTenantId().equals(tenant.getId()))
                .filter(ref -> ref.getApplicant().equals(applicant))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reference not found with id '" + referenceId + "' in the specified applicant"));
        return referenceMapper.mapToDto(reference);
    }

    @Transactional
    public ReferenceResponse updateReference(Long tenantId,
                                             Long applicantId,
                                             Long referenceId,
                                             ReferenceRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(emp -> emp.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Reference reference = referenceRepository.findById(referenceId)
                .filter(ref -> ref.getTenantId().equals(tenant.getId()))
                .filter(ref -> ref.getApplicant().equals(applicant))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reference not found with id '" + referenceId + "' in the specified applicant"));
        reference = referenceMapper.updateReference(reference, request);
        reference = referenceRepository.save(reference);
        return referenceMapper.mapToDto(reference);
    }

    @Transactional
    public void deleteReference(Long tenantId,
                                Long applicantId,
                                Long referenceId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(emp -> emp.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Reference reference = referenceRepository.findById(referenceId)
                .filter(ref -> ref.getTenantId().equals(tenant.getId()))
                .filter(ref -> ref.getApplicant().equals(applicant))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reference not found with id '" + referenceId + "' in the specified applicant"));
        referenceRepository.delete(reference);
    }
}
