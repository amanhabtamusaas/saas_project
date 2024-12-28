package com.leave_service.repository;

import com.leave_service.model.HolidayManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface HolidayManagementRepository extends JpaRepository<HolidayManagement, UUID> {

    @Query("SELECT h.date FROM HolidayManagement h")
    List<LocalDate> findAllHolidays();
   // boolean existsByBudgetYearAndTenantId(String budgetYear,UUID tenantId);
}
