package com.insa.repository;

import com.insa.entity.PayGrade;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PayGradeRepository extends JpaRepository<PayGrade, Long> {
    boolean existsBySalaryStep(Integer salaryStep);
    @Query("SELECT p FROM PayGrade p WHERE p.jobGrade.id = :jobGradeId")
    List<PayGrade> findByJobGradeId(@Param("jobGradeId") Long jobGradeId);

    boolean existsBySalaryStepAndTenantId(Integer salaryStep, Long tenantId);


//    boolean existsByPayGrade(String payGrade);
}
