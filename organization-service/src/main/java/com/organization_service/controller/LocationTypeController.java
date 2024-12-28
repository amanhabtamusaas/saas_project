package com.organization_service.controller;

import com.organization_service.config.PermissionEvaluator;
import com.organization_service.dto.requestDto.LocationTypeRequest;
import com.organization_service.dto.responseDto.LocationTypeResponse;
import com.organization_service.service.LocationTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/location-types/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Locations Type")
public class LocationTypeController {

    private final LocationTypeService locationTypeService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add-location-type")
    public ResponseEntity<?> createLocationType(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody LocationTypeRequest locationTypeRequest) {

        permissionEvaluator.addLocationTypePermission();

        LocationTypeResponse location = locationTypeService
                .createLocationType(tenantId, locationTypeRequest);
        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllLocationTypes(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllLocationTypesPermission();

        List<LocationTypeResponse> locationTypes = locationTypeService
                .getAllLocationTypes(tenantId);
        return ResponseEntity.ok(locationTypes);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getLocationTypeById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getLocationTypeByIdPermission();

        LocationTypeResponse location = locationTypeService.getLocationTypeById(id, tenantId);
        return ResponseEntity.ok(location);
    }

    @PutMapping("/update-locationType/{id}")
    public ResponseEntity<?> updateLocationType(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody LocationTypeRequest locationTypeRequest) {

        permissionEvaluator.updateLocationTypePermission();

        LocationTypeResponse location = locationTypeService
                .updateLocationType(id, tenantId, locationTypeRequest);
        return ResponseEntity.ok(location);
    }

    @DeleteMapping("/delete-locationType/{id}")
    public ResponseEntity<?> deleteLocationType(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.deleteLocationTypePermission();

        locationTypeService.deleteLocationType(id, tenantId);
        return ResponseEntity.ok("LocationType deleted successfully!");
    }
}