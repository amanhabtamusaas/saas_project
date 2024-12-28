package com.recruitment_service.repository;

import com.recruitment_service.model.Advertisement;
import com.recruitment_service.model.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdvertisementRepository extends JpaRepository<Advertisement, UUID> {

    Advertisement findByRecruitmentId(UUID recruitmentId);
    boolean existsByRecruitment(Recruitment recruitmentId);
}
