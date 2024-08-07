package com.insa.repository;

import com.insa.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartmentId(Long depId);

    boolean existsByEmployeeIdAndTenantId(String employeeId, Long tenantId);

    Optional<Employee> findByEmployeeId(String employeeId);

    @Query("SELECT e FROM Employee e WHERE e.firstName = :firstName " +
            "AND (:middleName IS NULL OR e.middleName = :middleName) " +
            "AND (:lastName IS NULL OR e.lastName = :lastName)")
    Optional<Employee> findByName(
            @Param("firstName") String firstName,
            @Param("middleName") String middleName,
            @Param("lastName") String lastName);
}
