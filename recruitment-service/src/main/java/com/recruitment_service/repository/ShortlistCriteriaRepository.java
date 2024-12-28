package com.recruitment_service.repository;

import com.recruitment_service.model.Recruitment;
import com.recruitment_service.model.ShortlistCriteria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ShortlistCriteriaRepository extends JpaRepository<ShortlistCriteria, UUID> {

    List<ShortlistCriteria> findByTenantIdAndRecruitment(UUID tenantId, Recruitment recruitment);
}
