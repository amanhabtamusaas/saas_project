package com.insa.mapper;

import com.insa.dto.request.PreServiceTraineeResultRequest;
import com.insa.dto.response.PreServiceTraineeResultResponse;
import com.insa.entity.PreServiceTraineeResult;
import com.insa.enums.Decision;
import com.insa.enums.Semester;
import org.springframework.stereotype.Component;

@Component
public class PreServiceTraineeResultMapper {

    public PreServiceTraineeResult mapToEntity(PreServiceTraineeResultRequest request) {

        PreServiceTraineeResult traineeResult = new PreServiceTraineeResult();
        traineeResult.setStartDate(request.getStartDate());
        traineeResult.setEndDate(request.getEndDate());
        traineeResult.setSemester(Semester.valueOf(request.getSemester().toUpperCase()));
        traineeResult.setResult(request.getResult());
        traineeResult.setDecision(Decision.valueOf(request.getDecision().toUpperCase()));
        traineeResult.setRemark(request.getRemark());

        return traineeResult;
    }

    public PreServiceTraineeResultResponse mapToDto(PreServiceTraineeResult traineeResult) {

        PreServiceTraineeResultResponse response = new PreServiceTraineeResultResponse();
        response.setId(traineeResult.getId());
        response.setStartDate(traineeResult.getStartDate());
        response.setEndDate(traineeResult.getEndDate());
        response.setSemester(traineeResult.getSemester().name());
        response.setTraineeId(traineeResult.getPreServiceTrainee().getId());
        response.setCourseId(traineeResult.getPreServiceCourse().getId());
        response.setDecision(traineeResult.getDecision().getDecision());
        response.setResult(traineeResult.getResult());
        response.setRemark(traineeResult.getRemark());
        response.setTenantId(traineeResult.getTenantId());
        response.setCreatedAt(traineeResult.getCreatedAt());
        response.setUpdatedAt(traineeResult.getUpdatedAt());
        response.setCreatedBy(traineeResult.getCreatedBy());
        response.setUpdatedBy(traineeResult.getUpdatedBy());

        return response;
    }

    public PreServiceTraineeResult updateEntity(PreServiceTraineeResult traineeResult,
                                                PreServiceTraineeResultRequest request) {

        if (request.getStartDate() != null) {
            traineeResult.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            traineeResult.setEndDate(request.getEndDate());
        }
        if (request.getSemester() != null) {
            traineeResult.setSemester(Semester.valueOf(request.getSemester().toUpperCase()));
        }
        if (request.getResult() != null) {
            traineeResult.setResult(request.getResult());
        }
        if (request.getDecision() != null) {
            traineeResult.setDecision(Decision.valueOf(request.getDecision().toUpperCase()));
        }
        if (request.getRemark() != null) {
            traineeResult.setRemark(request.getRemark());
        }

        return traineeResult;
    }
}
