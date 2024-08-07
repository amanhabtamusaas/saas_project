package com.insa.service;

import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.ReferenceRequest;
import com.insa.dto.response.ReferenceResponse;
import com.insa.entity.Employee;
import com.insa.entity.Reference;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.ReferenceMapper;
import com.insa.repository.EmployeeRepository;
import com.insa.repository.ReferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReferenceService {

    private final ReferenceRepository referenceRepository;
    private final ReferenceMapper referenceMapper;
    private final EmployeeRepository employeeRepository;
    private final TenantServiceClient tenantServiceClient;

    public ReferenceResponse addReference(Long tenantId,
                                          Long employeeId,
                                          ReferenceRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById (employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Reference reference = referenceMapper.mapToEntity (request);
        reference.setTenantId (tenant.getId ());
        reference.setEmployee (employee);
        reference = referenceRepository.save (reference);
        return referenceMapper.mapToDto (reference);
    }

    public List<ReferenceResponse> getAllReferences(Long tenantId,
                                                    Long employeeId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        List<Reference> references = referenceRepository.findByEmployeeId(employee.getId());
        return references.stream ()
                .filter (ref -> ref.getTenantId ().equals (tenant.getId ()))
                .map (referenceMapper::mapToDto)
                .toList();
    }

    public List<ReferenceResponse> getEmployeeReferences(Long tenantId,
                                                         String employeeId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        List<Reference> references = referenceRepository.findByEmployeeId(employee.getId());
        return references.stream ()
                .filter (ref -> ref.getTenantId ().equals (tenant.getId ()))
                .map (referenceMapper::mapToDto)
                .toList();
    }

    public ReferenceResponse getReferenceById(Long tenantId,
                                              Long employeeId,
                                              Long referenceId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Reference reference = referenceRepository.findById (referenceId)
                .filter (ref -> ref.getTenantId ().equals (tenant.getId ()))
                .filter (ref -> ref.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Reference not found with id '" + referenceId + "' in the specified employee"));
        return referenceMapper.mapToDto (reference);
    }

    public ReferenceResponse updateReference(Long tenantId,
                                             Long employeeId,
                                             Long referenceId,
                                             ReferenceRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Reference reference = referenceRepository.findById (referenceId)
                .filter (ref -> ref.getTenantId ().equals (tenant.getId ()))
                .filter (ref -> ref.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Reference not found with id '" + referenceId + "' in the specified employee"));
        reference = referenceMapper.updateReference (reference, request);
        reference = referenceRepository.save (reference);
        return referenceMapper.mapToDto (reference);
    }

    public void deleteReference(Long tenantId,
                                Long employeeId,
                                Long referenceId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Reference reference = referenceRepository.findById (referenceId)
                .filter (ref -> ref.getTenantId ().equals (tenant.getId ()))
                .filter (ref -> ref.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Reference not found with id '" + referenceId + "' in the specified employee"));
        referenceRepository.delete (reference);
    }
}
