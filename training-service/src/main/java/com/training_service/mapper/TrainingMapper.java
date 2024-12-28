package com.training_service.mapper;

import com.training_service.dto.clientDto.BudgetYearDto;
import com.training_service.dto.clientDto.DepartmentDto;
import com.training_service.dto.clientDto.TenantDto;
import com.training_service.dto.request.TrainingApproveRequest;
import com.training_service.dto.request.TrainingRequest;
import com.training_service.dto.response.TrainingResponse;
import com.training_service.model.Training;
import com.training_service.enums.TrainingStatus;
import com.training_service.enums.TrainingLocation;
import com.training_service.enums.TrainingType;
import com.training_service.model.TrainingCourse;
import com.training_service.model.TrainingCourseCategory;
import com.training_service.model.TrainingInstitution;
import com.training_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingMapper {

    private final ValidationUtil validationUtil;

    public Training mapToEntity(TenantDto tenant,
                                TrainingRequest request) {

        DepartmentDto department = validationUtil
                .getDepartmentById(tenant.getId(), request.getDepartmentId());
        BudgetYearDto budgetYear = validationUtil
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        TrainingInstitution trainingInstitution = validationUtil
                .getInstitutionById(tenant.getId(), request.getTrainingInstitutionId());
        TrainingCourseCategory trainingCourseCategory = validationUtil
                .getCategoryById(tenant.getId(), request.getCourseCategoryId());
        TrainingCourse trainingCourse = validationUtil
                .getTrainingCourseById(tenant.getId(), request.getTrainingCourseId());

        Training training = new Training();
        training.setTenantId(tenant.getId());
        training.setDepartmentId(department.getId());
        training.setBudgetYearId(budgetYear.getId());
        training.setTrainingInstitution(trainingInstitution);
        training.setTrainingCourseCategory(trainingCourseCategory);
        training.setTrainingCourse(trainingCourse);
        training.setTrainingType(TrainingType.valueOf(request.getTrainingType().toUpperCase()));
        training.setNumberOfParticipants(request.getNumberOfParticipants());
        training.setNumberOfDays(request.getNumberOfDays());
        training.setStartDate(request.getStartDate());
        training.setEndDate(request.getEndDate());
        training.setCostPerPerson(request.getCostPerPerson());
        training.setSponsoredBy(request.getSponsoredBy());
        training.setTrainingLocation(TrainingLocation.valueOf(
                request.getTrainingLocation().toUpperCase()));
        training.setReason(request.getReason());
        training.setVenue(request.getVenue());
        training.setTrainingStatus(TrainingStatus.PENDING);
        training.setRemark(request.getRemark());

        return training;
    }

    public TrainingResponse mapToDto(Training training) {

        TrainingResponse response = new TrainingResponse();
        response.setId(training.getId());
        response.setTrainingType(training.getTrainingType().getTrainingType());
        response.setDepartmentId(training.getDepartmentId());
        response.setBudgetYearId(training.getBudgetYearId());
        response.setCourseCategoryId(training.getTrainingCourseCategory().getId());
        response.setTrainingCourseId(training.getTrainingCourse().getId());
        response.setTrainingInstitutionId(training.getTrainingInstitution().getId());
        response.setNumberOfParticipants(training.getNumberOfParticipants());
        response.setNumberOfDays(training.getNumberOfDays());
        response.setStartDate(training.getStartDate());
        response.setEndDate(training.getEndDate());
        response.setCostPerPerson(training.getCostPerPerson());
        response.setSponsoredBy(training.getSponsoredBy());
        response.setTrainingLocation(training.getTrainingLocation().getTrainingLocation());
        response.setReason(training.getReason());
        response.setVenue(training.getVenue());
        response.setTrainingStatus(training.getTrainingStatus().getTrainingStatus());
        response.setRemark(training.getRemark());
        response.setTenantId(training.getTenantId());
        response.setCreatedAt(training.getCreatedAt());
        response.setUpdatedAt(training.getUpdatedAt());
        response.setCreatedBy(training.getCreatedBy());
        response.setUpdatedBy(training.getUpdatedBy());

        return response;
    }

    public Training updateEntity(TenantDto tenant,
                                 Training training,
                                 TrainingRequest request) {

        DepartmentDto department = validationUtil
                .getDepartmentById(tenant.getId(), request.getDepartmentId());
        BudgetYearDto budgetYear = validationUtil
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        TrainingInstitution trainingInstitution = validationUtil
                .getInstitutionById(tenant.getId(), request.getTrainingInstitutionId());
        TrainingCourseCategory trainingCourseCategory = validationUtil
                .getCategoryById(tenant.getId(), request.getCourseCategoryId());
        TrainingCourse trainingCourse = validationUtil
                .getTrainingCourseById(tenant.getId(), request.getTrainingCourseId());

        if (request.getDepartmentId() != null) {
            training.setDepartmentId(department.getId());
        }
        if (request.getBudgetYearId() != null) {
            training.setBudgetYearId(budgetYear.getId());
        }
        if (request.getTrainingInstitutionId() != null) {
            training.setTrainingInstitution(trainingInstitution);
        }
        if (request.getCourseCategoryId() != null) {
            training.setTrainingCourseCategory(trainingCourseCategory);
        }
        if (request.getTrainingCourseId() != null) {
            training.setTrainingCourse(trainingCourse);
        }
        if (request.getTrainingType() != null) {
            training.setTrainingType(TrainingType.valueOf(request.getTrainingType().toUpperCase()));
        }
        if (request.getNumberOfParticipants() != null) {
            training.setNumberOfParticipants(request.getNumberOfParticipants());
        }
        if (request.getNumberOfDays() != null) {
            training.setNumberOfDays(request.getNumberOfDays());
        }
        if (request.getStartDate() != null) {
            training.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            training.setEndDate(request.getEndDate());
        }
        if (request.getCostPerPerson() != null) {
            training.setCostPerPerson(request.getCostPerPerson());
        }
        if (request.getSponsoredBy() != null) {
            training.setSponsoredBy(request.getSponsoredBy());
        }
        if (request.getTrainingLocation() != null) {
            training.setTrainingLocation(TrainingLocation.valueOf(
                    request.getTrainingLocation().toUpperCase()));
        }
        if (request.getReason() != null) {
            training.setReason(request.getReason());
        }
        if (request.getVenue() != null) {
            training.setVenue(request.getVenue());
        }
        if (request.getRemark() != null) {
            training.setRemark(request.getRemark());
        }

        return training;
    }

    public Training approveTraining(Training training,
                                    TrainingApproveRequest request) {

        if (request.getDecision() != null) {
            training.setTrainingStatus(TrainingStatus
                    .valueOf(request.getDecision().toUpperCase()));
        }
        if (request.getRemark() != null) {
            training.setRemark(request.getRemark());
        }

        return training;
    }
}
