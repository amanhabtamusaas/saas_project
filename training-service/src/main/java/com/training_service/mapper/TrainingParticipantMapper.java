package com.training_service.mapper;

import com.training_service.dto.response.TrainingParticipantResponse;
import com.training_service.model.TrainingParticipant;
import org.springframework.stereotype.Component;

@Component
public class TrainingParticipantMapper {

    public TrainingParticipantResponse mapToDto(TrainingParticipant trainingParticipant) {

        TrainingParticipantResponse response = new TrainingParticipantResponse();
        response.setId(trainingParticipant.getId());
        response.setParticipantEmployeeId(trainingParticipant.getParticipantEmployeeId());
        response.setTrainingId(trainingParticipant.getTraining().getId());
        response.setReason(trainingParticipant.getReason());
        response.setTenantId(trainingParticipant.getTenantId());
        response.setCreatedAt(trainingParticipant.getCreatedAt());
        response.setUpdatedAt(trainingParticipant.getUpdatedAt());
        response.setCreatedBy(trainingParticipant.getCreatedBy());
        response.setUpdatedBy(trainingParticipant.getUpdatedBy());

        return response;
    }
}
