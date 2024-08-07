package com.insa.repository;

import com.google.common.collect.Collections2;
import com.insa.entity.PayGrade;
import com.insa.entity.StaffPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
@Repository
public interface StaffPlanRepository extends JpaRepository<StaffPlan, Long> {
    List<StaffPlan> findByDepartmentId(Long departmentId);

    boolean existsByTenantId(Long tenantId);
}