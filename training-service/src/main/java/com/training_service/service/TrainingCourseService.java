package com.training_service.service;

import com.training_service.dto.clientDto.TenantDto;
import com.training_service.dto.request.TrainingCourseRequest;
import com.training_service.dto.response.TrainingCourseResponse;
import com.training_service.exception.ResourceExistsException;
import com.training_service.model.TrainingCourse;
import com.training_service.mapper.TrainingCourseMapper;
import com.training_service.repository.TrainingCourseRepository;
import com.training_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainingCourseService {

    private final TrainingCourseRepository trainingCourseRepository;
    private final TrainingCourseMapper trainingCourseMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public TrainingCourseResponse addTrainingCourse(UUID tenantId,
                                                    TrainingCourseRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TrainingCourse trainingCourse = trainingCourseMapper.mapToEntity(tenant, request);
        if (trainingCourseRepository.existsByTenantIdAndCourseName(tenant.getId(), request.getCourseName())) {
            throw new ResourceExistsException(
                    "Training course with Name '" + trainingCourse.getCourseName() + "' already exists");
        }
        trainingCourse = trainingCourseRepository.save(trainingCourse);
        return trainingCourseMapper.mapToDto(trainingCourse);
    }

    public List<TrainingCourseResponse> getAllTrainingCourses(UUID tenantId,
                                                              UUID courseCategoryId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<TrainingCourse> trainingCourses = trainingCourseRepository.findAll();
        return trainingCourses.stream()
                .filter(course -> course.getTenantId().equals(tenant.getId()))
                .filter(course -> course.getTrainingCourseCategory().getId().equals(courseCategoryId))
                .map(trainingCourseMapper::mapToDto)
                .toList();
    }

    public TrainingCourseResponse getTrainingCourseById(UUID tenantId,
                                                        UUID courseId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TrainingCourse trainingCourse = validationUtil.getTrainingCourseById(tenant.getId(), courseId);
        return trainingCourseMapper.mapToDto(trainingCourse);
    }

    @Transactional
    public TrainingCourseResponse updateTrainingCourse(UUID tenantId,
                                                       UUID courseId,
                                                       TrainingCourseRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TrainingCourse trainingCourse = validationUtil.getTrainingCourseById(tenant.getId(), courseId);
        trainingCourse = trainingCourseMapper.updateEntity(tenant, trainingCourse, request);
        trainingCourse = trainingCourseRepository.save(trainingCourse);
        return trainingCourseMapper.mapToDto(trainingCourse);
    }

    @Transactional
    public void deleteTrainingCourse(UUID tenantId,
                                     UUID courseId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TrainingCourse trainingCourse = validationUtil.getTrainingCourseById(tenant.getId(), courseId);
        trainingCourseRepository.delete(trainingCourse);
    }
}