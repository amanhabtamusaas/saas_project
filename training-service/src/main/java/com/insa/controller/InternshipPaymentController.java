package com.insa.controller;

import com.insa.dto.request.InternshipPaymentRequest;
import com.insa.dto.request.InternshipPlacementRequest;
import com.insa.dto.request.InternshipStudentRequest;
import com.insa.dto.response.InternshipPaymentResponse;
import com.insa.dto.response.InternshipStudentResponse;
import com.insa.service.InternshipPaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internship-payments/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Internship Payment")
public class InternshipPaymentController {

    private final InternshipPaymentService internshipPaymentService;

    @PostMapping("/add")
    public ResponseEntity<?> addInternshipPayment(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody InternshipPaymentRequest request) {

        try {
            InternshipPaymentResponse response = internshipPaymentService
                    .createInternshipPayment(tenantId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the internship payment: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllInternshipPayments(
            @PathVariable("tenant-id") Long tenantId) {

        try {
            List<InternshipPaymentResponse> responses = internshipPaymentService
                    .getAllInternshipPayments(tenantId);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the internship payments: " + e.getMessage());
        }
    }

    @GetMapping("/get/{payment-id}")
    public ResponseEntity<?> getInternshipPaymentById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("payment-id") Long paymentId) {

        try {
            InternshipPaymentResponse response = internshipPaymentService
                    .getInternshipPaymentById(tenantId, paymentId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the internship payment: " + e.getMessage());
        }
    }

    @PutMapping("/update/{payment-id}")
    public ResponseEntity<?> updateInternshipPayment(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("payment-id") Long paymentId,
            @RequestBody InternshipPaymentRequest request) {

        try {
            InternshipPaymentResponse response = internshipPaymentService
                    .updateInternshipPayment(tenantId, paymentId, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the internship payment: " + e.getMessage());
        }
    }
}
