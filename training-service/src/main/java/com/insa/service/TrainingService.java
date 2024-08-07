package com.insa.service;

import com.insa.dto.apiDto.BudgetYearDto;
import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.TrainingApproveRequest;
import com.insa.dto.request.TrainingRequest;
import com.insa.dto.response.TrainingResponse;
import com.insa.dto.apiDto.DepartmentDto;
import com.insa.entity.Training;
import com.insa.entity.TrainingCourseCategory;
import com.insa.entity.TrainingCourse;
import com.insa.entity.TrainingInstitution;
import com.insa.enums.TrainingStatus;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.TrainingMapper;
import com.insa.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository annualTrainingRepository;
    private final TrainingCourseCategoryRepository trainingCourseCategoryRepository;
    private final TrainingCourseRepository trainingCourseRepository;
    private final TrainingInstitutionRepository trainingInstitutionRepository;
    private final TenantServiceClient tenantServiceClient;
    private final LeaveServiceClient leaveServiceClient;
    private final TrainingMapper trainingMapper;

    @Transactional
    public TrainingResponse addTraining(Long tenantId,
                                        TrainingRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        DepartmentDto department = tenantServiceClient
                .getDepartmentById(request.getDepartmentId(), tenant.getId());
        BudgetYearDto budgetYear = leaveServiceClient
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
       TrainingInstitution trainingInstitution = trainingInstitutionRepository
                .findById(request.getTrainingInstitutionId())
                .filter(institution -> institution.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training institution not found with id '" + request.getTrainingInstitutionId() +
                        "' in the specified tenant"));
        TrainingCourseCategory trainingCourseCategory = trainingCourseCategoryRepository
                .findById(request.getCourseCategoryId())
                .filter(category -> category.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course category not found with id '" + request.getCourseCategoryId() +
                                "' in the specified tenant"));
        TrainingCourse trainingCourse = trainingCourseRepository
                .findById(request.getTrainingCourseId())
                .filter(course -> course.getTenantId().equals(tenant.getId()))
                .filter(course -> course.getTrainingCourseCategory().equals(trainingCourseCategory))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training course not found with id '" + request.getTrainingCourseId() +
                                "' in the specified tenant"));
        Training training = trainingMapper.mapToEntity(request);
        training.setTenantId(tenant.getId());
        training.setDepartmentId(department.getId());
        training.setBudgetYearId(budgetYear.getId());
        training.setTrainingInstitution(trainingInstitution);
        training.setTrainingCourseCategory(trainingCourseCategory);
        training.setTrainingCourse(trainingCourse);
        training = annualTrainingRepository.save(training);
        return trainingMapper.mapToDto(training);
    }

    public List<TrainingResponse> getAllTrainings(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<Training> trainings = annualTrainingRepository.findAll();
        return trainings.stream()
                .filter(training -> training.getTenantId().equals(tenant.getId()))
                .map(trainingMapper::mapToDto)
                .toList();
    }

    public TrainingResponse getTrainingById(Long tenantId,
                                            Long trainingId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Training training = annualTrainingRepository.findById(trainingId)
                .filter(train -> train.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training not found with id '" + trainingId + "' in the specified tenant"));
        return trainingMapper.mapToDto(training);
    }

    public List<TrainingResponse> getTrainingsByStatus(Long tenantId,
                                                       String trainingStatus) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<Training> trainings = annualTrainingRepository
                .findByTenantIdAndTrainingStatus(
                        tenant.getId(), TrainingStatus.valueOf(trainingStatus.toUpperCase()));
        return trainings.stream()
                .filter(training -> training.getTenantId().equals(tenant.getId()))
                .map(trainingMapper::mapToDto)
                .toList();
    }

    @Transactional
    public TrainingResponse updateTraining(Long tenantId,
                                           Long trainingId,
                                           TrainingRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        DepartmentDto department = tenantServiceClient
                .getDepartmentById(request.getDepartmentId(), tenant.getId());
        BudgetYearDto budgetYear = leaveServiceClient
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        TrainingInstitution trainingInstitution = trainingInstitutionRepository
                .findById(request.getTrainingInstitutionId())
                .filter(institution -> institution.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training institution not found with id '" + request.getTrainingInstitutionId() +
                                "' in the specified tenant"));
        TrainingCourseCategory trainingCourseCategory = trainingCourseCategoryRepository
                .findById(request.getCourseCategoryId())
                .filter(category -> category.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course category not found with id '" + request.getCourseCategoryId() +
                                "' in the specified tenant"));
        TrainingCourse trainingCourse = trainingCourseRepository
                .findById(request.getTrainingCourseId())
                .filter(course -> course.getTenantId().equals(tenant.getId()))
                .filter(course -> course.getTrainingCourseCategory().equals(trainingCourseCategory))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training course not found with id '" + request.getTrainingCourseId() +
                                "' in the specified tenant"));
        Training training = annualTrainingRepository.findById(trainingId)
                .filter(train -> train.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training not found with id '" + trainingId + "' in the specified tenant"));
        training = trainingMapper.updateEntity(training, request);
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
        training = annualTrainingRepository.save(training);
        return trainingMapper.mapToDto(training);
    }

    @Transactional
    public TrainingResponse approveTraining(Long tenantId,
                                                  Long trainingId,
                                                  TrainingApproveRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Training training = annualTrainingRepository.findById(trainingId)
                .filter(train -> train.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training not found with id '" + trainingId + "' in the specified tenant"));
        training = trainingMapper.approveTraining(training, request);
        training = annualTrainingRepository.save(training);
        return trainingMapper.mapToDto(training);
    }

    @Transactional
    public void deleteTraining(Long tenantId,
                               Long trainingId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Training training = annualTrainingRepository.findById(trainingId)
                .filter(train -> train.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training not found with id '" + trainingId + "' in the specified tenant"));
        annualTrainingRepository.delete(training);
    }
}
