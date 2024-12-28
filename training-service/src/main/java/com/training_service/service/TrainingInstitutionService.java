package com.training_service.service;

import com.training_service.dto.clientDto.TenantDto;
import com.training_service.dto.request.TrainingInstitutionRequest;
import com.training_service.dto.response.TrainingInstitutionResponse;
import com.training_service.exception.ResourceExistsException;
import com.training_service.model.TrainingInstitution;
import com.training_service.mapper.TrainingInstitutionMapper;
import com.training_service.repository.TrainingInstitutionRepository;
import com.training_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainingInstitutionService {

    private final TrainingInstitutionRepository trainingInstitutionRepository;
    private final TrainingInstitutionMapper trainingInstitutionMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public TrainingInstitutionResponse addTrainingInstitution(UUID tenantId,
                                                              TrainingInstitutionRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TrainingInstitution trainingInstitution = trainingInstitutionMapper.mapToEntity(tenant, request);
        if (trainingInstitutionRepository.existsByTenantIdAndInstitutionName(
                tenant.getId(), request.getInstitutionName())) {
            throw new ResourceExistsException(
                    "Training institution with name '" + request.getInstitutionName() + "' already exists");
        }
        trainingInstitution = trainingInstitutionRepository.save(trainingInstitution);
        return trainingInstitutionMapper.mapToDto(trainingInstitution);
    }

    public List<TrainingInstitutionResponse> getAllTrainingInstitutions(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<TrainingInstitution> trainingInstitutions = trainingInstitutionRepository.findAll();
        return trainingInstitutions.stream()
                .filter(inst -> inst.getTenantId().equals(tenant.getId()))
                .map(trainingInstitutionMapper::mapToDto)
                .toList();
    }

    public TrainingInstitutionResponse getTrainingInstitutionById(UUID tenantId,
                                                                  UUID institutionId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TrainingInstitution trainingInstitution = validationUtil
                .getInstitutionById(tenant.getId(), institutionId);
        return trainingInstitutionMapper.mapToDto(trainingInstitution);
    }

    @Transactional
    public TrainingInstitutionResponse updateTrainingInstitution(UUID tenantId,
                                                                 UUID institutionId,
                                                                 TrainingInstitutionRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TrainingInstitution trainingInstitution = validationUtil
                .getInstitutionById(tenant.getId(), institutionId);
        trainingInstitution = trainingInstitutionMapper
                .updateEntity(tenant, trainingInstitution, request);
        trainingInstitution = trainingInstitutionRepository.save(trainingInstitution);
        return trainingInstitutionMapper.mapToDto(trainingInstitution);
    }

    @Transactional
    public void deleteTrainingInstitution(UUID tenantId,
                                          UUID institutionId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TrainingInstitution trainingInstitution = validationUtil
                .getInstitutionById(tenant.getId(), institutionId);
        trainingInstitutionRepository.delete(trainingInstitution);
    }
}