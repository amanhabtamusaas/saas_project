package com.training_service.service;

import com.training_service.dto.clientDto.TenantDto;
import com.training_service.dto.request.TrainingApproveRequest;
import com.training_service.dto.request.TrainingRequest;
import com.training_service.dto.response.TrainingResponse;
import com.training_service.model.Training;
import com.training_service.enums.TrainingStatus;
import com.training_service.mapper.TrainingMapper;
import com.training_service.repository.TrainingRepository;
import com.training_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public TrainingResponse addTraining(UUID tenantId,
                                        TrainingRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Training training = trainingMapper.mapToEntity(tenant, request);
        training = trainingRepository.save(training);
        return trainingMapper.mapToDto(training);
    }

    public List<TrainingResponse> getAllTrainings(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Training> trainings = trainingRepository.findAll();
        return trainings.stream()
                .filter(training -> training.getTenantId().equals(tenant.getId()))
                .map(trainingMapper::mapToDto)
                .toList();
    }

    public TrainingResponse getTrainingById(UUID tenantId,
                                            UUID trainingId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Training training = validationUtil.getTrainingById(tenant.getId(), trainingId);
        return trainingMapper.mapToDto(training);
    }

    public List<TrainingResponse> getTrainingsByStatus(UUID tenantId,
                                                       String trainingStatus) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Training> trainings = trainingRepository
                .findByTenantIdAndTrainingStatus(
                        tenant.getId(), TrainingStatus.valueOf(trainingStatus.toUpperCase()));
        return trainings.stream()
                .filter(training -> training.getTenantId().equals(tenant.getId()))
                .map(trainingMapper::mapToDto)
                .toList();
    }

    @Transactional
    public TrainingResponse updateTraining(UUID tenantId,
                                           UUID trainingId,
                                           TrainingRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Training training = validationUtil.getTrainingById(tenant.getId(), trainingId);
        training = trainingMapper.updateEntity(tenant, training, request);
        training = trainingRepository.save(training);
        return trainingMapper.mapToDto(training);
    }

    @Transactional
    public TrainingResponse approveTraining(UUID tenantId,
                                            UUID trainingId,
                                            TrainingApproveRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Training training = validationUtil.getTrainingById(tenant.getId(), trainingId);
        training = trainingMapper.approveTraining(training, request);
        training = trainingRepository.save(training);
        return trainingMapper.mapToDto(training);
    }

    @Transactional
    public void deleteTraining(UUID tenantId,
                               UUID trainingId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Training training = validationUtil.getTrainingById(tenant.getId(), trainingId);
        trainingRepository.delete(training);
    }
}