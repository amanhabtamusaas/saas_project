package com.insa.service;

import com.insa.dto.apiDto.BudgetYearDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "LEAVE-SERVICE", url = "${leave-service.base-url}")
public interface LeaveServiceClient {

    @GetMapping("/api/budget-years/{tenantId}/get/{id}")
    public BudgetYearDto getBudgetYearById(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable Long id);
}
