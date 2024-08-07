package com.insa.mapper;

import com.insa.dto.requestDto.FieldOfStudyRequest;
import com.insa.dto.responseDto.FieldOfStudyResponse;
import com.insa.entity.FieldOfStudy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FieldOfStudyMapper {
    @Autowired
    private TenantMapper tenantMapper;
    @Autowired
    private JobRegistrationMapper jobRegistrationMapper;

    public FieldOfStudy mapToEntity(FieldOfStudyRequest fieldOfStudyRequest) {
        FieldOfStudy fieldOfStudy = new FieldOfStudy();
        if (fieldOfStudyRequest != null) {
            fieldOfStudy.setFieldOfStudy(fieldOfStudyRequest.getFieldOfStudy());
            fieldOfStudy.setDescription(fieldOfStudyRequest.getDescription());
//            if (fieldOfStudyRequest.getTenant() != null) {
//                fieldOfStudy.setTenant(tenantMapper.mapToEntity(fieldOfStudyRequest.getTenant()));
//            }
//            if (fieldOfStudyRequest.getJobRegistration() != null) {
//                fieldOfStudy.setJobRegistration(jobRegistrationMapper.mapToEntity(fieldOfStudyRequest.getJobRegistration()));
//            }
        }
        return fieldOfStudy;
    }

    public FieldOfStudyResponse mapToDto(FieldOfStudy fieldOfStudy) {
        FieldOfStudyResponse response = new FieldOfStudyResponse();
        if (fieldOfStudy != null) {
            response.setId(fieldOfStudy.getId());
            response.setFieldOfStudy(fieldOfStudy.getFieldOfStudy());
            response.setDescription(fieldOfStudy.getDescription());
            if (fieldOfStudy.getTenant() != null) {
                response.setTenantId(fieldOfStudy.getTenant().getId());
            }
//            if (fieldOfStudy.getJobRegistration() != null) {
//                response.setJobRegistrationId(fieldOfStudy.getJobRegistration().getId());
//            }
        }
        return response;
    }

    public FieldOfStudy updateFieldOfStudy(FieldOfStudy fieldOfStudy, FieldOfStudyRequest fieldOfStudyRequest) {
        if (fieldOfStudy != null && fieldOfStudyRequest != null) {
            fieldOfStudy.setFieldOfStudy(fieldOfStudyRequest.getFieldOfStudy());
            fieldOfStudy.setDescription(fieldOfStudyRequest.getDescription());
//            if (fieldOfStudyRequest.getTenant() != null) {
//                fieldOfStudy.setTenant(tenantMapper.mapToEntity(fieldOfStudyRequest.getTenant()));
//            }
//            if (fieldOfStudyRequest.getJobRegistration() != null) {
//                fieldOfStudy.setJobRegistration(jobRegistrationMapper.mapToEntity(fieldOfStudyRequest.getJobRegistration()));
//            }
        }
        return fieldOfStudy;
    }
}
