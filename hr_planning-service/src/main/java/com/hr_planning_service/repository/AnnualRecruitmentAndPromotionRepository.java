package com.hr_planning_service.repository;

import com.hr_planning_service.model.AnnualRecruitmentAndPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnnualRecruitmentAndPromotionRepository extends JpaRepository<AnnualRecruitmentAndPromotion, UUID> {
    // Additional query methods (if needed) can be defined here
}
