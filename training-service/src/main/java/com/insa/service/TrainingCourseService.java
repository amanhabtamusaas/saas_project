package com.insa.service;

import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.TrainingCourseRequest;
import com.insa.dto.response.TrainingCourseResponse;
import com.insa.entity.TrainingCourseCategory;
import com.insa.entity.TrainingCourse;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.TrainingCourseMapper;
import com.insa.repository.TrainingCourseCategoryRepository;
import com.insa.repository.TrainingCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingCourseService {

    private final TrainingCourseRepository trainingCourseRepository;
    private final TenantServiceClient tenantServiceClient;
    private final TrainingCourseCategoryRepository trainingCourseCategoryRepository;
    private final TrainingCourseMapper trainingCourseMapper;

    @Transactional
    public TrainingCourseResponse addTrainingCourse(Long tenantId,
                                                    TrainingCourseRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        TrainingCourseCategory trainingCourseCategory = trainingCourseCategoryRepository
                .findById(request.getCourseCategoryId())
                .filter(category -> category.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course category not found with id '" + request.getCourseCategoryId() + "' in the specified tenant"));
        TrainingCourse trainingCourse = trainingCourseMapper.mapToEntity(request);
        if (trainingCourseRepository.existsByTenantIdAndCourseName(tenant.getId(), request.getCourseName())) {
            throw new ResourceExistsException(
                    "Training course with Name '" + trainingCourse.getCourseName () + "' already exists");
        }
        trainingCourse.setTenantId(tenant.getId());
        trainingCourse.setTrainingCourseCategory(trainingCourseCategory);
        trainingCourse = trainingCourseRepository.save(trainingCourse);
        return trainingCourseMapper.mapToDto(trainingCourse);
    }

    public List<TrainingCourseResponse> getAllTrainingCourses(Long tenantId,
                                                              Long courseCategoryId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<TrainingCourse> trainingCourses = trainingCourseRepository.findAll();
        return trainingCourses.stream()
                .filter(course -> course.getTenantId().equals(tenant.getId()))
                .filter(course -> course.getTrainingCourseCategory().getId().equals(courseCategoryId))
                .map(trainingCourseMapper::mapToDto)
                .toList();
    }

    public TrainingCourseResponse getTrainingCourseById(Long tenantId,
                                                        Long courseId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        TrainingCourse trainingCourse = trainingCourseRepository.findById(courseId)
                .filter(course -> course.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id '" + courseId + "' in the specified tenant"));
        return trainingCourseMapper.mapToDto(trainingCourse);
    }

    @Transactional
    public TrainingCourseResponse updateTrainingCourse(Long tenantId,
                                                       Long courseId,
                                                       TrainingCourseRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        TrainingCourseCategory trainingCourseCategory = trainingCourseCategoryRepository
                .findById(request.getCourseCategoryId())
                .filter(category -> category.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course category not found with id '" + request.getCourseCategoryId() + "' in the specified tenant"));
        TrainingCourse trainingCourse = trainingCourseRepository.findById(courseId)
                .filter(course -> course.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id '" + courseId + "' in the specified tenant"));
        trainingCourse = trainingCourseMapper.updateEntity(trainingCourse, request);
        if (request.getCourseCategoryId()!= null) {
            trainingCourse.setTrainingCourseCategory(trainingCourseCategory);
        }
        trainingCourse = trainingCourseRepository.save(trainingCourse);
        return trainingCourseMapper.mapToDto(trainingCourse);
    }

    @Transactional
    public void deleteTrainingCourse(Long tenantId,
                                     Long courseId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        TrainingCourse trainingCourse = trainingCourseRepository.findById(courseId)
                .filter(course -> course.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id '" + courseId + "' in the specified tenant"));
        trainingCourseRepository.delete(trainingCourse);
    }
}
