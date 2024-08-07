package com.insa.repository;

import com.insa.entity.Training;
import com.insa.enums.TrainingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findByTenantIdAndTrainingStatus(Long tenantId, TrainingStatus trainingStatus);
}
