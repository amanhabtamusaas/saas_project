package com.training_service.mapper;

import com.training_service.dto.clientDto.*;
import com.training_service.dto.request.EducationOpportunityRequest;
import com.training_service.dto.response.EducationOpportunityResponse;
import com.training_service.model.EducationOpportunity;
import com.training_service.enums.TrainingLocation;
import com.training_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class EducationOpportunityMapper {

    private final ValidationUtil validationUtil;

    public EducationOpportunity mapToEntity(TenantDto tenant,
                                            EmployeeDto employee,
                                            BudgetYearDto budgetYear,
                                            EducationOpportunityRequest request) {

        QualificationDto qualification = validationUtil
                .getQualificationById(tenant.getId(), request.getQualificationId());
        EducationLevelDto educationLevel = validationUtil
                .getEducationLevelById(tenant.getId(), request.getEducationLevelId());

        EducationOpportunity educationOpportunity = new EducationOpportunity();
        educationOpportunity.setTenantId(tenant.getId());
        educationOpportunity.setBudgetYearId(budgetYear.getId());
        educationOpportunity.setEmployeeId(employee.getId());
        educationOpportunity.setQualificationId(qualification.getId());
        educationOpportunity.setEducationLevelId(educationLevel.getId());
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

    public EducationOpportunity updateEntity(TenantDto tenant,
                                             EducationOpportunity educationOpportunity,
                                             EducationOpportunityRequest request) {

        BudgetYearDto budgetYear = validationUtil
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        EmployeeDto employee = validationUtil
                .getEmployeeByEmployeeId(tenant.getId(), request.getEmployeeId());
        QualificationDto qualification = validationUtil
                .getQualificationById(tenant.getId(), request.getQualificationId());
        EducationLevelDto educationLevel = validationUtil
                .getEducationLevelById(tenant.getId(), request.getEducationLevelId());

        if (request.getBudgetYearId() != null) {
            educationOpportunity.setBudgetYearId(budgetYear.getId());
        }
        if (request.getEmployeeId() != null) {
            educationOpportunity.setEmployeeId(employee.getId());
        }
        if (request.getQualificationId() != null) {
            educationOpportunity.setQualificationId(qualification.getId());
        }
        if (request.getEducationLevelId() != null) {
            educationOpportunity.setEducationLevelId(educationLevel.getId());
        }
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
