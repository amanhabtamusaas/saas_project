package com.insa.service;

import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.PreServiceCourseTypeRequest;
import com.insa.dto.response.PreServiceCourseTypeResponse;
import com.insa.entity.PreServiceCourseType;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.PreServiceCourseTypeMapper;
import com.insa.repository.PreServiceCourseTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreServiceCourseTypeService {

    private final PreServiceCourseTypeRepository preServiceCourseTypeRepository;
    private final TenantServiceClient tenantServiceClient;
    private final PreServiceCourseTypeMapper preServiceCourseTypeMapper;

    @Transactional
    public PreServiceCourseTypeResponse addCourseType(Long tenantId,
                                                      PreServiceCourseTypeRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceCourseType preServiceCourseType = preServiceCourseTypeMapper.mapToEntity(request);
        if (preServiceCourseTypeRepository.existsByTenantIdAndCourseType (
                tenant.getId(), request.getCourseType())) {
            throw new ResourceExistsException(
                    "Course type with name '" + request.getCourseType() + "' already exists in the specified tenant");
        }
        preServiceCourseType.setTenantId(tenant.getId());
        preServiceCourseType = preServiceCourseTypeRepository.save(preServiceCourseType);
        return preServiceCourseTypeMapper.mapToDto(preServiceCourseType);
    }

    public List<PreServiceCourseTypeResponse> getAllCourseTypes(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<PreServiceCourseType> preServiceCourseTypes = preServiceCourseTypeRepository.findAll();
        return preServiceCourseTypes.stream()
                .filter(type -> type.getTenantId().equals(tenant.getId()))
                .map(preServiceCourseTypeMapper::mapToDto)
                .toList();
    }

    public PreServiceCourseTypeResponse getCourseType(Long tenantId,
                                                      Long courseTypeId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceCourseType preServiceCourseType = preServiceCourseTypeRepository.findById(courseTypeId)
                .filter(type -> type.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course type not found with id '" + courseTypeId + "' in the specified tenant"));
        return preServiceCourseTypeMapper.mapToDto(preServiceCourseType);
    }

    @Transactional
    public PreServiceCourseTypeResponse updateCourseType(Long tenantId,
                                                         Long courseTypeId,
                                                         PreServiceCourseTypeRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceCourseType preServiceCourseType = preServiceCourseTypeRepository.findById(courseTypeId)
                .filter(type -> type.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course type not found with id '" + courseTypeId + "' in the specified tenant"));
        preServiceCourseType = preServiceCourseTypeMapper.updateEntity(preServiceCourseType, request);
        preServiceCourseType = preServiceCourseTypeRepository.save(preServiceCourseType);
        return preServiceCourseTypeMapper.mapToDto(preServiceCourseType);
    }

    @Transactional
    public void deleteCourseType(Long tenantId,
                                Long courseTypeId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceCourseType preServiceCourseType = preServiceCourseTypeRepository.findById(courseTypeId)
                .filter(type -> type.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course type not found with id '" + courseTypeId + "' in the specified tenant"));
        preServiceCourseTypeRepository.delete(preServiceCourseType);
    }
}
