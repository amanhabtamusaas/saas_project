package com.organization_service.repository;

import com.organization_service.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {
//    Optional<Department> findByTenantId(UUID tenantId);
@Query("SELECT d FROM Department d WHERE d.parentDepartment IS NULL")
List<Department> findAllParentDepartments();
    boolean existsByDepartmentName(String departmentName);

    boolean existsByDepartmentNameAndParentDepartment(String departmentName, Department parentDepartment);

    List<Department> findByParentDepartment(Department parentDepartment);


    boolean existsByDepartmentNameAndTenantId(String departmentName, UUID tenantId);
}