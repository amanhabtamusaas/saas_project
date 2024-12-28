package com.organization_service.mapper;

import com.organization_service.dto.responseDto.DepartmentHistoryResponse;
import com.organization_service.model.DepartmentHistory;
import org.springframework.stereotype.Component;

@Component
public class DepartmentHistoryMapper {
    public DepartmentHistoryResponse mapToDto(DepartmentHistory department) {
        DepartmentHistoryResponse response = new DepartmentHistoryResponse ();
        response.setId (department.getId ());
        response.setDepartmentName (department.getDepartmentName ());
        response.setEstablishedDate (department.getEstablishedDate ());
        response.setCreatedAt (department.getCreatedAt ());
        response.setUpdatedAt (department.getUpdatedAt ());
        response.setCreatedBy (department.getCreatedBy ());
        response.setUpdatedBy (department.getUpdatedBy ());
        response.setTenant_id(department.getTenant ().getId ());
        response.setDepartment_id(department.getDepartment().getId());

        return response;
    }

}
