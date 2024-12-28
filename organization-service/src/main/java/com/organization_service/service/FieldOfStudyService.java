package com.organization_service.service;

import com.organization_service.dto.requestDto.FieldOfStudyRequest;
import com.organization_service.dto.responseDto.FieldOfStudyResponse;
import com.organization_service.model.FieldOfStudy;
import com.organization_service.model.Tenant;
import com.organization_service.exception.ResourceExistsException;
import com.organization_service.exception.ResourceNotFoundException;
import com.organization_service.mapper.FieldOfStudyMapper;
import com.organization_service.repository.FieldOfStudyRepository;
import com.organization_service.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldOfStudyService {

    private final FieldOfStudyRepository fieldOfStudyRepository;
    private final FieldOfStudyMapper fieldOfStudyMapper;
    private final TenantRepository tenantRepository;

    public FieldOfStudyResponse createFieldOfStudy(UUID tenantId,
                                                   FieldOfStudyRequest fieldOfStudyRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));

        if (fieldOfStudyRepository.existsByFieldOfStudyAndTenantId(
                fieldOfStudyRequest.getFieldOfStudy(), tenant.getId())) {
            throw new ResourceExistsException("Field Of Study with Name " +
                    fieldOfStudyRequest.getFieldOfStudy() + " already exists");
        }

        FieldOfStudy fieldOfStudy = fieldOfStudyMapper.mapToEntity(fieldOfStudyRequest);
        fieldOfStudy.setTenant(tenant);

        fieldOfStudy = fieldOfStudyRepository.save(fieldOfStudy);

        return fieldOfStudyMapper.mapToDto(fieldOfStudy);
    }

    public List<FieldOfStudyResponse> getAllFieldOfStudies(UUID tenantId) {
        Tenant tenant = getTenantById(tenantId);

        List<FieldOfStudy> fieldOfStudies = fieldOfStudyRepository.findAll();
        return fieldOfStudies.stream()
                .filter(fieldOfStudy -> fieldOfStudy.getTenant().getId().equals(tenant.getId()))
                .map(fieldOfStudyMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public FieldOfStudyResponse getFieldOfStudyById(UUID id, UUID tenantId) {
        Tenant tenant = getTenantById(tenantId);

        FieldOfStudy fieldOfStudy = fieldOfStudyRepository.findById(id)
                .filter(fs -> fs.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "FieldOfStudy not found with id: " + id + " for the specified tenant"));

        return fieldOfStudyMapper.mapToDto(fieldOfStudy);
    }

    public FieldOfStudyResponse updateFieldOfStudy(UUID id, UUID tenantId,
                                                   FieldOfStudyRequest fieldOfStudyRequest) {
        Tenant tenant = getTenantById(tenantId);

        FieldOfStudy fieldOfStudy = fieldOfStudyRepository.findById(id)
                .filter(fs -> fs.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "FieldOfStudy not found with id: " + id + " for the specified tenant"));

        fieldOfStudy = fieldOfStudyMapper.updateFieldOfStudy(fieldOfStudy, fieldOfStudyRequest);
        fieldOfStudy = fieldOfStudyRepository.save(fieldOfStudy);

        return fieldOfStudyMapper.mapToDto(fieldOfStudy);
    }

    public void deleteFieldOfStudy(UUID id, UUID tenantId) {
        Tenant tenant = getTenantById(tenantId);

        FieldOfStudy fieldOfStudy = fieldOfStudyRepository.findById(id)
                .filter(fs -> fs.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "FieldOfStudy not found with id: " + id + " for the specified tenant"));

        fieldOfStudyRepository.delete(fieldOfStudy);
    }

    private Tenant getTenantById(UUID tenantId) {
        return tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));
    }
}