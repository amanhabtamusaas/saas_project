package com.training_service.controller;

import com.training_service.config.PermissionEvaluator;
import com.training_service.dto.request.InternshipPaymentRequest;
import com.training_service.dto.response.InternshipPaymentResponse;
import com.training_service.service.InternshipPaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/internship-payments/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Internship Payment")
public class InternshipPaymentController {

    private final InternshipPaymentService internshipPaymentService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addInternshipPayment(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody InternshipPaymentRequest request) {

        permissionEvaluator.addInternshipPaymentPermission();

        InternshipPaymentResponse response = internshipPaymentService
                .createInternshipPayment(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllInternshipPayments(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllInternshipPaymentsPermission();

        List<InternshipPaymentResponse> responses = internshipPaymentService
                .getAllInternshipPayments(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{payment-id}")
    public ResponseEntity<?> getInternshipPaymentById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("payment-id") UUID paymentId) {

        permissionEvaluator.getInternshipPaymentByIdPermission();

        InternshipPaymentResponse response = internshipPaymentService
                .getInternshipPaymentById(tenantId, paymentId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{payment-id}")
    public ResponseEntity<?> updateInternshipPayment(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("payment-id") UUID paymentId,
            @RequestBody InternshipPaymentRequest request) {

        permissionEvaluator.updateInternshipPaymentPermission();

        InternshipPaymentResponse response = internshipPaymentService
                .updateInternshipPayment(tenantId, paymentId, request);
        return ResponseEntity.ok(response);
    }
}