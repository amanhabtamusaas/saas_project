package com.insa.repository;

import com.insa.entity.Recruitment;
import com.insa.enums.RecruitmentStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    Optional<Recruitment> findByVacancyNumber(String vacancyNumber);
    List<Recruitment> findByRecruitmentStatus(RecruitmentStatus recruitmentStatus);
}
