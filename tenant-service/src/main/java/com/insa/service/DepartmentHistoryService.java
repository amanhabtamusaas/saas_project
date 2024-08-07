package com.insa.service;

import com.insa.dto.responseDto.DepartmentHistoryResponse;
import com.insa.dto.responseDto.DepartmentResponse;
import com.insa.entity.Department;
import com.insa.entity.DepartmentHistory;
import com.insa.entity.Tenant;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.DepartmentHistoryMapper;
import com.insa.mapper.DepartmentMapper;
import com.insa.repository.DepartmentHistoryRepository;
import com.insa.repository.DepartmentRepository;
import com.insa.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentHistoryService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentHistoryMapper departmentHistoryMapper;
    private final TenantRepository tenantRepository;
    private final DepartmentHistoryRepository departmentHistoryRepository;

    public List<DepartmentHistoryResponse> getAllDepartments(Long tenantId, Long departmentId) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));
        Department department = departmentRepository.findById (departmentId)
                .filter(dept -> dept.getTenant().getId().equals(tenant.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException(
                        "Department not found with id: " + departmentId + " "));

        List<DepartmentHistory> departments = departmentHistoryRepository.findAll ();
        return departments.stream()
                .filter(dept -> dept.getTenant().getId().equals(tenant.getId ()))
                .filter(dept -> dept.getDepartment().getId().equals(department.getId ()))
                .map (departmentHistoryMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
