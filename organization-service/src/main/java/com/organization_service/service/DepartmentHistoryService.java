package com.organization_service.service;

import com.organization_service.dto.responseDto.DepartmentHistoryResponse;
import com.organization_service.model.Department;
import com.organization_service.model.DepartmentHistory;
import com.organization_service.model.Tenant;
import com.organization_service.exception.ResourceNotFoundException;
import com.organization_service.mapper.DepartmentHistoryMapper;
import com.organization_service.repository.DepartmentHistoryRepository;
import com.organization_service.repository.DepartmentRepository;
import com.organization_service.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentHistoryService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentHistoryMapper departmentHistoryMapper;
    private final TenantRepository tenantRepository;
    private final DepartmentHistoryRepository departmentHistoryRepository;

    public List<DepartmentHistoryResponse> getAllDepartments(UUID tenantId, UUID departmentId) {

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));
        Department department = departmentRepository.findById(departmentId)
                .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id: " + departmentId + " "));

        List<DepartmentHistory> departments = departmentHistoryRepository.findAll();
        return departments.stream()
                .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                .filter(dept -> dept.getDepartment().getId().equals(department.getId()))
                .map(departmentHistoryMapper::mapToDto)
                .collect(Collectors.toList());
    }
}