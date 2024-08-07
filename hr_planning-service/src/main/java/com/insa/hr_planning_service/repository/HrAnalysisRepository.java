package com.insa.hr_planning_service.repository;

import com.insa.hr_planning_service.entity.HrAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HrAnalysisRepository extends JpaRepository<HrAnalysis, Long> {
    // Add any custom query methods if necessary
}
