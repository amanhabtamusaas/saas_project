package com.insa.controller;

import com.insa.dto.request.MediaTypeRequest;
import com.insa.dto.response.MediaTypeResponse;
import com.insa.service.MediaTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/media-types/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Media Type")
public class MediaTypeController {

    private final MediaTypeService mediaTypeService;

    @PostMapping("/add")
    public ResponseEntity<?> addMediaType(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody MediaTypeRequest request) {

        try {
            MediaTypeResponse response = mediaTypeService
                    .addMediaType(tenantId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the media type: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllMediaTypes(
            @PathVariable("tenant-id") Long tenantId) {

        try {
            List<MediaTypeResponse> responses = mediaTypeService
                    .getAllMediaTypes(tenantId);
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the media types: " + e.getMessage());
        }
    }

    @GetMapping("/get/{media-type-id}")
    public ResponseEntity<?> getMediaType(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("media-type-id") Long mediaTypeId) {

        try {
            MediaTypeResponse response = mediaTypeService
                    .getMediaType(tenantId, mediaTypeId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the media type: " + e.getMessage());
        }
    }

    @GetMapping("/get/advertisement-media/{advertisement-id}")
    public ResponseEntity<?> getMediaTypeByAdvertisementId(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("advertisement-id") Long advertisementId) {

        try {
            List<MediaTypeResponse> responses = mediaTypeService
                    .getAllMediaTypesByAdvertisementId(tenantId, advertisementId);
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the media types: " + e.getMessage());
        }
    }

    @PutMapping("/update/{media-type-id}")
    public ResponseEntity<?> updateMediaType(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("media-type-id") Long mediaTypeId,
            @RequestBody MediaTypeRequest request) {

        try {
            MediaTypeResponse response = mediaTypeService
                    .updateMediaType(tenantId, mediaTypeId, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the media type: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{media-type-id}")
    public ResponseEntity<?> deleteMediaType(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("media-type-id") Long mediaTypeId) {

        try {
            mediaTypeService.deleteMediaType(tenantId, mediaTypeId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Media type deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the media type: " + e.getMessage());
        }
    }
}

