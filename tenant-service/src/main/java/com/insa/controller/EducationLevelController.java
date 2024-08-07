package com.insa.controller;

import com.insa.dto.requestDto.EducationLevelRequest;
import com.insa.dto.responseDto.EducationLevelResponse;
import com.insa.service.EducationLevelService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/education-levels/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Education Level")
@SecurityRequirement(name = "Keycloak")
public class EducationLevelController {

    private final EducationLevelService educationLevelService;

    @PostMapping("/add")
    public ResponseEntity<EducationLevelResponse> createEducationLevel(
            @PathVariable("tenantId") Long tenantId,
            @RequestBody EducationLevelRequest educationLevelRequest) {

        EducationLevelResponse educationLevel = educationLevelService.createEducationLevel(tenantId, educationLevelRequest);
        return new ResponseEntity<>(educationLevel, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<EducationLevelResponse>> getAllEducationLevels(
            @PathVariable("tenantId") Long tenantId) {
        List<EducationLevelResponse> educationLevels = educationLevelService.getAllEducationLevels(tenantId);
        return ResponseEntity.ok(educationLevels);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EducationLevelResponse> getEducationLevelById(
            @PathVariable Long id,
            @PathVariable("tenantId") Long tenantId) {

        EducationLevelResponse educationLevel = educationLevelService.getEducationLevelById(id, tenantId);
        return ResponseEntity.ok(educationLevel);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EducationLevelResponse> updateEducationLevel(
            @PathVariable Long id,
            @PathVariable("tenantId") Long tenantId,
            @RequestBody EducationLevelRequest educationLevelRequest) {

        EducationLevelResponse educationLevel = educationLevelService.updateEducationLevel(id, tenantId, educationLevelRequest);
        return ResponseEntity.ok(educationLevel);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEducationLevel(
            @PathVariable Long id,
            @PathVariable("tenantId") Long tenantId) {

        educationLevelService.deleteEducationLevel(id, tenantId);
        return ResponseEntity.ok("Education Level deleted successfully!");
    }
}
