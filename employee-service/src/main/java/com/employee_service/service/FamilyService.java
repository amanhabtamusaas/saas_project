package com.employee_service.service;

import com.employee_service.dto.clientDto.TenantDto;
import com.employee_service.dto.request.FamilyRequest;
import com.employee_service.dto.response.FamilyResponse;
import com.employee_service.model.Employee;
import com.employee_service.model.Family;
import com.employee_service.exception.ResourceNotFoundException;
import com.employee_service.mapper.FamilyMapper;
import com.employee_service.repository.FamilyRepository;
import com.employee_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final FamilyMapper familyMapper;
    private final ValidationUtil validationUtil;

    public FamilyResponse addFamily(UUID tenantId,
                                    UUID employeeId,
                                    FamilyRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Family family = familyMapper.mapToEntity(tenant, employee, request);
        family = familyRepository.save(family);
        return familyMapper.mapToDto(family);
    }

    public List<FamilyResponse> getAllFamilies(UUID tenantId,
                                               UUID employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        List<Family> families = familyRepository.findByEmployeeId(employee.getId());
        return families.stream()
                .filter(fam -> fam.getTenantId().equals(tenant.getId()))
                .map(familyMapper::mapToDto)
                .toList();
    }

    public List<FamilyResponse> getEmployeeFamilies(UUID tenantId,
                                                    String employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeByEmployeeId(tenant.getId(), employeeId);
        List<Family> families = familyRepository.findByEmployeeId(employee.getId());
        return families.stream()
                .filter(fam -> fam.getTenantId().equals(tenant.getId()))
                .map(familyMapper::mapToDto)
                .toList();
    }

    public FamilyResponse getFamilyById(UUID tenantId,
                                        UUID employeeId,
                                        UUID familyId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Family family = getFamilyById(tenantId, employee, familyId);
        return familyMapper.mapToDto(family);
    }

    public FamilyResponse updateFamily(UUID tenantId,
                                       UUID employeeId,
                                       UUID familyId,
                                       FamilyRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Family family = getFamilyById(tenantId, employee, familyId);
        family = familyMapper.updateFamily(family, request);
        family = familyRepository.save(family);
        return familyMapper.mapToDto(family);
    }

    public void deleteFamily(UUID tenantId,
                             UUID employeeId,
                             UUID familyId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Family family = getFamilyById(tenantId, employee, familyId);
        familyRepository.delete(family);
    }

    private Family getFamilyById(UUID tenantId, Employee employee, UUID familyId) {

        return familyRepository.findById(familyId)
                .filter(fam -> fam.getTenantId().equals(tenantId))
                .filter(fam -> fam.getEmployee().equals(employee))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Family not found with id '" + familyId + "'"));
    }
}