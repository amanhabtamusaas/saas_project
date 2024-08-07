package com.insa.repository;

import com.insa.entity.Advertisement;
import com.insa.entity.MediaType;
import com.insa.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    Advertisement findByRecruitmentId(Long recruitmentId);
    boolean existsByRecruitment(Recruitment recruitmentId);
}
