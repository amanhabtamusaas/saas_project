package com.insa.controller;

import com.insa.dto.requestDto.LocationRequest;
import com.insa.dto.requestDto.LocationTypeRequest;
import com.insa.dto.responseDto.LocationResponse;
import com.insa.dto.responseDto.LocationTypeResponse;
import com.insa.entity.LocationType;
import com.insa.service.LocationService;
import com.insa.service.LocationTypeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Location-types/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Locations Type")
@SecurityRequirement(name = "Keycloak")
public class LocationTypeController {
    private final LocationTypeService locationTypeService;

    @PostMapping("/add-location-type")
    public ResponseEntity<LocationTypeResponse> createLocationType(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody LocationTypeRequest locationTypeRequest) {

        LocationTypeResponse location = locationTypeService
                .createLocationType (tenantId, locationTypeRequest);
        return new ResponseEntity<> (location, HttpStatus.CREATED);
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<LocationTypeResponse>> getAllLocationTypes(
            @PathVariable("tenant-id") Long tenantId) {
        List<LocationTypeResponse> locationTypes = locationTypeService.getAllLocationTypes (tenantId);
        return ResponseEntity.ok (locationTypes);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<LocationTypeResponse> getLocationTypeById(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId) {

        LocationTypeResponse location = locationTypeService.getLocationTypeById (id, tenantId);
        return ResponseEntity.ok (location);
    }

    @PutMapping("/update-locationType/{id}")
    public ResponseEntity<LocationTypeResponse> updateLocationType(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody LocationTypeRequest locationTypeRequest) {

        LocationTypeResponse location = locationTypeService
                .updateLocationType (id, tenantId, locationTypeRequest);
        return ResponseEntity.ok (location);
    }

    @DeleteMapping("/delete-locationType/{id}")
    public ResponseEntity<String> deleteLocationType(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId) {

        locationTypeService.deleteLocationType (id, tenantId);
        return ResponseEntity.ok ("LocationType deleted successfully!");
    }
}
