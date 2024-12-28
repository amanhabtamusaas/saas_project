package com.training_service.service;

import com.training_service.dto.clientDto.TenantDto;
import com.training_service.dto.request.PreServiceTraineeResultRequest;
import com.training_service.dto.response.PreServiceTraineeResultResponse;
import com.training_service.exception.ResourceExistsException;
import com.training_service.exception.ResourceNotFoundException;
import com.training_service.model.PreServiceCourse;
import com.training_service.model.PreServiceTrainee;
import com.training_service.model.PreServiceTraineeResult;
import com.training_service.enums.Semester;
import com.training_service.mapper.PreServiceTraineeResultMapper;
import com.training_service.repository.PreServiceCourseRepository;
import com.training_service.repository.PreServiceTraineeResultRepository;
import com.training_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PreServiceTraineeResultService {

    private final PreServiceTraineeResultRepository traineeResultRepository;
    private final PreServiceCourseRepository courseRepository;
    private final PreServiceTraineeResultMapper traineeResultMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public PreServiceTraineeResultResponse addTraineeResult(UUID tenantId,
                                                            UUID traineeId,
                                                            UUID courseId,
                                                            PreServiceTraineeResultRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTrainee trainee = validationUtil.getTraineeById(tenant.getId(), traineeId);
        List<PreServiceCourse> traineeCourses = courseRepository
                .findByTenantIdAndPreServiceTraineesId(tenant.getId(), traineeId)
                .stream()
                .filter(c -> c.getTenantId().equals(tenant.getId()))
                .toList();
        PreServiceCourse course = traineeCourses.stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id '" + courseId + "' for trainee with id '" + traineeId + "'"));
        if (traineeResultRepository.existsByTenantIdAndPreServiceTraineeIdAndPreServiceCourseIdAndSemester(
                tenant.getId(), trainee.getId(), course.getId(),
                Semester.valueOf(request.getSemester().toUpperCase()))) {
            throw new ResourceExistsException("The result for trainee with id '" + trainee.getId() +
                    "' and course with id '" + course.getId() +
                    "' in the '" + request.getSemester() + "' semester already exists.");
        }
        PreServiceTraineeResult traineeResult = traineeResultMapper.mapToEntity(tenant, trainee, course, request);
        traineeResult = traineeResultRepository.save(traineeResult);
        return traineeResultMapper.mapToDto(traineeResult);
    }

    public List<PreServiceTraineeResultResponse> getTraineesResultByCourseId(UUID tenantId,
                                                                             UUID courseId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceCourse course = validationUtil.getPreServiceCourseById(tenant.getId(), courseId);
        List<PreServiceTraineeResult> traineeResults = traineeResultRepository
                .findByTenantIdAndPreServiceCourseId(tenant.getId(), course.getId());
        return traineeResults.stream()
                .filter(tr -> tr.getTenantId().equals(tenant.getId()))
                .map(traineeResultMapper::mapToDto)
                .toList();
    }

    public PreServiceTraineeResultResponse getTraineeCourseResultById(UUID tenantId,
                                                                      UUID traineeId,
                                                                      UUID courseId,
                                                                      UUID resultId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTrainee trainee = validationUtil.getTraineeById(tenant.getId(), traineeId);
        PreServiceCourse course = validationUtil.getPreServiceCourseById(tenant.getId(), courseId);
        PreServiceTraineeResult result = getResultById(tenant.getId(), resultId);
        return traineeResultMapper.mapToDto(result);
    }

    public PreServiceTraineeResultResponse getTraineeResultById(UUID tenantId,
                                                                UUID resultId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTraineeResult result = getResultById(tenant.getId(), resultId);
        return traineeResultMapper.mapToDto(result);
    }

    @Transactional
    public PreServiceTraineeResultResponse updateTraineeResult(UUID tenantId,
                                                               UUID traineeId,
                                                               UUID courseId,
                                                               UUID resultId,
                                                               PreServiceTraineeResultRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTrainee trainee = validationUtil.getTraineeById(tenant.getId(), traineeId);
        List<PreServiceCourse> traineeCourses = courseRepository
                .findByTenantIdAndPreServiceTraineesId(tenant.getId(), traineeId)
                .stream()
                .filter(c -> c.getTenantId().equals(tenant.getId()))
                .toList();
        PreServiceCourse course = traineeCourses.stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id '" + courseId +
                                "' for trainee with id '" + traineeId + "'"));
        PreServiceTraineeResult result = getResultById(tenant.getId(), resultId);
        PreServiceTraineeResult traineeResult = traineeResultMapper.updateEntity(result, request);
        traineeResult = traineeResultRepository.save(traineeResult);
        return traineeResultMapper.mapToDto(traineeResult);
    }

    @Transactional
    public void deleteTraineeResult(UUID tenantId,
                                    UUID resultId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTraineeResult result = getResultById(tenant.getId(), resultId);
        traineeResultRepository.delete(result);
    }

    public PreServiceTraineeResult getResultById(UUID tenantId, UUID resultId) {

        return traineeResultRepository.findById(resultId)
                .filter(c -> c.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Result not found with id '" + resultId + "'"));
    }
}