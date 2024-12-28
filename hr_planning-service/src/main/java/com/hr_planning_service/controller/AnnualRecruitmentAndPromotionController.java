package com.hr_planning_service.controller;

import com.hr_planning_service.config.PermissionEvaluator;
import com.hr_planning_service.dto.request.AnnualRecruitmentAndPromotionRequest;
import com.hr_planning_service.dto.response.AnnualRecruitmentAndPromotionResponse;
import com.hr_planning_service.service.AnnualRecruitmentAndPromotionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/{tenantId}/annual-recruitment-promotion")
@RequiredArgsConstructor
@Tag(name = "Annual Recruitment and Promotion")
public class AnnualRecruitmentAndPromotionController {

    private final AnnualRecruitmentAndPromotionService annualRecruitmentAndPromotionService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/create")
    public ResponseEntity<?> createAnnualRecruitmentAndPromotion(
            @PathVariable("tenantId") UUID tenantId,
            @Valid @RequestBody AnnualRecruitmentAndPromotionRequest request) {

        permissionEvaluator.addAnnualRecruitmentAndPromotionPermission();

        AnnualRecruitmentAndPromotionResponse createdModel = annualRecruitmentAndPromotionService
                .createAnnualRecruitmentAndPromotion(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdModel);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAnnualRecruitmentAndPromotions(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllAnnualRecruitmentAndPromotionsPermission();

        List<AnnualRecruitmentAndPromotionResponse> allEntities = annualRecruitmentAndPromotionService
                .getAllAnnualRecruitmentAndPromotions(tenantId);
        return ResponseEntity.ok(allEntities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnnualRecruitmentAndPromotionById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.getAnnualRecruitmentAndPromotionByIdPermission();

        AnnualRecruitmentAndPromotionResponse model = annualRecruitmentAndPromotionService
                .getAnnualRecruitmentAndPromotionById(tenantId, id);
        return ResponseEntity.ok(model);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateAnnualRecruitmentAndPromotion(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id,
            @Valid @RequestBody AnnualRecruitmentAndPromotionRequest request) {

        permissionEvaluator.updateAnnualRecruitmentAndPromotionPermission();

        AnnualRecruitmentAndPromotionResponse updatedModel = annualRecruitmentAndPromotionService
                .updateAnnualRecruitmentAndPromotion(tenantId, id, request);
        return ResponseEntity.ok(updatedModel);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteAnnualRecruitmentAndPromotion(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.deleteAnnualRecruitmentAndPromotionPermission();

        annualRecruitmentAndPromotionService.deleteAnnualRecruitmentAndPromotion(tenantId, id);
        return ResponseEntity.ok("Annual recruitment and promotion entry deleted successfully!");
    }
}