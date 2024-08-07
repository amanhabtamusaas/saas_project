package com.insa.service;

import com.insa.dto.apiDto.LocationDto;
import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.TrainingInstitutionRequest;
import com.insa.dto.response.TrainingInstitutionResponse;
import com.insa.entity.TrainingInstitution;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.TrainingInstitutionMapper;
import com.insa.repository.TrainingInstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingInstitutionService {

    private final TrainingInstitutionRepository trainingInstitutionRepository;
    private final TenantServiceClient tenantServiceClient;
    private final TrainingInstitutionMapper trainingInstitutionMapper;

    @Transactional
    public TrainingInstitutionResponse addTrainingInstitution(Long tenantId,
                                                              TrainingInstitutionRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        LocationDto location = tenantServiceClient
                .getLocationById(request.getLocationId(), tenant.getId());
        TrainingInstitution trainingInstitution = trainingInstitutionMapper.mapToEntity(request);
        if (trainingInstitutionRepository.existsByTenantIdAndInstitutionName(
                tenant.getId(), request.getInstitutionName())) {
            throw new ResourceExistsException(
                    "Training institution with name '" + request.getInstitutionName() + "' already exists");
        }
        trainingInstitution.setTenantId(tenant.getId());
        trainingInstitution.setLocationId(location.getId());
        trainingInstitution = trainingInstitutionRepository.save(trainingInstitution);
        return trainingInstitutionMapper.mapToDto(trainingInstitution);
    }

    public List<TrainingInstitutionResponse> getAllTrainingInstitutions(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<TrainingInstitution> trainingInstitutions = trainingInstitutionRepository.findAll();
        return trainingInstitutions.stream()
                .filter(inst -> inst.getTenantId().equals(tenant.getId()))
                .map(trainingInstitutionMapper::mapToDto)
                .toList();
    }

    public TrainingInstitutionResponse getTrainingInstitutionById(Long tenantId,
                                                              Long institutionId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        TrainingInstitution trainingInstitution = trainingInstitutionRepository.findById(institutionId)
                .filter(inst -> inst.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training institution not found with id '" + institutionId + "' in the specified tenant"));
        return trainingInstitutionMapper.mapToDto(trainingInstitution);
    }

    @Transactional
    public TrainingInstitutionResponse updateTrainingInstitution(Long tenantId,
                                                                 Long institutionId,
                                                                 TrainingInstitutionRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        LocationDto location = tenantServiceClient
                .getLocationById(request.getLocationId(), tenant.getId());
        TrainingInstitution trainingInstitution = trainingInstitutionRepository.findById(institutionId)
                .filter(inst -> inst.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training institution not found with id '" + institutionId + "' in the specified tenant"));
        trainingInstitution = trainingInstitutionMapper.updateEntity(trainingInstitution, request);
        if (request.getLocationId() != null) {
            trainingInstitution.setLocationId(location.getId());
        }
        trainingInstitution = trainingInstitutionRepository.save(trainingInstitution);
        return trainingInstitutionMapper.mapToDto(trainingInstitution);
    }

    @Transactional
    public void deleteTrainingInstitution(Long tenantId,
                                          Long institutionId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        TrainingInstitution trainingInstitution = trainingInstitutionRepository.findById(institutionId)
                .filter(inst -> inst.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training institution not found with id '" + institutionId + "' in the specified tenant"));
        trainingInstitutionRepository.delete(trainingInstitution);
    }
}
