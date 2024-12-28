package com.employee_service.repository;

import com.employee_service.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    List<Employee> findByDepartmentId(UUID depId);

    boolean existsByEmployeeIdAndTenantId(String employeeId, UUID tenantId);

    Optional<Employee> findByEmployeeId(String employeeId);

    @Query("SELECT e FROM Employee e WHERE e.firstName = :firstName " +
            "AND (:middleName IS NULL OR e.middleName = :middleName " +
            "OR :lastName IS NULL OR e.lastName = :lastName) " +
            "AND (:lastName IS NULL OR e.lastName = :lastName)")
    Optional<Employee> findByName(
            @Param("firstName") String firstName,
            @Param("middleName") String middleName,
            @Param("lastName") String lastName);

}
