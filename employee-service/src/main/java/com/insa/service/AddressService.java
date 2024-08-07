package com.insa.service;

import com.insa.dto.apiDto.LocationDto;
import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.AddressRequest;
import com.insa.dto.response.AddressResponse;
import com.insa.entity.Address;
import com.insa.entity.Employee;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.AddressMapper;
import com.insa.repository.AddressRepository;
import com.insa.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final EmployeeRepository employeeRepository;
    private final AddressMapper addressMapper;
    private final TenantServiceClient tenantServiceClient;

    public AddressResponse addAddress(Long tenantId,
                                      Long employeeId,
                                      AddressRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        LocationDto location = tenantServiceClient
                .getLocationById(request.getLocationId(), tenant.getId());
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Address address = addressMapper.mapToEntity(request);
        address.setTenantId (tenant.getId ());
        address.setLocationId(location.getId());
        address.setEmployee(employee);
        address = addressRepository.save(address);
        return addressMapper.mapToDto(address);
    }

    public List<AddressResponse> getAllAddresses(Long tenantId,
                                                 Long employeeId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        List<Address> addresses = addressRepository.findByEmployeeId(employee.getId());
        return addresses.stream()
                .filter (add -> add.getTenantId ().equals (tenant.getId ()))
                .map(addressMapper::mapToDto)
                .toList();
    }

    public List<AddressResponse> getEmployeeAddresses(Long tenantId,
                                                      String employeeId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        List<Address> addresses = addressRepository.findByEmployeeId(employee.getId());
        return addresses.stream()
                .filter (add -> add.getTenantId ().equals (tenant.getId ()))
                .map(addressMapper::mapToDto)
                .toList();
    }

    public AddressResponse getAddressById(Long tenantId,
                                          Long employeeId,
                                          Long addressId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Address address = addressRepository.findById (addressId)
                .filter (add -> add.getTenantId ().equals (tenant.getId ()))
                .filter (add -> add.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address not found with id '" + addressId + "' in the specified employee"));
        return addressMapper.mapToDto (address);
    }

    public AddressResponse updateAddress(Long tenantId,
                                         Long employeeId,
                                         Long addressId,
                                         AddressRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Address address = addressRepository.findById(addressId)
                .filter (add -> add.getTenantId ().equals (tenant.getId ()))
                .filter (add -> add.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address not found with id '" + addressId + "' in the specified employee"));
        address = addressMapper.updateAddress(address, request);
        address = addressRepository.save(address);
        return addressMapper.mapToDto(address);
    }

    public void deleteAddress(Long tenantId,
                              Long employeeId,
                              Long addressId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Address address = addressRepository.findById(addressId)
                .filter (add -> add.getTenantId ().equals (tenant.getId ()))
                .filter (add -> add.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address not found with id '" + addressId + "' in the specified employee"));
        addressRepository.delete(address);
    }
}
