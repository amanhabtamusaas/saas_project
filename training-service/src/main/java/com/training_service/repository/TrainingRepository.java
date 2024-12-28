package com.training_service.repository;

import com.training_service.model.Training;
import com.training_service.enums.TrainingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TrainingRepository extends JpaRepository<Training, UUID> {

    List<Training> findByTenantIdAndTrainingStatus(UUID tenantId, TrainingStatus trainingStatus);
}
