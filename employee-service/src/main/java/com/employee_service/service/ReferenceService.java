package com.employee_service.service;

import com.employee_service.dto.clientDto.TenantDto;
import com.employee_service.dto.request.ReferenceRequest;
import com.employee_service.dto.response.ReferenceResponse;
import com.employee_service.model.Employee;
import com.employee_service.model.Reference;
import com.employee_service.exception.ResourceNotFoundException;
import com.employee_service.mapper.ReferenceMapper;
import com.employee_service.repository.ReferenceRepository;
import com.employee_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReferenceService {

    private final ReferenceRepository referenceRepository;
    private final ReferenceMapper referenceMapper;
    private final ValidationUtil validationUtil;

    public ReferenceResponse addReference(UUID tenantId,
                                          UUID employeeId,
                                          ReferenceRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Reference reference = referenceMapper.mapToEntity(tenant, employee, request);
        reference = referenceRepository.save(reference);
        return referenceMapper.mapToDto(reference);
    }

    public List<ReferenceResponse> getAllReferences(UUID tenantId,
                                                    UUID employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        List<Reference> references = referenceRepository.findByEmployeeId(employee.getId());
        return references.stream()
                .filter(ref -> ref.getTenantId().equals(tenant.getId()))
                .map(referenceMapper::mapToDto)
                .toList();
    }

    public List<ReferenceResponse> getEmployeeReferences(UUID tenantId,
                                                         String employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeByEmployeeId(tenant.getId(), employeeId);
        List<Reference> references = referenceRepository.findByEmployeeId(employee.getId());
        return references.stream()
                .filter(ref -> ref.getTenantId().equals(tenant.getId()))
                .map(referenceMapper::mapToDto)
                .toList();
    }

    public ReferenceResponse getReferenceById(UUID tenantId,
                                              UUID employeeId,
                                              UUID referenceId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Reference reference = getReferenceById(tenant.getId(), employee, referenceId);
        return referenceMapper.mapToDto(reference);
    }

    public ReferenceResponse updateReference(UUID tenantId,
                                             UUID employeeId,
                                             UUID referenceId,
                                             ReferenceRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Reference reference = getReferenceById(tenant.getId(), employee, referenceId);
        reference = referenceMapper.updateReference(reference, request);
        reference = referenceRepository.save(reference);
        return referenceMapper.mapToDto(reference);
    }

    public void deleteReference(UUID tenantId,
                                UUID employeeId,
                                UUID referenceId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Reference reference = getReferenceById(tenant.getId(), employee, referenceId);
        referenceRepository.delete(reference);
    }

    private Reference getReferenceById(UUID tenantId, Employee employee, UUID referenceId) {

        return referenceRepository.findById(referenceId)
                .filter(ref -> ref.getTenantId().equals(tenantId))
                .filter(ref -> ref.getEmployee().equals(employee))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reference not found with id '" + referenceId + "'"));
    }
}