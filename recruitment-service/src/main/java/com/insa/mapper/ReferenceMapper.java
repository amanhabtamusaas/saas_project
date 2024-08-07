package com.insa.mapper;

import com.insa.dto.request.ReferenceRequest;
import com.insa.dto.response.ReferenceResponse;
import com.insa.entity.Reference;
import org.springframework.stereotype.Component;

@Component
public class ReferenceMapper {

    public Reference mapToEntity(ReferenceRequest request) {
        Reference reference = new Reference ();
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
        response.setApplicantId (reference.getApplicant ().getId());
        response.setFullName (reference.getFullName ());
        response.setPhoneNumber (reference.getPhoneNumber ());
        response.setJobTitle (reference.getJobTitle ());
        response.setWorkAddress (reference.getWorkAddress ());
        response.setEmail (reference.getEmail ());
        response.setDescription (reference.getDescription ());
        response.setTenantId (reference.getTenantId ());
        response.setCreatedAt (reference.getCreatedAt ());
        response.setUpdatedAt (reference.getUpdatedAt ());
        response.setCreatedBy (reference.getCreatedBy ());
        response.setUpdatedBy (reference.getUpdatedBy ());

        return response;
    }

    public Reference updateReference(Reference reference,
                                     ReferenceRequest request) {

        if (request.getFullName () != null)
            reference.setFullName (request.getFullName ());
        if (request.getPhoneNumber () != null)
            reference.setPhoneNumber (request.getPhoneNumber ());
        if (request.getJobTitle () != null)
            reference.setJobTitle (request.getJobTitle ());
        if (request.getWorkAddress () != null)
            reference.setWorkAddress (request.getWorkAddress ());
        if (request.getEmail () != null)
            reference.setEmail (request.getEmail ());
        if (request.getDescription () != null)
            reference.setDescription (request.getDescription ());

        return reference;
    }
}
