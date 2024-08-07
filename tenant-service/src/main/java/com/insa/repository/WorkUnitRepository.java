package com.insa.repository;

import com.insa.entity.WorkUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.MethodFilter;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WorkUnitRepository extends JpaRepository<WorkUnit,Long> {
    boolean existsByWorkUnitNameAndTenantId(String workUnitName, Long tenantId);
}
