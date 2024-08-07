package com.insa.hr_planning_service.controller;

import com.insa.hr_planning_service.dto.request.HrAnalysisRequestDto;
import com.insa.hr_planning_service.dto.response.HrAnalysisResponseDto;
import com.insa.hr_planning_service.service.HrAnalysisService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/{tenantId}/hr-analyses")
@RequiredArgsConstructor
@Tag(name = "HR Analysis")
public class HrAnalysisController {

    private final HrAnalysisService hrAnalysisService;

    @PostMapping("/create")
    public ResponseEntity<HrAnalysisResponseDto> createHrAnalysis(
            @PathVariable("tenantId") Long tenantId,
            @Valid @RequestBody HrAnalysisRequestDto hrAnalysisRequestDto
    ) {
        HrAnalysisResponseDto createdHrAnalysis = hrAnalysisService.createHrAnalysis(tenantId, hrAnalysisRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHrAnalysis);
    }

    @GetMapping("/all")
    public ResponseEntity<List<HrAnalysisResponseDto>> getAllHrAnalyses(
            @PathVariable("tenantId") Long tenantId
    ) {
        List<HrAnalysisResponseDto> allHrAnalyses = hrAnalysisService.getAllHrAnalyses(tenantId);
        return ResponseEntity.ok(allHrAnalyses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HrAnalysisResponseDto> getHrAnalysisById(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable Long id
    ) {
        HrAnalysisResponseDto hrAnalysis = hrAnalysisService.getHrAnalysisById(tenantId, id);
        return ResponseEntity.ok(hrAnalysis);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<HrAnalysisResponseDto> updateHrAnalysis(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable Long id,
            @Valid @RequestBody HrAnalysisRequestDto hrAnalysisRequestDto
    ) {
        HrAnalysisResponseDto updatedHrAnalysis = hrAnalysisService.updateHrAnalysis(tenantId, id, hrAnalysisRequestDto);
        return ResponseEntity.ok(updatedHrAnalysis);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteHrAnalysis(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable Long id
    ) {
        hrAnalysisService.deleteHrAnalysis(tenantId, id);
        return ResponseEntity.ok("Hr demand and analysis deleted successfully! ");
    }
}
