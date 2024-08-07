package com.insa.service;

import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.FamilyRequest;
import com.insa.dto.response.FamilyResponse;
import com.insa.entity.Employee;
import com.insa.entity.Family;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.FamilyMapper;
import com.insa.repository.EmployeeRepository;
import com.insa.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final FamilyMapper familyMapper;
    private final EmployeeRepository employeeRepository;
    private final TenantServiceClient tenantServiceClient;

    public FamilyResponse addFamily(Long tenantId,
                                    Long employeeId,
                                    FamilyRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById (employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Family family = familyMapper.mapToEntity (request);
        family.setTenantId (tenant.getId ());
        family.setEmployee (employee);
        family = familyRepository.save (family);
        return familyMapper.mapToDto (family);
    }

    public List<FamilyResponse> getAllFamilies(Long tenantId,
                                               Long employeeId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        List<Family> families = familyRepository.findByEmployeeId(employee.getId());
        return families.stream ()
                .filter (fam -> fam.getTenantId ().equals (tenant.getId ()))
                .map (familyMapper::mapToDto)
                .toList();
    }

    public List<FamilyResponse> getEmployeeFamilies(Long tenantId,
                                                    String employeeId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        List<Family> families = familyRepository.findByEmployeeId(employee.getId());
        return families.stream ()
                .filter (fam -> fam.getTenantId ().equals (tenant.getId ()))
                .map (familyMapper::mapToDto)
                .toList();
    }

    public FamilyResponse getFamilyById(Long tenantId,
                                        Long employeeId,
                                        Long familyId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Family family = familyRepository.findById (familyId)
                .filter (fam -> fam.getTenantId ().equals (tenant.getId ()))
                .filter (fam -> fam.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Family not found with id '" + familyId + "' in the specified employee"));
        return familyMapper.mapToDto (family);
    }

    public FamilyResponse updateFamily(Long tenantId,
                                       Long employeeId,
                                       Long familyId,
                                       FamilyRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Family family = familyRepository.findById (familyId)
                .filter (fam -> fam.getTenantId ().equals (tenant.getId ()))
                .filter (fam -> fam.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Family not found with id '" + familyId + "' in the specified employee"));
        family = familyMapper.updateFamily (family, request);
        family = familyRepository.save (family);
        return familyMapper.mapToDto (family);
    }

    public void deleteFamily(Long tenantId,
                             Long employeeId,
                             Long familyId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Family family = familyRepository.findById (familyId)
                .filter (fam -> fam.getTenantId ().equals (tenant.getId ()))
                .filter (fam -> fam.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Family not found with id '" + familyId + "' in the specified employee"));
        familyRepository.delete (family);
    }
}
