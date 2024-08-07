package com.insa.mapper;

import com.insa.dto.requestDto.DepartmentTypeRequest;
import com.insa.dto.requestDto.LocationTypeRequest;
import com.insa.dto.responseDto.DepartmentTypeResponse;
import com.insa.dto.responseDto.LocationTypeResponse;
import com.insa.entity.DepartmentType;
import com.insa.entity.LocationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepartmentTypeMapper {
    @Autowired
    private TenantMapper tenantMapper;
    public DepartmentType mapToEntity(DepartmentTypeRequest departmentTypeRequest) {
        DepartmentType departmentType = new DepartmentType ();
        departmentType.setDepartmentTypeName(departmentTypeRequest.getDepartmentTypeName());
        departmentType.setDescription(departmentTypeRequest.getDescription());
        return departmentType;
    }

    public DepartmentTypeResponse mapToDto(DepartmentType departmentType) {
        DepartmentTypeResponse response = new DepartmentTypeResponse ();
        response.setId(departmentType.getId());
        response.setDepartmentTypeName(departmentType.getDepartmentTypeName());
        response.setDescription(departmentType.getDescription());
        response.setTenantId(departmentType.getTenant().getId());

        return response;
    }

    public DepartmentType updateDepartmentType(DepartmentType departmentType, DepartmentTypeRequest departmentTypeRequest) {
        if (departmentTypeRequest.getDepartmentTypeName() != null)
            departmentType.setDepartmentTypeName (departmentTypeRequest.getDepartmentTypeName());
        if (departmentTypeRequest.getDescription () != null)
            departmentType.setDescription (departmentTypeRequest.getDescription());
        return departmentType;
    }
}
