package com.leave_service.controller;

import com.leave_service.config.PermissionEvaluator;
import com.leave_service.dto.request.BudgetYearRequest;
import com.leave_service.dto.response.BudgetYearResponse;
import com.leave_service.service.BudgetYearService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/budget-years/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Budget Year")
public class BudgetYearController {

    private final BudgetYearService budgetYearService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add-budget-year")
    public ResponseEntity<?> createBudgetYear(
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody @Valid BudgetYearRequest budgetYearRequest) {

        permissionEvaluator.addBudgetYearPermission();

        BudgetYearResponse createdBudgetYear = budgetYearService
                .createBudgetYear(tenantId, budgetYearRequest);
        return ResponseEntity.ok(createdBudgetYear);
    }

    @GetMapping("/get-all-years")
    public ResponseEntity<?> getAllBudgetYears(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllBudgetYearsPermission();

        List<BudgetYearResponse> budgetYears = budgetYearService.getAllBudgetYears(tenantId);
        return ResponseEntity.ok(budgetYears);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBudgetYearById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.getBudgetYearByIdPermission();

        BudgetYearResponse budgetYear = budgetYearService.getBudgetYearById(tenantId, id);
        return ResponseEntity.ok(budgetYear);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBudgetYear(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id,
            @RequestBody @Valid BudgetYearRequest budgetYearRequest) {

        permissionEvaluator.updateBudgetYearPermission();

        BudgetYearResponse updatedBudgetYear = budgetYearService
                .updateBudgetYear(tenantId, id, budgetYearRequest);
        return ResponseEntity.ok(updatedBudgetYear);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBudgetYear(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.deleteBudgetYearPermission();

        budgetYearService.deleteBudgetYear(tenantId, id);
        return ResponseEntity.ok("Budget year deleted successfully!");
    }
}