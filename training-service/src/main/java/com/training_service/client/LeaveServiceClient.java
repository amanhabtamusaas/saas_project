package com.training_service.client;

import com.training_service.dto.clientDto.BudgetYearDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient("LEAVE-SERVICE")
public interface LeaveServiceClient {

    @GetMapping("/api/budget-years/{tenantId}/get/{id}")
    public BudgetYearDto getBudgetYearById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id);
}
