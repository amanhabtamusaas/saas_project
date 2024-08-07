package com.insa.controller;

import com.insa.dto.requestDto.AddressRequest;
import com.insa.dto.responseDto.AddressResponse;
import com.insa.service.AddressService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Address")
@SecurityRequirement(name = "Keycloak")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/add-address")
    public ResponseEntity<AddressResponse> createAddress(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody AddressRequest addressRequest) {
        AddressResponse address = addressService.createAddress(tenantId, addressRequest);
        return new ResponseEntity<>(address, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<AddressResponse>> getAllAddresses(
            @PathVariable("tenant-id") Long tenantId) {
        List<AddressResponse> addresses = addressService.getAllAddresses(tenantId);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/departments/{department-id}")
    public ResponseEntity<List<AddressResponse>> getAddressByDepartmentId(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("department-id") Long departmentId) {
        List<AddressResponse> addresses = addressService.getAddressByDepartmentId(tenantId, departmentId);
        return ResponseEntity.ok(addresses);
    }

    @PutMapping("/edit-address/{id}")
    public ResponseEntity<AddressResponse> updateAddress(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable Long id,
            @RequestBody AddressRequest addressRequest) {
        AddressResponse address = addressService.updateAddress(id, tenantId, addressRequest);
        return ResponseEntity.ok(address);
    }

    @DeleteMapping("/remove-address/{id}")
    public ResponseEntity<String> deleteAddress(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable Long id) {
        addressService.deleteAddress(id, tenantId);
        return ResponseEntity.ok("Address deleted successfully!");
    }
}
