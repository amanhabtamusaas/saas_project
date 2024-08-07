package com.insa.mapper;

import com.insa.dto.request.TrainingInstitutionRequest;
import com.insa.dto.response.TrainingInstitutionResponse;
import com.insa.entity.TrainingInstitution;
import org.springframework.stereotype.Component;

@Component
public class TrainingInstitutionMapper {

    public TrainingInstitution mapToEntity(TrainingInstitutionRequest request) {

        TrainingInstitution trainingInstitution = new TrainingInstitution();
        trainingInstitution.setInstitutionName(request.getInstitutionName());
        trainingInstitution.setCostPerPerson(request.getCostPerPerson());
        trainingInstitution.setPhoneNumber(request.getPhoneNumber());
        trainingInstitution.setEmail(request.getEmail());
        trainingInstitution.setFax(request.getFax());
        trainingInstitution.setWebsite(request.getWebsite());
        trainingInstitution.setTinNumber(request.getTinNumber());
        trainingInstitution.setRemark(request.getRemark());

        return trainingInstitution;
    }

    public TrainingInstitutionResponse mapToDto(TrainingInstitution trainingInstitution) {

        TrainingInstitutionResponse response = new TrainingInstitutionResponse();
        response.setId(trainingInstitution.getId());
        response.setInstitutionName(trainingInstitution.getInstitutionName());
        response.setCostPerPerson(trainingInstitution.getCostPerPerson());
        response.setPhoneNumber(trainingInstitution.getPhoneNumber());
        response.setEmail(trainingInstitution.getEmail());
        response.setFax(trainingInstitution.getFax());
        response.setWebsite(trainingInstitution.getWebsite());
        response.setTinNumber(trainingInstitution.getTinNumber());
        response.setRemark(trainingInstitution.getRemark());
        response.setLocationId(trainingInstitution.getLocationId());
        response.setTenantId(trainingInstitution.getTenantId());
        response.setCreatedAt(trainingInstitution.getCreatedAt());
        response.setUpdatedAt(trainingInstitution.getUpdatedAt());
        response.setUpdatedAt(trainingInstitution.getUpdatedAt());
        response.setUpdatedBy(trainingInstitution.getUpdatedBy());

        return response;
    }

    public TrainingInstitution updateEntity(TrainingInstitution trainingInstitution,
                                            TrainingInstitutionRequest request) {

        if (request.getInstitutionName() != null) {
            trainingInstitution.setInstitutionName(request.getInstitutionName());
        }
        if (request.getCostPerPerson() != null) {
            trainingInstitution.setCostPerPerson(request.getCostPerPerson());
        }
        if (request.getPhoneNumber() != null) {
            trainingInstitution.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getEmail() != null) {
            trainingInstitution.setEmail(request.getEmail());
        }
        if (request.getFax() != null) {
            trainingInstitution.setFax(request.getFax());
        }
        if (request.getWebsite() != null) {
            trainingInstitution.setWebsite(request.getWebsite());
        }
        if (request.getTinNumber() != null) {
            trainingInstitution.setTinNumber(request.getTinNumber());
        }
        if (request.getRemark() != null) {
            trainingInstitution.setRemark(request.getRemark());
        }

        return trainingInstitution;
    }
}
