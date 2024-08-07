package com.insa.hr_planning_service.service;
import com.insa.hr_planning_service.dto.BudgetYearDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "LEAVE-SERVICE", url = "${leave-service.base-url}")
public interface LeaveServiceClient {
    @GetMapping("/api/{tenantId}/budget-years/get/{id}")
    public BudgetYearDto getBudgetYearById(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable Long id);
    @GetMapping("/api/{tenantId}/budget-years/get-all-years")
    public BudgetYearDto getAllBudgetYears(
            @PathVariable("tenantId") Long tenantId);
}
