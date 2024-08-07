package com.insa.service;

import com.insa.dto.requestDto.EducationLevelRequest;
import com.insa.dto.responseDto.EducationLevelResponse;
import com.insa.entity.Address;
import com.insa.entity.EducationLevel;
import com.insa.entity.Tenant;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.EducationLevelMapper;
import com.insa.repository.EducationLevelRepository;
import com.insa.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationLevelService {

//    private static final Logger logger = LoggerFactory.getLogger(EducationLevelService.class);

    private final EducationLevelRepository educationLevelRepository;
    private final EducationLevelMapper educationLevelMapper;
    private final TenantRepository tenantRepository;

    public EducationLevelResponse createEducationLevel(Long tenantId, EducationLevelRequest educationLevelRequest) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + tenantId + " "));
        if (educationLevelRepository.existsByEducationLevelNameAndTenantId(educationLevelRequest.getEducationLevelName(),tenant.getId())) {
            throw new ResourceExistsException("Education Level with Name " + educationLevelRequest.getEducationLevelName() + " already exists");
        }

        EducationLevel educationLevel = educationLevelMapper.mapToEntity(educationLevelRequest);
        educationLevel.setTenant(tenant);
        educationLevel = educationLevelRepository.save(educationLevel);
        return educationLevelMapper.mapToDto(educationLevel);
    }

    public List<EducationLevelResponse> getAllEducationLevels(Long tenantId) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + tenantId + " "));

        List<EducationLevel> educationLevels = educationLevelRepository.findByTenant(tenant);
        return educationLevels.stream()
                .map(educationLevelMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public EducationLevelResponse getEducationLevelById(Long id, Long tenantId) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + tenantId + " "));
        EducationLevel educationLevel = educationLevelRepository.findByIdAndTenant(id, tenant)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Education level not found with id: " + id + " for the specified tenant "));
        return educationLevelMapper.mapToDto(educationLevel);
    }

    @Transactional
    public EducationLevelResponse updateEducationLevel(Long id, Long tenantId, EducationLevelRequest educationLevelRequest) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + tenantId + " "));
        EducationLevel educationLevel = educationLevelRepository.findByIdAndTenant(id, tenant)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Education level not found with id: " + id + " for the specified tenant "));
        educationLevel = educationLevelMapper.updateEducationLevel(educationLevel, educationLevelRequest);
        educationLevel = educationLevelRepository.save(educationLevel);
        return educationLevelMapper.mapToDto(educationLevel);
    }

    @Transactional
    public void deleteEducationLevel(Long id, Long tenantId) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + tenantId + " "));
        EducationLevel educationLevel = educationLevelRepository.findByIdAndTenant(id, tenant)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Education level not found with id: " + id + " for the specified tenant "));
        educationLevelRepository.delete(educationLevel);
    }
}
