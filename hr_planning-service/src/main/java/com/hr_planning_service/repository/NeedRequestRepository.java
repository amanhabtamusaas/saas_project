package com.hr_planning_service.repository;

import com.hr_planning_service.model.HrNeedRequest;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface NeedRequestRepository extends JpaRepository<HrNeedRequest, UUID> {

    // Custom query with JPQL
    @Query("SELECT n FROM HrNeedRequest n WHERE n.staffPlanId = :staffPlanId AND n.tenantId = :tenantId")
    List<HrNeedRequest> findByStaffPlanId(@Param("staffPlanId") UUID staffPlanId,
                                                     @Param("tenantId") UUID tenantId);
}
