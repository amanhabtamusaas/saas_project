package com.insa.controller;

import com.insa.dto.request.AdvertisementRequest;
import com.insa.dto.response.AdvertisementResponse;
import com.insa.service.AdvertisementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advertisements/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Advertisement")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping("/add")
    public ResponseEntity<?> createAdvertisement(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody AdvertisementRequest request) {

        try {
            AdvertisementResponse response = advertisementService
                    .createAdvertisement(tenantId, request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the advertisement: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllAdvertisements(
            @PathVariable("tenant-id") Long tenantId) {

        try {
            List<AdvertisementResponse> responses = advertisementService
                    .getAllAdvertisements(tenantId);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the advertisements: " + e.getMessage());
        }
    }

    @GetMapping("/get/{advertisement-id}")
    public ResponseEntity<?> getAdvertisementById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("advertisement-id") Long advertisementId) {

        try {
            AdvertisementResponse response = advertisementService
                    .getAdvertisementById(tenantId, advertisementId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the advertisement: " + e.getMessage());
        }
    }

    @GetMapping("/get/recruitment")
    public ResponseEntity<?> getAdvertisementByRecruitment(
            @PathVariable("tenant-id") Long tenantId,
            @RequestParam("vacancy-number") String vacancyNumber) {

        try {
            AdvertisementResponse response = advertisementService
                    .getAdvertisementByRecruitment(tenantId, vacancyNumber);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the advertisement: " + e.getMessage());
        }
    }

    @PutMapping("/update/{advertisement-id}")
    public ResponseEntity<?> getAdvertisementById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("advertisement-id") Long advertisementId,
            @RequestBody AdvertisementRequest request) {

        try {
            AdvertisementResponse response = advertisementService
                    .updateAdvertisement(tenantId, advertisementId, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the advertisement: " + e.getMessage());
        }
    }

    @DeleteMapping("/{advertisement-id}/remove-advertisement-media/{media-type-id}")
    public ResponseEntity<?> removeMediaTypeFromAdvertisement(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("advertisement-id") Long advertisementId,
            @PathVariable("media-type-id") Long mediaTypeId) {

        try {
            advertisementService.removeMediaTypeFromAdvertisement(tenantId, advertisementId, mediaTypeId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Media type removed successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while removing the media type: " + e.getMessage());
        }
    }
}
