package com.insa.controller;


import com.insa.dto.request.ShortlistCriteriaRequest;
import com.insa.dto.response.ShortlistCriteriaResponse;
import com.insa.service.ShortlistCriteriaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shortlist-criteria/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Shortlist criteria")
public class ShortlistCriteriaController {

    private final ShortlistCriteriaService shortlistCriteriaService;

    @PostMapping("/add")
    public ResponseEntity<?> createShortlistCriteria(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody ShortlistCriteriaRequest request) {

        try {
            ShortlistCriteriaResponse response = shortlistCriteriaService
                    .createShortlistCriteria(tenantId, request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the shortlist criteria: " + e.getMessage());
        }
    }

    @GetMapping("/get-all/{recruitment-id}")
    public ResponseEntity<?> getAllShortlistCriteria(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("recruitment-id") Long recruitmentId) {

        try {
            List<ShortlistCriteriaResponse> responses = shortlistCriteriaService
                    .getAllShortlistCriteria(tenantId, recruitmentId);
            return ResponseEntity.ok(responses);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the shortlist criteria: " + e.getMessage());
        }
    }

    @GetMapping("/get/{shortlist-criteria-id}")
    public ResponseEntity<?> getShortlistCriteriaById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("shortlist-criteria-id") Long shortlistCriteriaId) {

        try {
            ShortlistCriteriaResponse response = shortlistCriteriaService
                    .getShortlistCriteriaById(tenantId, shortlistCriteriaId);
            return ResponseEntity.ok(response);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the shortlist criteria: " + e.getMessage());
        }
    }

    @PutMapping("/update/{shortlist-criteria-id}")
    public ResponseEntity<?> updateShortlistCriteria(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("shortlist-criteria-id") Long shortlistCriteriaId,
            @RequestBody ShortlistCriteriaRequest request ) {

        try {
            ShortlistCriteriaResponse response = shortlistCriteriaService
                    .updateShortlistCriteria(tenantId, shortlistCriteriaId, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the shortlist criteria: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{shortlist-criteria-id}")
    public ResponseEntity<?> deleteShortlistCriteria(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("shortlist-criteria-id") Long shortlistCriteriaId) {

        try {
            shortlistCriteriaService.deleteShortlistCriteria(tenantId, shortlistCriteriaId);
            return ResponseEntity.ok("ShortlistCriteria deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the shortlist criteria: " + e.getMessage());
        }
    }
}
