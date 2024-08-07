package com.insa.mapper;

import com.insa.dto.request.AnnualTrainingPlanRequest;
import com.insa.dto.response.AnnualTrainingPlanResponse;
import com.insa.entity.AnnualTrainingPlan;
import org.springframework.stereotype.Component;

@Component
public class AnnualTrainingPlanMapper {

    public AnnualTrainingPlan mapToEntity(AnnualTrainingPlanRequest request) {

        AnnualTrainingPlan annualTrainingPlan = new AnnualTrainingPlan();
        annualTrainingPlan.setNumberOfParticipants(request.getNumberOfParticipants());
        annualTrainingPlan.setNumberOfDays(request.getNumberOfDays());
        annualTrainingPlan.setStartDate(request.getStartDate());
        annualTrainingPlan.setEndDate(request.getEndDate());
        annualTrainingPlan.setCostPerPerson(request.getCostPerPerson());
        annualTrainingPlan.setRound(request.getRound());
        annualTrainingPlan.setVenue(request.getVenue());
        annualTrainingPlan.setRemark(request.getRemark());

        return annualTrainingPlan;
    }

    public AnnualTrainingPlanResponse mapToDto(AnnualTrainingPlan annualTrainingPlan) {

        AnnualTrainingPlanResponse response = new AnnualTrainingPlanResponse();
        response.setId(annualTrainingPlan.getId());
        response.setDepartmentId(annualTrainingPlan.getDepartmentId());
        response.setBudgetYearId(annualTrainingPlan.getBudgetYearId());
        response.setCourseCategoryId(annualTrainingPlan.getTrainingCourseCategory().getId());
        response.setTrainingCourseId(annualTrainingPlan.getTrainingCourse().getId());
        response.setTrainingInstitutionId(annualTrainingPlan.getTrainingInstitution().getId());
        response.setNumberOfParticipants(annualTrainingPlan.getNumberOfParticipants());
        response.setNumberOfDays(annualTrainingPlan.getNumberOfDays());
        response.setStartDate(annualTrainingPlan.getStartDate());
        response.setEndDate(annualTrainingPlan.getEndDate());
        response.setCostPerPerson(annualTrainingPlan.getCostPerPerson());
        response.setRound(annualTrainingPlan.getRound());
        response.setVenue(annualTrainingPlan.getVenue());
        response.setRemark(annualTrainingPlan.getRemark());
        response.setTenantId(annualTrainingPlan.getTenantId());
        response.setCreatedAt(annualTrainingPlan.getCreatedAt());
        response.setUpdatedAt(annualTrainingPlan.getUpdatedAt());
        response.setCreatedBy(annualTrainingPlan.getCreatedBy());
        response.setUpdatedBy(annualTrainingPlan.getUpdatedBy());

        return response;
    }

    public AnnualTrainingPlan updateEntity(AnnualTrainingPlan annualTrainingPlan,
                                       AnnualTrainingPlanRequest request) {

        if (request.getNumberOfParticipants() != null)
            annualTrainingPlan.setNumberOfParticipants(request.getNumberOfParticipants());
        if (request.getNumberOfDays() != null)
            annualTrainingPlan.setNumberOfDays(request.getNumberOfDays());
        if (request.getStartDate() != null)
            annualTrainingPlan.setStartDate(request.getStartDate());
        if (request.getEndDate() != null)
            annualTrainingPlan.setEndDate(request.getEndDate());
        if (request.getCostPerPerson() != null)
            annualTrainingPlan.setCostPerPerson(request.getCostPerPerson());
        if (request.getRound() != null)
            annualTrainingPlan.setRound(request.getRound());
        if (request.getVenue() != null)
            annualTrainingPlan.setVenue(request.getVenue());
        if (request.getRemark() != null)
            annualTrainingPlan.setRemark(request.getRemark());

        return annualTrainingPlan;
    }
}
