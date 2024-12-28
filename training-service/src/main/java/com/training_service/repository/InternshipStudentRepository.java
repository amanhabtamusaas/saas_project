package com.training_service.repository;

import com.training_service.model.InternshipStudent;
import com.training_service.enums.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InternshipStudentRepository extends JpaRepository<InternshipStudent, UUID> {

    List<InternshipStudent> findByTenantIdAndBudgetYearIdAndSemester(
            UUID tenantId, UUID budgetYearId, Semester semester);
    boolean existsByTenantIdAndBudgetYearIdAndSemesterAndIdNumber(
            UUID tenantId, UUID yearId, Semester semester, String idNumber);
}
