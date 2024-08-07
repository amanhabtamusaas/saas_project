package com.insa.service;


import com.insa.dto.apiDto.LocationDto;
import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.UniversityRequest;
import com.insa.dto.response.UniversityResponse;
import com.insa.entity.University;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.UniversityMapper;
import com.insa.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;
    private final TenantServiceClient tenantServiceClient;
    private final UniversityMapper universityMapper;

    @Transactional
    public UniversityResponse addUniversity(Long tenantId,
                                            UniversityRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        LocationDto location  = tenantServiceClient
                .getLocationById(request.getLocationId(), tenant.getId());
        University university = universityMapper.mapToEntity(request);
        if (universityRepository.existsByTenantIdAndUniversityName(
                tenant.getId(), request.getUniversityName())) {
            throw new ResourceExistsException(
                    "University with name '" + request.getUniversityName() + "' already exists");
        }
        university.setTenantId(tenant.getId());
        university.setLocationId(location.getId());
        university = universityRepository.save(university);
        return universityMapper.mapToDto(university);
    }

    public List<UniversityResponse> getAllUniversities(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<University> universities = universityRepository.findAll();
        return universities.stream()
                .filter(university -> university.getTenantId().equals(tenant.getId()))
                .map(universityMapper::mapToDto)
                .toList();
    }

    public UniversityResponse getUniversityById(Long tenantId,
                                                Long universityId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        University university = universityRepository.findById(universityId)
                .filter(univ -> univ.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "University not found with id '" + universityId + "' in the specified tenant"));
        return universityMapper.mapToDto(university);
    }

    @Transactional
    public UniversityResponse updateUniversity(Long tenantId,
                                               Long universityId,
                                               UniversityRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        LocationDto location  = tenantServiceClient
                .getLocationById(request.getLocationId(), tenant.getId());
        University university = universityRepository.findById(universityId)
                .filter(univ -> univ.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "University not found with id '" + universityId + "' in the specified tenant"));
        if (request.getLocationId() != null) {
            university.setLocationId(location.getId());
        }
        university = universityMapper.updateEntity(university, request);
        university = universityRepository.save(university);
        return universityMapper.mapToDto(university);
    }

    @Transactional
    public void deleteUniversity(Long tenantId,
                                 Long universityId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        University university = universityRepository.findById(universityId)
                .filter(univ -> univ.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "University not found with id '" + universityId + "' in the specified tenant"));
        universityRepository.delete(university);
    }
}
