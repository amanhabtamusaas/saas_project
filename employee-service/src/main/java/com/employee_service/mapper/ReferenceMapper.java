package com.employee_service.mapper;

import com.employee_service.dto.clientDto.TenantDto;
import com.employee_service.dto.request.ReferenceRequest;
import com.employee_service.dto.response.ReferenceResponse;
import com.employee_service.model.Employee;
import com.employee_service.model.Reference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReferenceMapper {

    public Reference mapToEntity(TenantDto tenant,
                                 Employee employee,
                                 ReferenceRequest request) {

        Reference reference = new Reference ();
        reference.setTenantId(tenant.getId());
        reference.setEmployee(employee);
        reference.setFullName (request.getFullName ());
        reference.setPhoneNumber (request.getPhoneNumber ());
        reference.setJobTitle (request.getJobTitle ());
        reference.setWorkAddress (request.getWorkAddress ());
        reference.setEmail (request.getEmail ());
        reference.setDescription (request.getDescription ());

        return reference;
    }

    public ReferenceResponse mapToDto(Reference reference) {

        ReferenceResponse response = new ReferenceResponse ();
        response.setId (reference.getId ());
        response.setTenantId (reference.getTenantId ());
        response.setFullName (reference.getFullName ());
        response.setPhoneNumber (reference.getPhoneNumber ());
        response.setJobTitle (reference.getJobTitle ());
        response.setWorkAddress (reference.getWorkAddress ());
        response.setEmail (reference.getEmail ());
        response.setDescription (reference.getDescription ());
        response.setCreatedAt (reference.getCreatedAt ());
        response.setUpdatedAt (reference.getUpdatedAt ());
        response.setCreatedBy (reference.getCreatedBy ());
        response.setUpdatedBy (reference.getUpdatedBy ());
        response.setEmployeeId (reference.getEmployee ().getId ());

        return response;
    }

    public Reference updateReference(Reference reference,
                                     ReferenceRequest request) {

        if (request.getFullName () != null) {
            reference.setFullName(request.getFullName());
        }
        if (request.getPhoneNumber () != null) {
            reference.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getJobTitle () != null) {
            reference.setJobTitle(request.getJobTitle());
        }
        if (request.getWorkAddress () != null) {
            reference.setWorkAddress(request.getWorkAddress());
        }
        if (request.getEmail () != null) {
            reference.setEmail(request.getEmail());
        }
        if (request.getDescription () != null) {
            reference.setDescription(request.getDescription());
        }

        return reference;
    }
}
