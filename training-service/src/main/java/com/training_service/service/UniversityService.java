package com.training_service.service;

import com.training_service.dto.clientDto.TenantDto;
import com.training_service.dto.request.UniversityRequest;
import com.training_service.dto.response.UniversityResponse;
import com.training_service.exception.ResourceExistsException;
import com.training_service.model.University;
import com.training_service.mapper.UniversityMapper;
import com.training_service.repository.UniversityRepository;
import com.training_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;
    private final UniversityMapper universityMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public UniversityResponse addUniversity(UUID tenantId,
                                            UniversityRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        University university = universityMapper.mapToEntity(tenant, request);
        if (universityRepository.existsByTenantIdAndUniversityName(
                tenant.getId(), request.getUniversityName())) {
            throw new ResourceExistsException(
                    "University with name '" + request.getUniversityName() + "' already exists");
        }
        university = universityRepository.save(university);
        return universityMapper.mapToDto(university);
    }

    public List<UniversityResponse> getAllUniversities(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<University> universities = universityRepository.findAll();
        return universities.stream()
                .filter(university -> university.getTenantId().equals(tenant.getId()))
                .map(universityMapper::mapToDto)
                .toList();
    }

    public UniversityResponse getUniversityById(UUID tenantId,
                                                UUID universityId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        University university = validationUtil.getUniversityById(tenant.getId(), universityId);
        return universityMapper.mapToDto(university);
    }

    @Transactional
    public UniversityResponse updateUniversity(UUID tenantId,
                                               UUID universityId,
                                               UniversityRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        University university = validationUtil.getUniversityById(tenant.getId(), universityId);
        university = universityMapper.updateEntity(tenant, university, request);
        university = universityRepository.save(university);
        return universityMapper.mapToDto(university);
    }

    @Transactional
    public void deleteUniversity(UUID tenantId,
                                 UUID universityId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        University university = validationUtil.getUniversityById(tenant.getId(), universityId);
        universityRepository.delete(university);
    }
}