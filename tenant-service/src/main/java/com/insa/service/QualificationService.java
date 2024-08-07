package com.insa.service;

import com.insa.dto.requestDto.QualificationRequest;
import com.insa.dto.responseDto.QualificationResponse;
import com.insa.entity.Qualification;
import com.insa.entity.Tenant;
import com.insa.entity.JobRegistration;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.QualificationMapper;
import com.insa.repository.QualificationRepository;
import com.insa.repository.TenantRepository;
import com.insa.repository.JobRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QualificationService {

    private final QualificationRepository qualificationRepository;
    private final QualificationMapper qualificationMapper;
    private final TenantRepository tenantRepository;
    private final JobRegistrationRepository jobRegistrationRepository;

    public QualificationResponse createQualification(Long tenantId, QualificationRequest qualificationRequest) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + tenantId + " "));
        if (qualificationRepository.existsByQualificationAndTenantId(qualificationRequest.getQualification(),tenant.getId())) {
            throw new ResourceExistsException("Qualification with Name " + qualificationRequest.getQualification() + " already exists");
        }


        Qualification qualification = qualificationMapper.mapToEntity(qualificationRequest);
        qualification.setTenant(tenant);


        qualification = qualificationRepository.save(qualification);

        return qualificationMapper.mapToDto(qualification);
    }

    public List<QualificationResponse> getAllQualifications(Long tenantId) {
        Tenant tenant = getTenantById(tenantId);

        List<Qualification> qualifications = qualificationRepository.findAll();
        return qualifications.stream()
                .filter(qualification -> qualification.getTenant().getId().equals(tenant.getId()))
                .map(qualificationMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public QualificationResponse getQualificationById(Long id, Long tenantId) {
        Tenant tenant = getTenantById(tenantId);

        Qualification qualification = qualificationRepository.findById(id)
                .filter(q -> q.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Qualification not found with id: " + id + " for the specified tenant"));

        return qualificationMapper.mapToDto(qualification);
    }

    public QualificationResponse updateQualification(Long id, Long tenantId, QualificationRequest qualificationRequest) {
        Tenant tenant = getTenantById(tenantId);


        Qualification qualification = qualificationRepository.findById(id)
                .filter(q -> q.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Qualification not found with id: " + id + " for the specified tenant"));

        qualification = qualificationMapper.updateQualification(qualification, qualificationRequest);


        qualification = qualificationRepository.save(qualification);

        return qualificationMapper.mapToDto(qualification);
    }

    public void deleteQualification(Long id, Long tenantId) {
        Tenant tenant = getTenantById(tenantId);

        Qualification qualification = qualificationRepository.findById(id)
                .filter(q -> q.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Qualification not found with id: " + id + " for the specified tenant"));

        qualificationRepository.delete(qualification);
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
