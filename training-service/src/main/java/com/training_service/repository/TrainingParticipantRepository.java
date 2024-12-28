package com.training_service.repository;

import com.training_service.model.TrainingParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrainingParticipantRepository extends JpaRepository<TrainingParticipant, UUID> {

    boolean existsByTenantIdAndTrainingIdAndParticipantEmployeeId(UUID tenantId, UUID trainingId, UUID employeeId);
}
