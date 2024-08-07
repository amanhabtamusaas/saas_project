package com.insa.service;

import com.insa.dto.requestDto.AddressRequest;
import com.insa.dto.responseDto.AddressResponse;
import com.insa.entity.Address;
import com.insa.entity.Department;
import com.insa.entity.Tenant;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.AddressMapper;
import com.insa.repository.AddressRepository;
import com.insa.repository.DepartmentRepository;
import com.insa.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final TenantRepository tenantRepository;
    private final DepartmentRepository departmentRepository;

    public AddressResponse createAddress(Long tenantId, AddressRequest addressRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId));
        // Check if department with the same name already exists
        if (addressRepository.existsByDepartmentIdAndTenantId(addressRequest.getDepartmentId(),tenant.getId())) {
            throw new ResourceExistsException("Department with Name " + addressRequest.getDepartmentId() + " already exists");
        }

        Address address = addressMapper.mapToEntity(addressRequest);
        address.setTenant(tenant);
        address = addressRepository.save(address);
        return addressMapper.mapToDto(address);
    }

    public List<AddressResponse> getAllAddresses(Long tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId));

        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .filter(ad -> ad.getTenant().getId().equals(tenant.getId()))
                .map(addressMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AddressResponse> getAddressByDepartmentId(Long tenantId, Long departmentId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId));

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id: " + departmentId));

        List<Address> addresses = addressRepository.findByDepartmentId(departmentId);
        return addresses.stream()
                .filter(address -> address.getTenant().getId().equals(tenant.getId()))
                .map(addressMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public AddressResponse updateAddress(Long id, Long tenantId, AddressRequest addressRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId));

        Address address = addressRepository.findById(id)
                .filter(ad -> ad.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address not found with id: " + id + " for the specified tenant"));

        address = addressMapper.updateAddress(address, addressRequest);
        address = addressRepository.save(address);
        return addressMapper.mapToDto(address);
    }

    public void deleteAddress(Long id, Long tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId));

        Address address = addressRepository.findById(id)
                .filter(ad -> ad.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address not found with id: " + id + " for the specified tenant"));

        addressRepository.delete(address);
    }
}