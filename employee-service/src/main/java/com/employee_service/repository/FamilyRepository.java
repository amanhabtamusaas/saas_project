package com.employee_service.repository;

import com.employee_service.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FamilyRepository extends JpaRepository<Family, UUID> {

    List<Family> findByEmployeeId(UUID empId);
}
