package com.insa.service;

import com.insa.dto.apiDto.EmployeeDto;
import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.TrainingParticipantRequest;
import com.insa.dto.response.TrainingParticipantResponse;
import com.insa.entity.Training;
import com.insa.entity.TrainingParticipant;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.TrainingParticipantMapper;
import com.insa.repository.TrainingRepository;
import com.insa.repository.TrainingParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingParticipantService {

    private final TrainingParticipantRepository TrainingParticipantRepository;
    private final TrainingRepository trainingRepository;
    private final TenantServiceClient tenantServiceClient;
    private final EmployeeServiceClient employeeServiceClient;
    private final TrainingParticipantMapper trainingParticipantMapper;
    private final TrainingParticipantRepository trainingParticipantRepository;

    @Transactional
    public TrainingParticipantResponse addTrainingParticipant(Long tenantId,
                                                              Long trainingId,
                                                              TrainingParticipantRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Training training = trainingRepository.findById(trainingId)
                .filter(tr -> tr.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training not found with id '" + trainingId + "' in the specified tenant"));
        EmployeeDto employee;
        if (request.getSearchBy().equalsIgnoreCase("NAME")) {
            employee = employeeServiceClient
                    .getEmployeeByName(tenant.getId(),
                            request.getFirstName(), request.getMiddleName(), request.getLastName());
        } else {
            employee = employeeServiceClient
                    .getEmployeeByEmployeeId(tenant.getId(), request.getParticipantEmployeeId());
        }
        if (trainingParticipantRepository.existsByTenantIdAndTrainingIdAndParticipantEmployeeId(
                tenant.getId(), trainingId, employee.getId())) {
            throw new ResourceExistsException(
                    "participant with employee id '" + employee.getEmployeeId() + "' already exists");
        }
        TrainingParticipant trainingParticipant = new TrainingParticipant();
        trainingParticipant.setTenantId(tenant.getId());
        trainingParticipant.setParticipantEmployeeId(employee.getId());
        trainingParticipant.setTraining(training);
        trainingParticipant.setReason(request.getReason());
        trainingParticipant = TrainingParticipantRepository.save(trainingParticipant);
        return trainingParticipantMapper.mapToDto(trainingParticipant);
    }

    public List<TrainingParticipantResponse> getAllTrainingParticipants(Long tenantId,
                                                                        Long trainingId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Training training = trainingRepository.findById(trainingId)
                .filter(tr -> tr.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training not found with id '" + trainingId + "' in the specified tenant"));
        List<TrainingParticipant> trainingParticipants = TrainingParticipantRepository.findAll();
        return trainingParticipants.stream()
                .filter(participant -> participant.getTenantId().equals(tenant.getId()))
                .filter(participant -> {
                    Training participantTraining = participant.getTraining();
                    return participantTraining != null && participantTraining.equals(training);
                })
                .map(trainingParticipantMapper::mapToDto)
                .toList();
    }

    public TrainingParticipantResponse getTrainingParticipantById(Long tenantId,
                                                                  Long trainingId,
                                                                  Long participantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Training training = trainingRepository.findById(trainingId)
                .filter(tr -> tr.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training not found with id: " + trainingId + "' in the specified tenant"));
        TrainingParticipant trainingParticipant = TrainingParticipantRepository.findById(participantId)
                .filter(participant -> participant.getTenantId().equals(tenant.getId()))
                .filter(participant -> {
                    Training participantTraining = participant.getTraining();
                    return participantTraining != null && participantTraining.equals(training);
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training participant not found with id '" + participantId + "' in the specified tenant"));
        return trainingParticipantMapper.mapToDto(trainingParticipant);
    }

    @Transactional
    public TrainingParticipantResponse updateTrainingParticipant(Long tenantId,
                                                                 Long trainingId,
                                                                 Long participantId,
                                                                 TrainingParticipantRequest  request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Training training = trainingRepository.findById(trainingId)
                .filter(tr -> tr.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training not found with id '" + trainingId + "' in the specified tenant"));
        EmployeeDto employee;
        if (request.getSearchBy().equalsIgnoreCase("NAME")) {
            employee = employeeServiceClient
                    .getEmployeeByName(tenant.getId(),
                            request.getFirstName(), request.getMiddleName(), request.getLastName());
        } else {
            employee = employeeServiceClient
                    .getEmployeeByEmployeeId(tenant.getId(), request.getParticipantEmployeeId());
        }
        TrainingParticipant trainingParticipant = TrainingParticipantRepository.findById(participantId)
                .filter(participant -> participant.getTenantId().equals(tenant.getId()))
                .filter(participant -> {
                    Training participantTraining = participant.getTraining();
                    return participantTraining != null && participantTraining.equals(training);
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training participant not found with id '" + participantId + "' in the specified tenant"));
        if (request.getParticipantEmployeeId() != null) {
            trainingParticipant.setParticipantEmployeeId(employee.getId());
        }
        if (request.getReason() != null) {
            trainingParticipant.setReason(request.getReason());
        }
        trainingParticipant = TrainingParticipantRepository.save(trainingParticipant);
        return trainingParticipantMapper.mapToDto(trainingParticipant);
    }

    @Transactional
    public void deleteTrainingParticipant(Long tenantId,
                                          Long trainingId,
                                          Long participantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Training training = trainingRepository.findById(trainingId)
                .filter(tr -> tr.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training not found with id '" + trainingId + "' in the specified tenant"));
        TrainingParticipant trainingParticipant = TrainingParticipantRepository.findById(participantId)
                .filter(participant -> participant.getTenantId().equals(tenant.getId()))
                .filter(participant -> {
                    Training participantTraining = participant.getTraining();
                    return participantTraining != null && participantTraining.equals(training);
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training participant not found with id '" + participantId + "' in the specified tenant"));
        TrainingParticipantRepository.delete(trainingParticipant);
    }
}
