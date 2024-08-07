package com.insa.hr_planning_service.repository;

import com.insa.hr_planning_service.entity.HrNeedRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NeedRequestRepository extends JpaRepository<HrNeedRequest,Long> {
}
