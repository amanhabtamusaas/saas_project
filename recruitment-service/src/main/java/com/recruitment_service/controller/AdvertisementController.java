package com.recruitment_service.controller;

import com.recruitment_service.config.PermissionEvaluator;
import com.recruitment_service.dto.request.AdvertisementRequest;
import com.recruitment_service.dto.response.AdvertisementResponse;
import com.recruitment_service.service.AdvertisementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/advertisements/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Advertisement")
public class AdvertisementController {

    private final AdvertisementService advertisementService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createAdvertisement(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody AdvertisementRequest request) {

        permissionEvaluator.addAdvertisementPermission();

        AdvertisementResponse response = advertisementService
                .createAdvertisement(tenantId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllAdvertisements(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllAdvertisementsPermission();

        List<AdvertisementResponse> responses = advertisementService
                .getAllAdvertisements(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{advertisement-id}")
    public ResponseEntity<?> getAdvertisementById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("advertisement-id") UUID advertisementId) {

        permissionEvaluator.getAdvertisementByIdPermission();

        AdvertisementResponse response = advertisementService
                .getAdvertisementById(tenantId, advertisementId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/recruitment")
    public ResponseEntity<?> getAdvertisementByVacancyNumber(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("vacancy-number") String vacancyNumber) {

        permissionEvaluator.getAdvertisementByVacancyNumberPermission();

        AdvertisementResponse response = advertisementService
                .getAdvertisementByVacancyNumber(tenantId, vacancyNumber);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{advertisement-id}")
    public ResponseEntity<?> updateAdvertisement(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("advertisement-id") UUID advertisementId,
            @RequestBody AdvertisementRequest request) {

        permissionEvaluator.updateAdvertisementPermission();

        AdvertisementResponse response = advertisementService
                .updateAdvertisement(tenantId, advertisementId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{advertisement-id}/remove-advertisement-media/{media-type-id}")
    public ResponseEntity<?> removeMediaTypeFromAdvertisement(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("advertisement-id") UUID advertisementId,
            @PathVariable("media-type-id") UUID mediaTypeId) {

        permissionEvaluator.removeAdvertisementMediaTypePermission();

        advertisementService.removeMediaTypeFromAdvertisement(tenantId, advertisementId, mediaTypeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Media type removed successfully!");
    }
}