package com.insa.repository;

import com.insa.entity.AssessmentWeight;
import com.insa.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssessmentWeightRepository extends JpaRepository<AssessmentWeight, Long> {

    Optional<AssessmentWeight> findByRecruitmentId(Long recruitmentId);
    boolean existsByRecruitment(Recruitment recruitment);
}
