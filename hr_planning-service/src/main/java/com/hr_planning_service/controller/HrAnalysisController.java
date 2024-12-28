package com.hr_planning_service.controller;

import com.hr_planning_service.config.PermissionEvaluator;
import com.hr_planning_service.dto.request.HrAnalysisRequestDto;
import com.hr_planning_service.dto.response.HrAnalysisResponseDto;
import com.hr_planning_service.service.HrAnalysisService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hr-analyses/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "HR Analysis")
public class HrAnalysisController {

    private final HrAnalysisService hrAnalysisService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/create")
    public ResponseEntity<?> createHrAnalysis(
            @PathVariable("tenantId") UUID tenantId,
            @Valid @RequestBody HrAnalysisRequestDto hrAnalysisRequestDto) {

        permissionEvaluator.addHrAnalysisPermission();

        HrAnalysisResponseDto createdHrAnalysis = hrAnalysisService
                .createHrAnalysis(tenantId, hrAnalysisRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHrAnalysis);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllHrAnalyses(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllHrAnalysesPermission();

        List<HrAnalysisResponseDto> allHrAnalyses = hrAnalysisService
                .getAllHrAnalyses(tenantId);
        return ResponseEntity.ok(allHrAnalyses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHrAnalysisById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.getHrAnalysisByIdPermission();

        HrAnalysisResponseDto hrAnalysis = hrAnalysisService
                .getHrAnalysisById(tenantId, id);
        return ResponseEntity.ok(hrAnalysis);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateHrAnalysis(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id,
            @Valid @RequestBody HrAnalysisRequestDto hrAnalysisRequestDto) {

        permissionEvaluator.updateHrAnalysisPermission();

        HrAnalysisResponseDto updatedHrAnalysis = hrAnalysisService
                .updateHrAnalysis(tenantId, id, hrAnalysisRequestDto);
        return ResponseEntity.ok(updatedHrAnalysis);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteHrAnalysis(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.deleteHrAnalysisPermission();

        hrAnalysisService.deleteHrAnalysis(tenantId, id);
        return ResponseEntity.ok("HR demand and analysis deleted successfully!");
    }
}