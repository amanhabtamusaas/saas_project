package com.insa.repository;

import com.insa.entity.Address;
import com.insa.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
//    Optional<Department> findByTenantId(Long tenantId);
@Query("SELECT d FROM Department d WHERE d.parentDepartment IS NULL")
List<Department> findAllParentDepartments();
    boolean existsByDepartmentName(String departmentName);

    boolean existsByDepartmentNameAndParentDepartment(String departmentName, Department parentDepartment);

    List<Department> findByParentDepartment(Department parentDepartment);


    boolean existsByDepartmentNameAndTenantId(String departmentName, Long tenantId);
}