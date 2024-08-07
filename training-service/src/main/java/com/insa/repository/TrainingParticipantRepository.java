package com.insa.repository;

import com.insa.entity.TrainingParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingParticipantRepository extends JpaRepository<TrainingParticipant, Long> {

    boolean existsByTenantIdAndTrainingIdAndParticipantEmployeeId(Long tenantId, Long trainingId, Long employeeId);
}
