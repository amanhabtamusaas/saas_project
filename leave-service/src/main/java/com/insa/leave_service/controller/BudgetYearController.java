package com.insa.leave_service.controller;

import com.insa.leave_service.dto.request.BudgetYearRequest;
import com.insa.leave_service.dto.response.BudgetYearResponse;
import com.insa.leave_service.service.BudgetYearService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budget-years/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Budget Year")
public class BudgetYearController {

    private final BudgetYearService budgetYearService;

    @PostMapping("/add-budget-year")
    public ResponseEntity<BudgetYearResponse> createBudgetYear(@PathVariable("tenantId") Long tenantId,
                                                               @RequestBody @Valid BudgetYearRequest budgetYearRequest) {
        BudgetYearResponse createdBudgetYear = budgetYearService.createBudgetYear(tenantId, budgetYearRequest);
        return ResponseEntity.ok(createdBudgetYear);
    }

    @GetMapping("/get-all-years")
    public ResponseEntity<List<BudgetYearResponse>> getAllBudgetYears(@PathVariable("tenantId") Long tenantId) {
        List<BudgetYearResponse> budgetYears = budgetYearService.getAllBudgetYears(tenantId);
        return ResponseEntity.ok(budgetYears);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BudgetYearResponse> getBudgetYearById(@PathVariable("tenantId") Long tenantId, @PathVariable Long id) {
        BudgetYearResponse budgetYear = budgetYearService.getBudgetYearById(tenantId, id);
        return ResponseEntity.ok(budgetYear);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BudgetYearResponse> updateBudgetYear(@PathVariable("tenantId") Long tenantId, @PathVariable Long id, @RequestBody @Valid BudgetYearRequest budgetYearRequest) {
        BudgetYearResponse updatedBudgetYear = budgetYearService.updateBudgetYear(tenantId, id, budgetYearRequest);
        return ResponseEntity.ok(updatedBudgetYear);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBudgetYear(@PathVariable("tenantId") Long tenantId, @PathVariable Long id) {
        budgetYearService.deleteBudgetYear(tenantId, id);
        return ResponseEntity.ok("Holiday deleted successfully!");
    }
}
