package com.insa.mapper;

import com.insa.dto.request.TrainingApproveRequest;
import com.insa.dto.request.TrainingRequest;
import com.insa.dto.response.TrainingResponse;
import com.insa.entity.Training;
import com.insa.enums.TrainingStatus;
import com.insa.enums.TrainingLocation;
import com.insa.enums.TrainingType;
import org.springframework.stereotype.Component;

@Component
public class TrainingMapper {

    public Training mapToEntity(TrainingRequest request) {

        Training training = new Training();
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

    public Training updateEntity(Training training,
                                 TrainingRequest request) {

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
