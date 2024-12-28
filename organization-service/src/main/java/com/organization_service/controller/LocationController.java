package com.organization_service.controller;

import com.organization_service.config.PermissionEvaluator;
import com.organization_service.dto.requestDto.LocationRequest;
import com.organization_service.dto.responseDto.LocationResponse;
import com.organization_service.model.Location;
import com.organization_service.service.LocationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/locations/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Locations")
public class LocationController {

    private final LocationService locationService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add-location")
    public ResponseEntity<?> createLocation(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody LocationRequest locationRequest) {

        permissionEvaluator.addLocationPermission();

        LocationResponse location = locationService
                .createLocation(tenantId, locationRequest);
        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllLocations(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllLocationsPermission();

        List<LocationResponse> locations = locationService.getAllLocations(tenantId);
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getLocationById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getLocationByIdPermission();

        LocationResponse location = locationService.getLocationById(id, tenantId);
        return ResponseEntity.ok(location);
    }

    @PutMapping("/update-location/{id}")
    public ResponseEntity<?> updateLocation(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody LocationRequest locationRequest) {

        permissionEvaluator.updateLocationPermission();

        LocationResponse location = locationService
                .updateLocation(id, tenantId, locationRequest);
        return ResponseEntity.ok(location);
    }

    @DeleteMapping("/delete-location/{id}")
    public ResponseEntity<String> deleteLocation(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.deleteLocationPermission();

        locationService.deleteLocation(id, tenantId);
        return ResponseEntity.ok("Location deleted successfully!");
    }

    @PostMapping("/{parentId}/sub-locations")
    public ResponseEntity<?> addSubLocation(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable UUID parentId,
            @RequestBody LocationRequest subLocationRequest) {

        permissionEvaluator.addSubLocationPermission();

        LocationResponse locationResponse = locationService
                .addSubLocation(parentId, tenantId, subLocationRequest);
        return ResponseEntity.ok(locationResponse);
    }

    @DeleteMapping("/{parentId}/sub-locations/{subId}")
    public ResponseEntity<String> removeSubLocation(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable UUID parentId,
            @PathVariable UUID subId) {

        permissionEvaluator.deleteSubLocationPermission();

        locationService.removeSubLocation(parentId, tenantId, subId);
        return ResponseEntity.ok("Sub-Location deleted successfully!");
    }

    @GetMapping("/parent-location")
    public ResponseEntity<?> getAllParentLocations(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllParentLocationsPermission();

        List<LocationResponse> responses = locationService.getAllParentLocations(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{locationId}/children")
    public ResponseEntity<?> getAllChildLocations(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable UUID locationId) {

        permissionEvaluator.getAllSubLocationsPermission();

        List<Location> locations = locationService.getAllChildLocations(tenantId, locationId);
        return ResponseEntity.ok(locations);
    }
}