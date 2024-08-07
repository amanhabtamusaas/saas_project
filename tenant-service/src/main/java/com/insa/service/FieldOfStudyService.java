package com.insa.service;

import com.insa.dto.requestDto.FieldOfStudyRequest;
import com.insa.dto.responseDto.FieldOfStudyResponse;
import com.insa.entity.FieldOfStudy;
import com.insa.entity.Tenant;
import com.insa.entity.JobRegistration;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.FieldOfStudyMapper;
import com.insa.repository.FieldOfStudyRepository;
import com.insa.repository.TenantRepository;
import com.insa.repository.JobRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldOfStudyService {

    private final FieldOfStudyRepository fieldOfStudyRepository;
    private final FieldOfStudyMapper fieldOfStudyMapper;
    private final TenantRepository tenantRepository;
    private final JobRegistrationRepository jobRegistrationRepository;

    public FieldOfStudyResponse createFieldOfStudy(Long tenantId, FieldOfStudyRequest fieldOfStudyRequest) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + tenantId + " "));

        if (fieldOfStudyRepository.existsByFieldOfStudyAndTenantId(fieldOfStudyRequest.getFieldOfStudy(),tenant.getId())) {
            throw new ResourceExistsException("Education Level with Name " + fieldOfStudyRequest.getFieldOfStudy() + " already exists");
        }


        FieldOfStudy fieldOfStudy = fieldOfStudyMapper.mapToEntity(fieldOfStudyRequest);
        fieldOfStudy.setTenant(tenant);


        fieldOfStudy = fieldOfStudyRepository.save(fieldOfStudy);

        return fieldOfStudyMapper.mapToDto(fieldOfStudy);
    }

    public List<FieldOfStudyResponse> getAllFieldOfStudies(Long tenantId) {
        Tenant tenant = getTenantById(tenantId);

        List<FieldOfStudy> fieldOfStudies = fieldOfStudyRepository.findAll();
        return fieldOfStudies.stream()
                .filter(fieldOfStudy -> fieldOfStudy.getTenant().getId().equals(tenant.getId()))
                .map(fieldOfStudyMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public FieldOfStudyResponse getFieldOfStudyById(Long id, Long tenantId) {
        Tenant tenant = getTenantById(tenantId);

        FieldOfStudy fieldOfStudy = fieldOfStudyRepository.findById(id)
                .filter(fs -> fs.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "FieldOfStudy not found with id: " + id + " for the specified tenant"));

        return fieldOfStudyMapper.mapToDto(fieldOfStudy);
    }

    public FieldOfStudyResponse updateFieldOfStudy(Long id, Long tenantId, FieldOfStudyRequest fieldOfStudyRequest) {
        Tenant tenant = getTenantById(tenantId);


        FieldOfStudy fieldOfStudy = fieldOfStudyRepository.findById(id)
                .filter(fs -> fs.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "FieldOfStudy not found with id: " + id + " for the specified tenant"));

        fieldOfStudy = fieldOfStudyMapper.updateFieldOfStudy(fieldOfStudy, fieldOfStudyRequest);
      

        fieldOfStudy = fieldOfStudyRepository.save(fieldOfStudy);

        return fieldOfStudyMapper.mapToDto(fieldOfStudy);
    }

    public void deleteFieldOfStudy(Long id, Long tenantId) {
        Tenant tenant = getTenantById(tenantId);

        FieldOfStudy fieldOfStudy = fieldOfStudyRepository.findById(id)
                .filter(fs -> fs.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "FieldOfStudy not found with id: " + id + " for the specified tenant"));

        fieldOfStudyRepository.delete(fieldOfStudy);
    }

    private Tenant getTenantById(Long tenantId) {
        return tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));
    }

    private JobRegistration getJobRegistrationById(Long jobRegistrationId) {
        return jobRegistrationRepository.findById(jobRegistrationId)
                .orElseThrow(() -> new ResourceNotFoundException("JobRegistration not found with id: " + jobRegistrationId));
    }
}

