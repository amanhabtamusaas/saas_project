package com.insa.mapper;
import com.insa.dto.requestDto.QualificationRequest;
import com.insa.dto.responseDto.QualificationResponse;
import com.insa.entity.Qualification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QualificationMapper {
    @Autowired
    private TenantMapper tenantMapper;
    @Autowired
    private JobRegistrationMapper jobRegistrationMapper;

    public Qualification mapToEntity(QualificationRequest qualificationRequest) {
        Qualification qualification = new Qualification();
        if (qualificationRequest != null) {
            qualification.setQualification(qualificationRequest.getQualification());
            qualification.setDescription(qualificationRequest.getDescription());
//            if (qualificationRequest.getTenant() != null) {
//                qualification.setTenant(tenantMapper.mapToEntity(qualificationRequest.getTenant()));
//            }
//            if (qualificationRequest.getJobRegistration() != null) {
//                qualification.setJobRegistration(jobRegistrationMapper.mapToEntity(qualificationRequest.getJobRegistration()));
//            }
        }
        return qualification;
    }

    public QualificationResponse mapToDto(Qualification qualification) {
        QualificationResponse response = new QualificationResponse();
        if (qualification != null) {
            response.setId(qualification.getId());
            response.setQualification(qualification.getQualification());
            response.setDescription(qualification.getDescription());
            if (qualification.getTenant() != null) {
                response.setTenantId(qualification.getTenant().getId());
            }
           //
        }
        return response;
    }

    public Qualification updateQualification(Qualification qualification, QualificationRequest qualificationRequest) {
        if (qualification != null && qualificationRequest != null) {
            qualification.setQualification(qualificationRequest.getQualification());
            qualification.setDescription(qualificationRequest.getDescription());
//            if (qualificationRequest.getTenant() != null) {
//                qualification.setTenant(tenantMapper.mapToEntity(qualificationRequest.getTenant()));
//            }
//            if (qualificationRequest.getJobRegistration() != null) {
//                qualification.setJobRegistration(jobRegistrationMapper.mapToEntity(qualificationRequest.getJobRegistration()));
//            }
        }
        return qualification;
    }
}

