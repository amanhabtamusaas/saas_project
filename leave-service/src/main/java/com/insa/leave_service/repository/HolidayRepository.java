package com.insa.leave_service.repository;

import com.insa.leave_service.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface HolidayRepository extends JpaRepository<Holiday,Long> {
    boolean existsByHolidayName(String holidayName);
    boolean existsByHolidayNameAndTenantId(String holidayName,Long tenantId);


}
