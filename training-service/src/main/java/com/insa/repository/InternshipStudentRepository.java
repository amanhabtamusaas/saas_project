package com.insa.repository;

import com.insa.entity.InternshipStudent;
import com.insa.enums.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternshipStudentRepository extends JpaRepository<InternshipStudent, Long> {

    List<InternshipStudent> findByTenantIdAndBudgetYearIdAndSemester(
            Long tenantId, Long budgetYearId, Semester semester);
    boolean existsByTenantIdAndBudgetYearIdAndSemesterAndIdNumber(
            Long tenantId, Long yearId, Semester semester, String idNumber);
}
