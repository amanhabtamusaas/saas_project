package com.insa.service;

import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.PreServiceCourseRequest;
import com.insa.dto.response.PreServiceCourseResponse;
import com.insa.entity.PreServiceCourseType;
import com.insa.entity.PreServiceCourse;
import com.insa.entity.PreServiceTrainee;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.PreServiceCourseMapper;
import com.insa.repository.PreServiceCourseTypeRepository;
import com.insa.repository.PreServiceCourseRepository;
import com.insa.repository.PreServiceTraineeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PreServiceCourseService {

    private final PreServiceCourseRepository preServiceCourseRepository;
    private final TenantServiceClient tenantServiceClient;
    private final PreServiceCourseMapper preServiceCourseMapper;
    private final PreServiceCourseTypeRepository preServiceCourseTypeRepository;
    private final PreServiceTraineeRepository preServiceTraineeRepository;

    @Transactional
    public PreServiceCourseResponse addPreServiceCourse(Long tenantId,
                                                        PreServiceCourseRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceCourseType preServiceCourseType = preServiceCourseTypeRepository.findById(request.getCourseTypeId())
                .filter(type -> type.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course type not found with id '" + request.getCourseTypeId() + "' in the specified tenant"));
        PreServiceCourse preServiceCourse = preServiceCourseMapper.mapToEntity(request);
        if (preServiceCourseRepository.existsByTenantIdAndCourseName(
                tenant.getId(), request.getCourseName())) {
            throw new ResourceExistsException(
                    "Course with name '" + request.getCourseName() + "' already exists in the specified tenant");
        }
        preServiceCourse.setTenantId(tenant.getId());
        preServiceCourse.setPreServiceCourseType(preServiceCourseType);
        preServiceCourse = preServiceCourseRepository.save(preServiceCourse);
        return preServiceCourseMapper.mapToDto(preServiceCourse);
    }

    public List<PreServiceCourseResponse> getAllPreServiceCourses(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<PreServiceCourse> preServiceCourses = preServiceCourseRepository.findAll();
        return preServiceCourses.stream()
                .filter(course -> course.getTenantId().equals(tenant.getId()))
                .map(preServiceCourseMapper::mapToDto)
                .toList();
    }

    public PreServiceCourseResponse getPreServiceCourse(Long tenantId,
                                                        Long courseId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceCourse preServiceCourse = preServiceCourseRepository.findById(courseId)
                .filter(course -> course.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pre Service Course not found with id '" + courseId + "' in the specified tenant"));
        return preServiceCourseMapper.mapToDto(preServiceCourse);
    }

    public List<PreServiceCourseResponse> getCoursesByTrainee(Long tenantId,
                                                              Long traineeId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceTrainee preServiceTrainee = preServiceTraineeRepository.findById(traineeId)
                .filter(trainee -> trainee.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pre Service Trainee not found with id '" + traineeId + "' in the specified tenant"));
        Set<PreServiceCourse> traineeCourses = preServiceTrainee.getPreServiceCourses();
        return traineeCourses.stream()
                .map(preServiceCourseMapper::mapToDto)
                .toList();
    }

    public List<PreServiceCourseResponse> getPreServiceCoursesByCourseType(Long tenantId,
                                                                           Long courseTypeId) {
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceCourseType preServiceCourseType = preServiceCourseTypeRepository.findById(courseTypeId)
                .filter(type -> type.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course type not found with id '" + courseTypeId + "' in the specified tenant"));
        List<PreServiceCourse> preServiceCourses = preServiceCourseRepository
                .findByTenantIdAndPreServiceCourseTypeId(tenant.getId(), preServiceCourseType.getId());
        return preServiceCourses.stream()
                .filter(course -> course.getTenantId().equals(tenant.getId()))
                .map(preServiceCourseMapper::mapToDto)
                .toList();
    }

    @Transactional
    public PreServiceCourseResponse updatePreServiceCourse(Long tenantId,
                                                           Long courseId,
                                                           PreServiceCourseRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceCourseType preServiceCourseType = preServiceCourseTypeRepository.findById(request.getCourseTypeId())
                .filter(type -> type.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course type not found with id '" + request.getCourseTypeId() + "' in the specified tenant"));
        PreServiceCourse preServiceCourse = preServiceCourseRepository.findById(courseId)
                .filter(course -> course.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pre Service Course Not Found with id '" + courseId + "' in the specified tenant"));
        preServiceCourse = preServiceCourseMapper.updateEntity(preServiceCourse, request);
        if (request.getCourseTypeId() != null) {
            preServiceCourse.setPreServiceCourseType(preServiceCourseType);
        }
        preServiceCourse = preServiceCourseRepository.save(preServiceCourse);
        return preServiceCourseMapper.mapToDto(preServiceCourse);
    }

    @Transactional
    public void removeCourseFromTrainee(Long tenantId,
                                        Long traineeId,
                                        Long courseId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceTrainee preServiceTrainee = preServiceTraineeRepository.findById(traineeId)
                .filter(trainee -> trainee.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pre Service Trainee not found with id '" + traineeId + "' in the specified tenant"));
        PreServiceCourse courseToRemove = preServiceCourseRepository.findById(courseId)
                .filter(c -> c.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pre Service Course Not Found with id '" + courseId + "' in the specified tenant"));
        Set<PreServiceCourse> traineeCourses = preServiceTrainee.getPreServiceCourses();
        boolean removed = traineeCourses.remove(courseToRemove);
        if (!removed) {
            throw new ResourceNotFoundException(
                    "Course not associated with the trainee '" + courseId + "' in the specified tenant");
        }
        preServiceTrainee.setPreServiceCourses(traineeCourses);
        preServiceTraineeRepository.save(preServiceTrainee);
    }

    @Transactional
    public void deletePreServiceCourse(Long tenantId,
                                       Long courseId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceCourse preServiceCourse = preServiceCourseRepository.findById(courseId)
                .filter(course -> course.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pre Service Course Not Found with id '" + courseId + "' in the specified tenant"));
        Set<PreServiceTrainee> preServiceTrainees = preServiceCourse.getPreServiceTrainees();
        for (PreServiceTrainee trainee : preServiceTrainees) {
            trainee.getPreServiceCourses().remove(preServiceCourse);
            preServiceTraineeRepository.save(trainee);
        }
        preServiceCourseRepository.delete(preServiceCourse);
    }
}
