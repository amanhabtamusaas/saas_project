package com.insa.hr_planning_service.repository;

import com.insa.hr_planning_service.entity.AnnualRecruitmentAndPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnualRecruitmentAndPromotionRepository extends JpaRepository<AnnualRecruitmentAndPromotion, Long> {
    // Additional query methods (if needed) can be defined here
}
