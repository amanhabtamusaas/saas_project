package com.employee_service.controller;

import com.employee_service.config.PermissionEvaluator;
import com.employee_service.dto.request.AddressRequest;
import com.employee_service.dto.response.AddressResponse;
import com.employee_service.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/addresses/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Address")
public class AddressController {

    private final AddressService addressService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/{employee-id}/add")
    public ResponseEntity<?> addAddress(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @RequestBody AddressRequest addressRequest) {

        permissionEvaluator.addAddressPermission();

        AddressResponse address = addressService
                .addAddress(tenantId, employeeId, addressRequest);
        return new ResponseEntity<>(address, HttpStatus.CREATED);
    }

    @GetMapping("/{employee-id}/get-all")
    public ResponseEntity<?> getAllAddresses(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId) {

        permissionEvaluator.getAllAddressesPermission();

        List<AddressResponse> addresses = addressService
                .getAllAddresses(tenantId, employeeId);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/get/employee-addresses")
    public ResponseEntity<?> getEmployeeAddresses(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("employee-id") String employeeId) {

        permissionEvaluator.getAddressesByEmployeeIdPermission();

        List<AddressResponse> addresses = addressService
                .getEmployeeAddresses(tenantId, employeeId);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{employee-id}/get/{address-id}")
    public ResponseEntity<?> getAddressById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("address-id") UUID addressId) {

        permissionEvaluator.getAddressByIdPermission();

        AddressResponse address = addressService
                .getAddressById(tenantId, employeeId, addressId);
        return ResponseEntity.ok(address);
    }

    @PutMapping("/{employee-id}/update/{address-id}")
    public ResponseEntity<?> updateAddress(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("address-id") UUID addressId,
            @RequestBody AddressRequest addressRequest) {

        permissionEvaluator.updateAddressPermission();

        AddressResponse address = addressService
                .updateAddress(tenantId, employeeId, addressId, addressRequest);
        return ResponseEntity.ok(address);
    }

    @DeleteMapping("/{employee-id}/delete/{address-id}")
    public ResponseEntity<?> deleteAddress(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("address-id") UUID addressId) {

        permissionEvaluator.deleteAddressPermission();

        addressService.deleteAddress(tenantId, employeeId, addressId);
        return ResponseEntity.ok("Address Deleted Successfully");
    }
}