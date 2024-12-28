package com.training_service.service;

import com.training_service.dto.clientDto.EmployeeDto;
import com.training_service.dto.clientDto.TenantDto;
import com.training_service.dto.request.TrainingParticipantRequest;
import com.training_service.dto.response.TrainingParticipantResponse;
import com.training_service.exception.ResourceExistsException;
import com.training_service.exception.ResourceNotFoundException;
import com.training_service.model.Training;
import com.training_service.model.TrainingParticipant;
import com.training_service.mapper.TrainingParticipantMapper;
import com.training_service.repository.TrainingParticipantRepository;
import com.training_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainingParticipantService {

    private final TrainingParticipantRepository trainingParticipantRepository;
    private final TrainingParticipantMapper trainingParticipantMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public TrainingParticipantResponse addTrainingParticipant(UUID tenantId,
                                                              UUID trainingId,
                                                              TrainingParticipantRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Training training = validationUtil.getTrainingById(tenant.getId(), trainingId);
        EmployeeDto employee;
        if (request.getSearchBy().equalsIgnoreCase("NAME")) {
            employee = validationUtil
                    .getEmployeeByName(tenant.getId(),
                            request.getFirstName(), request.getMiddleName(), request.getLastName());
        } else {
            employee = validationUtil
                    .getEmployeeByEmployeeId(tenant.getId(), request.getParticipantEmployeeId());
        }
        if (trainingParticipantRepository.existsByTenantIdAndTrainingIdAndParticipantEmployeeId(
                tenant.getId(), trainingId, employee.getId())) {
            throw new ResourceExistsException(
                    "Participant with employee id '" + employee.getEmployeeId() + "' already exists");
        }
        TrainingParticipant trainingParticipant = new TrainingParticipant();
        trainingParticipant.setTenantId(tenant.getId());
        trainingParticipant.setParticipantEmployeeId(employee.getId());
        trainingParticipant.setTraining(training);
        trainingParticipant.setReason(request.getReason());
        trainingParticipant = trainingParticipantRepository.save(trainingParticipant);
        return trainingParticipantMapper.mapToDto(trainingParticipant);
    }

    public List<TrainingParticipantResponse> getAllTrainingParticipants(UUID tenantId,
                                                                        UUID trainingId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Training training = validationUtil.getTrainingById(tenant.getId(), trainingId);
        List<TrainingParticipant> trainingParticipants = trainingParticipantRepository.findAll();
        return trainingParticipants.stream()
                .filter(participant -> participant.getTenantId().equals(tenant.getId()))
                .filter(participant -> participant.getTraining().equals(training))
                .map(trainingParticipantMapper::mapToDto)
                .toList();
    }

    public TrainingParticipantResponse getTrainingParticipantById(UUID tenantId,
                                                                  UUID trainingId,
                                                                  UUID participantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Training training = validationUtil.getTrainingById(tenant.getId(), trainingId);
        TrainingParticipant trainingParticipant = getParticipantById(tenant.getId(), participantId);
        return trainingParticipantMapper.mapToDto(trainingParticipant);
    }

    @Transactional
    public TrainingParticipantResponse updateTrainingParticipant(UUID tenantId,
                                                                 UUID trainingId,
                                                                 UUID participantId,
                                                                 TrainingParticipantRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Training training = validationUtil.getTrainingById(tenant.getId(), trainingId);
        EmployeeDto employee;
        if (request.getSearchBy().equalsIgnoreCase("NAME")) {
            employee = validationUtil
                    .getEmployeeByName(tenant.getId(),
                            request.getFirstName(), request.getMiddleName(), request.getLastName());
        } else {
            employee = validationUtil
                    .getEmployeeByEmployeeId(tenant.getId(), request.getParticipantEmployeeId());
        }
        TrainingParticipant trainingParticipant = getParticipantById(tenant.getId(), participantId);
        if (request.getParticipantEmployeeId() != null) {
            trainingParticipant.setParticipantEmployeeId(employee.getId());
        }
        if (request.getReason() != null) {
            trainingParticipant.setReason(request.getReason());
        }
        trainingParticipant = trainingParticipantRepository.save(trainingParticipant);
        return trainingParticipantMapper.mapToDto(trainingParticipant);
    }

    @Transactional
    public void deleteTrainingParticipant(UUID tenantId,
                                          UUID trainingId,
                                          UUID participantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Training training = validationUtil.getTrainingById(tenant.getId(), trainingId);
        TrainingParticipant trainingParticipant = getParticipantById(tenant.getId(), participantId);
        trainingParticipantRepository.delete(trainingParticipant);
    }

    private TrainingParticipant getParticipantById(UUID tenantId, UUID participantId) {
        return trainingParticipantRepository.findById(participantId)
                .filter(participant -> participant.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training participant not found with id '" + participantId + "'"));
    }
}