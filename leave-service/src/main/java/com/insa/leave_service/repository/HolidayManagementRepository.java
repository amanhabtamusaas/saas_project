package com.insa.leave_service.repository;

import com.insa.leave_service.entity.HolidayManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidayManagementRepository extends JpaRepository<HolidayManagement, Long> {

    @Query("SELECT h.date FROM HolidayManagement h")
    List<LocalDate> findAllHolidays();
   // boolean existsByBudgetYearAndTenantId(String budgetYear,Long tenantId);
}
