package com.training_service.mapper;

import com.training_service.dto.clientDto.LocationDto;
import com.training_service.dto.clientDto.TenantDto;
import com.training_service.dto.request.TrainingInstitutionRequest;
import com.training_service.dto.response.TrainingInstitutionResponse;
import com.training_service.model.TrainingInstitution;
import com.training_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingInstitutionMapper {

    private final ValidationUtil validationUtil;

    public TrainingInstitution mapToEntity(TenantDto tenant,
                                           TrainingInstitutionRequest request) {

        LocationDto location = validationUtil
                .getLocationById(tenant.getId(), request.getLocationId());

        TrainingInstitution trainingInstitution = new TrainingInstitution();
        trainingInstitution.setTenantId(tenant.getId());
        trainingInstitution.setLocationId(location.getId());
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

    public TrainingInstitution updateEntity(TenantDto tenant,
                                            TrainingInstitution trainingInstitution,
                                            TrainingInstitutionRequest request) {

        LocationDto location = validationUtil
                .getLocationById(tenant.getId(), request.getLocationId());

        if (request.getLocationId() != null) {
            trainingInstitution.setLocationId(location.getId());
        }
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
