package com.insa.repository;

import com.insa.entity.Department;
import com.insa.entity.DepartmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface DepartmentHistoryRepository extends JpaRepository<DepartmentHistory,Long> {
    Optional<DepartmentHistory> findByTenantId(Long tenantId);

}
