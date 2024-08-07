package com.insa.mapper;

import com.insa.dto.request.EducationOpportunityRequest;
import com.insa.dto.response.EducationOpportunityResponse;
import com.insa.entity.EducationOpportunity;
import com.insa.enums.TrainingLocation;
import org.springframework.stereotype.Component;


@Component
public class EducationOpportunityMapper {

    public EducationOpportunity mapToEntity(EducationOpportunityRequest request) {

        EducationOpportunity educationOpportunity = new EducationOpportunity();
        educationOpportunity.setTrainingLocation(
                TrainingLocation.valueOf(request.getTrainingLocation().toUpperCase()));
        educationOpportunity.setCountry(request.getCountry());
        educationOpportunity.setSponsor(request.getSponsor());
        educationOpportunity.setInstitution(request.getInstitution());
        educationOpportunity.setStartDate(request.getStartDate());
        educationOpportunity.setEndDate(request.getEndDate());
        educationOpportunity.setLetterDate(request.getLetterDate());
        educationOpportunity.setLetterReferenceNumber(request.getLetterReferenceNumber());
        educationOpportunity.setRemark(request.getRemark());
        educationOpportunity.setTotalResult(request.getTotalResult());

        return educationOpportunity;
    }

    public EducationOpportunityResponse mapToDto(EducationOpportunity educationOpportunity) {

        EducationOpportunityResponse response = new EducationOpportunityResponse();
        response.setId(educationOpportunity.getId());
        response.setEmployeeId(educationOpportunity.getEmployeeId());
        response.setBudgetYearId(educationOpportunity.getBudgetYearId());
        response.setEducationLevelId(educationOpportunity.getEducationLevelId());
        response.setQualificationId(educationOpportunity.getQualificationId());
        response.setTrainingLocation(educationOpportunity.getTrainingLocation().getTrainingLocation());
        response.setCountry(educationOpportunity.getCountry());
        response.setSponsor(educationOpportunity.getSponsor());
        response.setInstitution(educationOpportunity.getInstitution());
        response.setStartDate(educationOpportunity.getStartDate());
        response.setEndDate(educationOpportunity.getEndDate());
        response.setLetterDate(educationOpportunity.getLetterDate());
        response.setLetterReferenceNumber(educationOpportunity.getLetterReferenceNumber());
        response.setRemark(educationOpportunity.getRemark());
        response.setTotalResult(educationOpportunity.getTotalResult());
        response.setTenantId(educationOpportunity.getTenantId());
        response.setCreatedAt(educationOpportunity.getCreatedAt());
        response.setUpdatedAt(educationOpportunity.getUpdatedAt());
        response.setCreatedBy(educationOpportunity.getCreatedBy());
        response.setUpdatedBy(educationOpportunity.getUpdatedBy());

        return response;
    }

    public EducationOpportunity updateEntity(EducationOpportunity educationOpportunity,
                                             EducationOpportunityRequest request) {

        if (request.getTrainingLocation() != null) {
            educationOpportunity.setTrainingLocation(
                    TrainingLocation.valueOf(request.getTrainingLocation().toUpperCase()));
        }
        if (request.getCountry() != null) {
            educationOpportunity.setCountry(request.getCountry());
        }
        if (request.getSponsor() != null) {
            educationOpportunity.setSponsor(request.getSponsor());
        }
        if (request.getInstitution() != null) {
            educationOpportunity.setInstitution(request.getInstitution());
        }
        if (request.getStartDate() != null) {
            educationOpportunity.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            educationOpportunity.setEndDate(request.getEndDate());
        }
        if (request.getLetterDate() != null) {
            educationOpportunity.setLetterDate(request.getLetterDate());
        }
        if (request.getLetterReferenceNumber() != null) {
            educationOpportunity.setLetterReferenceNumber(request.getLetterReferenceNumber());
        }
        if (request.getRemark() != null) {
            educationOpportunity.setRemark(request.getRemark());
        }
        if (request.getTotalResult() != null) {
            educationOpportunity.setTotalResult(request.getTotalResult());
        }

        return educationOpportunity;
    }
}
