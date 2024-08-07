package com.insa.controller;

import com.insa.dto.requestDto.DepartmentRequest;
import com.insa.dto.requestDto.LocationRequest;
import com.insa.dto.responseDto.DepartmentResponse;
import com.insa.dto.responseDto.LocationResponse;
import com.insa.entity.Department;
import com.insa.entity.Location;
import com.insa.service.DepartmentService;
import com.insa.service.LocationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Locations")
@SecurityRequirement(name = "Keycloak")
public class LocationController {
    private final LocationService locationService;

    @PostMapping("/add-location")
    public ResponseEntity<LocationResponse> createLocation(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody LocationRequest locationRequest) {

        LocationResponse location = locationService
                .createLocation (tenantId, locationRequest);
        return new ResponseEntity<> (location, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<LocationResponse>> getAllLocations(
            @PathVariable("tenant-id") Long tenantId) {
        List<LocationResponse> locations = locationService.getAllLocations (tenantId);
        return ResponseEntity.ok (locations);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<LocationResponse> getLocationById(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId) {

        LocationResponse location = locationService.getLocationById (id, tenantId);
        return ResponseEntity.ok (location);
    }


    @PutMapping("/update-location/{id}")
    public ResponseEntity<LocationResponse> updateLocation(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody LocationRequest locationRequest) {

        LocationResponse location = locationService
                .updateLocation (id, tenantId, locationRequest);
        return ResponseEntity.ok (location);
    }

    @DeleteMapping("/delete-location/{id}")
    public ResponseEntity<String> deleteLocation(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId) {

        locationService.deleteLocation (id, tenantId);
        return ResponseEntity.ok ("Location deleted successfully!");
    }
    @PostMapping("/{parentId}/sub-locations")
    public ResponseEntity<LocationResponse> addSubLocation(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable Long parentId,
            @RequestBody LocationRequest subLocationRequest) {
        LocationResponse locationResponse = locationService.addSubLocation(parentId, tenantId, subLocationRequest);
        return ResponseEntity.ok(locationResponse);
    }

    @DeleteMapping("/{parentId}/sub-locations/{subId}")
    public ResponseEntity<String> removeSubLocation(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable Long parentId,
            @PathVariable Long subId) {
        locationService.removeSubLocation(parentId, tenantId, subId);
        return ResponseEntity.ok("Sub-Location deleted successfully!");
    }


    @GetMapping("/parent-location")
    public List<LocationResponse> getAllParentLocations(@PathVariable("tenant-id") Long tenantId) {
        return locationService.getAllParentLocations(tenantId);
    }


    @GetMapping("/{locationId}/children")
    public List<Location> getAllChildLocations(@PathVariable("tenant-id") Long tenantId,@PathVariable Long locationId) {
        return locationService.getAllChildLocations(locationId,tenantId);
    }
}
