package com.organization_service.controller;

import com.organization_service.config.PermissionEvaluator;
import com.organization_service.dto.requestDto.PayGradeRequest;
import com.organization_service.dto.responseDto.PayGradeResponse;
import com.organization_service.service.PayGradeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pay-grades/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Pay Grade")
public class PayGradeController {

    private final PayGradeService payGradeService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add-pay-grade")
    public ResponseEntity<?> createPayGrade(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody PayGradeRequest payGradeRequest) {

        permissionEvaluator.addPayGradePermission();

        PayGradeResponse payGrade = payGradeService
                .createPayGrade(tenantId, payGradeRequest);
        return new ResponseEntity<>(payGrade, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllPayGrades(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllPayGradesPermission();

        List<PayGradeResponse> payGrades = payGradeService.getAllPayGrades(tenantId);
        return ResponseEntity.ok(payGrades);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getPayGradeById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getPayGradeByIdPermission();

        PayGradeResponse payGrade = payGradeService.getPayGradeById(id, tenantId);
        return ResponseEntity.ok(payGrade);
    }

    @GetMapping("/jobgrade/{jobGradeId}")
    public ResponseEntity<?> getPayGradesByJobGradeId(
            @PathVariable UUID jobGradeId,
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getPayGradesByJobGradeIdPermission();

        List<PayGradeResponse> response = payGradeService
                .getPayGradesByJobGradeId(jobGradeId, tenantId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-pay-grade/{id}")
    public ResponseEntity<?> updatePayGrade(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody PayGradeRequest payGradeRequest) {

        permissionEvaluator.updatePayGradePermission();

        PayGradeResponse payGrade = payGradeService
                .updatePayGrade(id, tenantId, payGradeRequest);
        return ResponseEntity.ok(payGrade);
    }

    @DeleteMapping("/delete-pay-grade/{id}")
    public ResponseEntity<?> deletePayGrade(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.deletePayGradePermission();

        payGradeService.deletePayGrade(id, tenantId);
        return ResponseEntity.ok("Pay-grade deleted successfully!");
    }
}