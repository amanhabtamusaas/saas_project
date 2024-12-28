package com.hr_planning_service.client;
import com.hr_planning_service.dto.BudgetYearDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;


@FeignClient(name = "LEAVE-SERVICE", url = "${leave-service.base-url}")
public interface LeaveServiceClient {
    @GetMapping("/api/budget-years/{tenantId}/get/{id}")
    public BudgetYearDto getBudgetYearById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id);
    @GetMapping("/api/budget-years/{tenantId}/get-all")
    public BudgetYearDto getAllBudgetYears(
            @PathVariable("tenantId") UUID tenantId);
}
