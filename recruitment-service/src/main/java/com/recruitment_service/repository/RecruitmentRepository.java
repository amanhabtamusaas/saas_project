package com.recruitment_service.repository;

import com.recruitment_service.model.Recruitment;
import com.recruitment_service.enums.RecruitmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecruitmentRepository extends JpaRepository<Recruitment, UUID> {
    Optional<Recruitment> findByVacancyNumber(String vacancyNumber);
    List<Recruitment> findByRecruitmentStatus(RecruitmentStatus recruitmentStatus);
}
