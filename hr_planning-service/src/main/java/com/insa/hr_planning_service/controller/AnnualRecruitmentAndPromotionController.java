package com.insa.hr_planning_service.controller;

import com.insa.hr_planning_service.dto.request.AnnualRecruitmentAndPromotionRequest;
import com.insa.hr_planning_service.dto.response.AnnualRecruitmentAndPromotionResponse;
import com.insa.hr_planning_service.service.AnnualRecruitmentAndPromotionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{tenantId}/annual-recruitment-promotion")
@RequiredArgsConstructor
@Tag(name = "Annual Recruitment and Promotion")
public class AnnualRecruitmentAndPromotionController {

    private final AnnualRecruitmentAndPromotionService annualRecruitmentAndPromotionService;

    @PostMapping("/create")
    public ResponseEntity<AnnualRecruitmentAndPromotionResponse> createAnnualRecruitmentAndPromotion(
            @PathVariable("tenantId") Long tenantId,
            @Valid @RequestBody AnnualRecruitmentAndPromotionRequest request
    ) {
        AnnualRecruitmentAndPromotionResponse createdEntity = annualRecruitmentAndPromotionService.createAnnualRecruitmentAndPromotion(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntity);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AnnualRecruitmentAndPromotionResponse>> getAllAnnualRecruitmentAndPromotions(
            @PathVariable("tenantId") Long tenantId
    ) {
        List<AnnualRecruitmentAndPromotionResponse> allEntities = annualRecruitmentAndPromotionService.getAllAnnualRecruitmentAndPromotions(tenantId);
        return ResponseEntity.ok(allEntities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnualRecruitmentAndPromotionResponse> getAnnualRecruitmentAndPromotionById(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable Long id
    ) {
        AnnualRecruitmentAndPromotionResponse entity = annualRecruitmentAndPromotionService.getAnnualRecruitmentAndPromotionById(tenantId, id);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<AnnualRecruitmentAndPromotionResponse> updateAnnualRecruitmentAndPromotion(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable Long id,
            @Valid @RequestBody AnnualRecruitmentAndPromotionRequest request
    ) {
        AnnualRecruitmentAndPromotionResponse updatedEntity = annualRecruitmentAndPromotionService.updateAnnualRecruitmentAndPromotion(tenantId, id, request);
        return ResponseEntity.ok(updatedEntity);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteAnnualRecruitmentAndPromotion(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable Long id
    ) {
        annualRecruitmentAndPromotionService.deleteAnnualRecruitmentAndPromotion(tenantId, id);
        return ResponseEntity.ok("Annual recruitment and promotion entry deleted successfully!");
    }
}
