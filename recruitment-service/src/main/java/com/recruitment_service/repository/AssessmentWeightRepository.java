package com.recruitment_service.repository;

import com.recruitment_service.model.AssessmentWeight;
import com.recruitment_service.model.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AssessmentWeightRepository extends JpaRepository<AssessmentWeight, UUID> {

    Optional<AssessmentWeight> findByRecruitmentId(UUID recruitmentId);
    boolean existsByRecruitment(Recruitment recruitment);
}
