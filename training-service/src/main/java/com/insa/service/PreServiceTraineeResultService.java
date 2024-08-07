package com.insa.service;

import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.PreServiceTraineeResultRequest;
import com.insa.dto.response.PreServiceTraineeResultResponse;
import com.insa.entity.PreServiceCourse;
import com.insa.entity.PreServiceTrainee;
import com.insa.entity.PreServiceTraineeResult;
import com.insa.enums.Semester;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.PreServiceTraineeResultMapper;
import com.insa.repository.PreServiceCourseRepository;
import com.insa.repository.PreServiceTraineeRepository;
import com.insa.repository.PreServiceTraineeResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreServiceTraineeResultService {

    private final PreServiceTraineeResultRepository traineeResultRepository;
    private final PreServiceCourseRepository courseRepository;
    private final PreServiceTraineeRepository traineeRepository;
    private final TenantServiceClient tenantServiceClient;
    private final PreServiceTraineeResultMapper traineeResultMapper;

    @Transactional
    public PreServiceTraineeResultResponse addTraineeResult(Long tenantId,
                                                            Long traineeId,
                                                            Long courseId,
                                                            PreServiceTraineeResultRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceTrainee trainee = traineeRepository.findById(traineeId)
                .filter(tr -> tr.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trainee not found with id '" + traineeId + "'in the specified tenant"));
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
        PreServiceTraineeResult traineeResult = traineeResultMapper.mapToEntity(request);
        traineeResult.setTenantId(tenant.getId());
        traineeResult.setPreServiceTrainee(trainee);
        traineeResult.setPreServiceCourse(course);
        traineeResult = traineeResultRepository.save(traineeResult);
        return traineeResultMapper.mapToDto(traineeResult);
    }

    public List<PreServiceTraineeResultResponse> getTraineesResultByCourse(Long tenantId,
                                                                           Long courseId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceCourse course = courseRepository.findById(courseId)
                .filter(c -> c.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id '" + courseId + "' in the specified tenant"));
        List<PreServiceTraineeResult> traineeResults = traineeResultRepository
                .findByTenantIdAndPreServiceCourseId(tenant.getId(), course.getId());
        return traineeResults.stream()
                .filter(tr -> tr.getTenantId().equals(tenant.getId()))
                .map(traineeResultMapper::mapToDto)
                .toList();
    }

    public PreServiceTraineeResultResponse getTraineeCourseResult(Long tenantId,
                                                                Long traineeId,
                                                                Long courseId,
                                                                Long resultId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceTrainee trainee = traineeRepository.findById(traineeId)
                .filter(tr -> tr.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trainee not found with id '" + traineeId + "' in the specified tenant"));
        PreServiceCourse course = courseRepository.findById(courseId)
                .filter(c -> c.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id '" + courseId + "' in the specified tenant"));
        PreServiceTraineeResult result = traineeResultRepository.findById(resultId)
                .filter(r -> r.getTenantId().equals(tenant.getId()))
                .filter(r -> r.getPreServiceTrainee().equals(trainee))
                .filter(r -> r.getPreServiceCourse().equals(course))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Result not found with id '" + resultId + "' in the specified course"));
        return traineeResultMapper.mapToDto(result);
    }

    public PreServiceTraineeResultResponse getTraineeResultById(Long tenantId,
                                                                Long resultId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceTraineeResult result = traineeResultRepository.findById(resultId)
                .filter(c -> c.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Result not found with id '" + resultId + "'"));
        return traineeResultMapper.mapToDto(result);
    }

    @Transactional
    public PreServiceTraineeResultResponse updateTraineeResult(Long tenantId,
                                                               Long traineeId,
                                                               Long courseId,
                                                               Long resultId,
                                                               PreServiceTraineeResultRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceTrainee trainee = traineeRepository.findById(traineeId)
                .filter(tr -> tr.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trainee not found with id '" + traineeId + "' in the specified tenant"));
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
        PreServiceTraineeResult result = traineeResultRepository.findById(resultId)
                .filter(c -> c.getTenantId().equals(tenant.getId()))
                .filter(c -> c.getPreServiceTrainee().equals(trainee))
                .filter(c -> c.getPreServiceCourse().equals(course))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Result not found with id '" + resultId + "'"));
        PreServiceTraineeResult traineeResult = traineeResultMapper.updateEntity(result, request);
        traineeResult = traineeResultRepository.save(traineeResult);
        return traineeResultMapper.mapToDto(traineeResult);
    }

    @Transactional
    public void deleteTraineeResult(Long tenantId,
                                    Long resultId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceTraineeResult result = traineeResultRepository.findById(resultId)
                .filter(c -> c.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Result not found with id '" + resultId + "'"));
        traineeResultRepository.delete(result);
    }
}
