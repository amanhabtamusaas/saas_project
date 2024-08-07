package com.insa.mapper;

import com.insa.dto.requestDto.EducationLevelRequest;
import com.insa.dto.responseDto.EducationLevelResponse;
import com.insa.entity.EducationLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EducationLevelMapper {
    @Autowired
    private TenantMapper tenantMapper;
    @Autowired
    private JobRegistrationMapper jobRegistrationMapper;

    public EducationLevel mapToEntity(EducationLevelRequest educationLevelRequest) {
        EducationLevel educationLevel = new EducationLevel();
        if (educationLevelRequest != null) {
            educationLevel.setEducationLevelName(educationLevelRequest.getEducationLevelName());
            educationLevel.setDescription(educationLevelRequest.getDescription());
            // Assuming tenant mapping is required
//            educationLevel.setTenant(tenantMapper.mapToEntity(educationLevelRequest.getTenant()));
//            // Assuming job registration mapping is required
//            educationLevel.setJobRegistration(jobRegistrationMapper.mapToEntity(educationLevelRequest.getJobRegistration()));
        }
        return educationLevel;
    }

    public EducationLevelResponse mapToDto(EducationLevel educationLevel) {
        EducationLevelResponse response = new EducationLevelResponse();
        if (educationLevel != null) {
            response.setId(educationLevel.getId());
            response.setEducationLevelName(educationLevel.getEducationLevelName());
            response.setDescription(educationLevel.getDescription());
            // Assuming tenant ID mapping is required
            response.setTenantId(educationLevel.getTenant().getId());
            // Assuming job registration ID mapping is required
//            response.setJobRegistrationId(educationLevel.getJobRegistration().getId());
        }
        return response;
    }

    public EducationLevel updateEducationLevel(EducationLevel educationLevel, EducationLevelRequest educationLevelRequest) {
        if (educationLevel != null && educationLevelRequest != null) {
            educationLevel.setEducationLevelName(educationLevelRequest.getEducationLevelName());
            educationLevel.setDescription(educationLevelRequest.getDescription());
            // Assuming tenant mapping is required
//            educationLevel.setTenant(tenantMapper.mapToEntity(educationLevelRequest.getTenant()));
//            // Assuming job registration mapping is required
//            educationLevel.setJobRegistration(jobRegistrationMapper.mapToEntity(educationLevelRequest.getJobRegistration()));
        }
        return educationLevel;
    }
}

