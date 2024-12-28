package com.employee_service.service;

import com.employee_service.dto.clientDto.TenantDto;
import com.employee_service.dto.request.AddressRequest;
import com.employee_service.dto.response.AddressResponse;
import com.employee_service.exception.ResourceNotFoundException;
import com.employee_service.model.Address;
import com.employee_service.model.Employee;
import com.employee_service.mapper.AddressMapper;
import com.employee_service.repository.AddressRepository;
import com.employee_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public AddressResponse addAddress(UUID tenantId,
                                      UUID employeeId,
                                      AddressRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Address address = addressMapper.mapToEntity(tenant, employee, request);
        address = addressRepository.save(address);
        return addressMapper.mapToDto(address);
    }

    public List<AddressResponse> getAllAddresses(UUID tenantId,
                                                 UUID employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        List<Address> addresses = addressRepository.findByEmployeeId(employee.getId());
        return addresses.stream()
                .filter(add -> add.getTenantId().equals(tenant.getId()))
                .map(addressMapper::mapToDto)
                .toList();
    }

    public List<AddressResponse> getEmployeeAddresses(UUID tenantId,
                                                      String employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeByEmployeeId(tenant.getId(), employeeId);
        List<Address> addresses = addressRepository.findByEmployeeId(employee.getId());
        return addresses.stream()
                .filter(add -> add.getTenantId().equals(tenant.getId()))
                .map(addressMapper::mapToDto)
                .toList();
    }

    public AddressResponse getAddressById(UUID tenantId,
                                          UUID employeeId,
                                          UUID addressId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Address address = getAddress(tenant.getId(), employee, addressId);
        return addressMapper.mapToDto(address);
    }

    @Transactional
    public AddressResponse updateAddress(UUID tenantId,
                                         UUID employeeId,
                                         UUID addressId,
                                         AddressRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Address address = getAddress(tenant.getId(), employee, addressId);
        address = addressMapper.updateAddress(tenant, address, request);
        address = addressRepository.save(address);
        return addressMapper.mapToDto(address);
    }

    @Transactional
    public void deleteAddress(UUID tenantId,
                              UUID employeeId,
                              UUID addressId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Address address = getAddress(tenant.getId(), employee, addressId);
        addressRepository.delete(address);
    }

    private Address getAddress(UUID tenantId, Employee employee, UUID addressId) {

        return addressRepository.findById(addressId)
                .filter(add -> add.getTenantId().equals(tenantId))
                .filter(add -> add.getEmployee().equals(employee))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address not found with id '" + addressId + "'"));
    }
}