package com.insa.repository;

import com.insa.entity.Education;
import com.insa.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    List<Experience> findByEmployeeId(Long empId);
}
