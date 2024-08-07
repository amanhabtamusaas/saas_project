package com.insa.mapper;

import com.insa.dto.request.UniversityRequest;
import com.insa.dto.response.UniversityResponse;
import com.insa.entity.University;
import org.springframework.stereotype.Component;

@Component
public class UniversityMapper {

    public University mapToEntity(UniversityRequest request) {

        University university = new University();
        university.setUniversityName(request.getUniversityName());
        university.setAbbreviatedName(request.getAbbreviatedName());
        university.setCostPerPerson(request.getCostPerPerson());
        university.setMobilePhoneNumber(request.getMobilePhoneNumber());
        university.setTelephoneNumber(request.getTelephoneNumber());
        university.setEmail(request.getEmail());
        university.setFax(request.getFax());
        university.setWebsite(request.getWebsite());
        university.setRemark(request.getRemark());

        return university;
    }

    public UniversityResponse mapToDto(University university) {

        UniversityResponse response = new UniversityResponse();
        response.setId(university.getId());
        response.setUniversityName(university.getUniversityName());
        response.setAbbreviatedName(university.getAbbreviatedName());
        response.setLocationId(university.getLocationId());
        response.setCostPerPerson(university.getCostPerPerson());
        response.setMobilePhoneNumber(university.getMobilePhoneNumber());
        response.setTelephoneNumber(university.getTelephoneNumber());
        response.setEmail(university.getEmail());
        response.setFax(university.getFax());
        response.setWebsite(university.getWebsite());
        response.setRemark(university.getRemark());
        response.setTenantId(university.getTenantId());
        response.setCreatedAt(university.getCreatedAt());
        response.setUpdatedAt(university.getUpdatedAt());
        response.setCreatedBy(university.getCreatedBy());
        response.setUpdatedBy(university.getUpdatedBy());

        return response;
    }

    public University updateEntity(University university,
                                   UniversityRequest request) {

        if(request.getUniversityName() != null) {
            university.setUniversityName(request.getUniversityName());
        }
        if(request.getAbbreviatedName() != null) {
            university.setAbbreviatedName(request.getAbbreviatedName());
        }
        if(request.getCostPerPerson() != null) {
            university.setCostPerPerson(request.getCostPerPerson());
        }
        if(request.getMobilePhoneNumber() != null) {
            university.setMobilePhoneNumber(request.getMobilePhoneNumber());
        }
        if(request.getTelephoneNumber() != null) {
            university.setTelephoneNumber(request.getTelephoneNumber());
        }
        if(request.getEmail() != null) {
            university.setEmail(request.getEmail());
        }
        if(request.getFax() != null) {
            university.setFax(request.getFax());
        }
        if(request.getWebsite() != null) {
            university.setWebsite(request.getWebsite());
        }
        if(request.getRemark() != null) {
            university.setRemark(request.getRemark());
        }

        return university;
    }
}
