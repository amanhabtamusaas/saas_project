package com.insa.mapper;

import com.insa.dto.requestDto.DepartmentRequest;
import com.insa.dto.requestDto.DepartmentRequest;
import com.insa.dto.responseDto.DepartmentResponse;
import com.insa.dto.responseDto.DepartmentResponse;
import com.insa.entity.Department;
import com.insa.entity.Tenant;
import com.insa.entity.DepartmentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepartmentMapper {

    @Autowired
    private TenantMapper tenantMapper; // Assuming you have this mapper already defined
    @Autowired
    private DepartmentTypeMapper departmentTypeMapper; // Assuming you have this mapper already defined

    public Department mapToEntity(DepartmentRequest departmentRequest) {
        Department department = new Department();
        department.setDepartmentName(departmentRequest.getDepartmentName());
        department.setEstablishedDate(departmentRequest.getEstablishedDate());
        DepartmentType departmentType = new DepartmentType();
        departmentType.setId(departmentRequest.getDepartmentTypeId());
        department.setDepartmentType(departmentType);
        return department;
    }

    public DepartmentResponse mapToDto(Department department) {
        DepartmentResponse response = new DepartmentResponse();
        response.setId(department.getId());
        response.setDepartmentName(department.getDepartmentName());
        response.setEstablishedDate(department.getEstablishedDate());
        response.setCreatedAt(department.getCreatedAt());
        response.setUpdatedAt(department.getUpdatedAt());
        response.setCreatedBy(department.getCreatedBy());
        response.setUpdatedBy(department.getUpdatedBy());

        if (department.getTenant() != null) {
            response.setTenantId(department.getTenant().getId());
        }

        if (department.getDepartmentType() != null) {
            response.setDepartmentTypeId(department.getDepartmentType().getId());
        }

        if (department.getParentDepartment() != null) {
            response.setParentDepartmentId(department.getParentDepartment().getId());
        }

        if (department.getSubDepartments() != null) {
            List<Long> subDepartmentIds = department.getSubDepartments()
                    .stream()
                    .map(Department::getId)
                    .collect(Collectors.toList());
            response.setSubDepartmentIds(subDepartmentIds);
        }

        return response;
    }

    public Department updateDepartment(Department department, DepartmentRequest departmentRequest) {
        if (departmentRequest.getDepartmentName() != null) {
            department.setDepartmentName(departmentRequest.getDepartmentName());
        }


        if (departmentRequest.getDepartmentTypeId() != null) {
            DepartmentType departmentType = new DepartmentType();
            departmentType.setId(departmentRequest.getDepartmentTypeId());
            department.setDepartmentType(departmentType);
        }


        return department;
    }
}
