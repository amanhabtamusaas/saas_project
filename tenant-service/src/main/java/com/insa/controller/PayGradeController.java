package com.insa.controller;

import com.insa.dto.requestDto.PayGradeRequest;
import com.insa.dto.responseDto.PayGradeResponse;
import com.insa.service.PayGradeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pay-grades/{tenant-id}")
@RequiredArgsConstructor
@Tag (name = "Pay Grade")
@SecurityRequirement(name = "Keycloak")
public class PayGradeController {

    private final PayGradeService payGradeService;

    @PostMapping("/add-pay-grade")
    public ResponseEntity<PayGradeResponse> createPayGrade(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody PayGradeRequest payGradeRequest) {

        PayGradeResponse payGrade = payGradeService.createPayGrade (tenantId, payGradeRequest);
        return new ResponseEntity<> (payGrade, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<PayGradeResponse>> getAllPayGrades(
            @PathVariable("tenant-id") Long tenantId) {

        List<PayGradeResponse> payGrades = payGradeService.getAllPayGrades (tenantId);
        return ResponseEntity.ok (payGrades);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PayGradeResponse> getPayGradeById(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId) {

        PayGradeResponse payGrade = payGradeService.getPayGradeById (id, tenantId);
        return ResponseEntity.ok (payGrade);
    }
    @GetMapping("/jobgrade/{jobGradeId}")
    public List<PayGradeResponse> getPayGradesByJobGradeId(
            @PathVariable Long jobGradeId,
            @PathVariable("tenant-id") Long tenantId) {
        return payGradeService.getPayGradesByJobGradeId(jobGradeId, tenantId);
    }


    @PutMapping("/update-pay-grade/{id}")
    //@PreAuthorize ("hasRole('admin')")
    public ResponseEntity<PayGradeResponse> updatePayGrade(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody PayGradeRequest payGradeRequest) {

        PayGradeResponse payGrade = payGradeService.updatePayGrade (id, tenantId, payGradeRequest);
        return ResponseEntity.ok (payGrade);
    }

    @DeleteMapping("/delete-pay-grade/{id}")
    public ResponseEntity<String> deletePayGrade(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId)
    {
        payGradeService.deletePayGrade (id, tenantId);
        return ResponseEntity.ok ("Pay-grade deleted successfully!");
    }
}
