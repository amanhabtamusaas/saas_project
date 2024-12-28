package com.training_service.service;

import com.training_service.dto.clientDto.TenantDto;
import com.training_service.dto.request.PreServiceCourseTypeRequest;
import com.training_service.dto.response.PreServiceCourseTypeResponse;
import com.training_service.exception.ResourceExistsException;
import com.training_service.model.PreServiceCourseType;
import com.training_service.mapper.PreServiceCourseTypeMapper;
import com.training_service.repository.PreServiceCourseTypeRepository;
import com.training_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PreServiceCourseTypeService {

    private final PreServiceCourseTypeRepository preServiceCourseTypeRepository;
    private final PreServiceCourseTypeMapper preServiceCourseTypeMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public PreServiceCourseTypeResponse addCourseType(UUID tenantId,
                                                      PreServiceCourseTypeRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceCourseType courseType = preServiceCourseTypeMapper.mapToEntity(tenant, request);
        if (preServiceCourseTypeRepository.existsByTenantIdAndCourseType(
                tenant.getId(), request.getCourseType())) {
            throw new ResourceExistsException(
                    "Course type with name '" + request.getCourseType() + "' already exists");
        }
        courseType = preServiceCourseTypeRepository.save(courseType);
        return preServiceCourseTypeMapper.mapToDto(courseType);
    }

    public List<PreServiceCourseTypeResponse> getAllCourseTypes(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<PreServiceCourseType> preServiceCourseTypes = preServiceCourseTypeRepository.findAll();
        return preServiceCourseTypes.stream()
                .filter(type -> type.getTenantId().equals(tenant.getId()))
                .map(preServiceCourseTypeMapper::mapToDto)
                .toList();
    }

    public PreServiceCourseTypeResponse getCourseTypeById(UUID tenantId,
                                                          UUID courseTypeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceCourseType preServiceCourseType = validationUtil
                .getCourseTypeById(tenant.getId(), courseTypeId);
        return preServiceCourseTypeMapper.mapToDto(preServiceCourseType);
    }

    @Transactional
    public PreServiceCourseTypeResponse updateCourseType(UUID tenantId,
                                                         UUID courseTypeId,
                                                         PreServiceCourseTypeRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceCourseType preServiceCourseType = validationUtil
                .getCourseTypeById(tenant.getId(), courseTypeId);
        preServiceCourseType = preServiceCourseTypeMapper.updateEntity(preServiceCourseType, request);
        preServiceCourseType = preServiceCourseTypeRepository.save(preServiceCourseType);
        return preServiceCourseTypeMapper.mapToDto(preServiceCourseType);
    }

    @Transactional
    public void deleteCourseType(UUID tenantId,
                                 UUID courseTypeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceCourseType preServiceCourseType = validationUtil
                .getCourseTypeById(tenant.getId(), courseTypeId);
        preServiceCourseTypeRepository.delete(preServiceCourseType);
    }
}