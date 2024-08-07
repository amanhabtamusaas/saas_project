package com.insa.service;

import com.insa.dto.apiDto.*;
import com.insa.dto.request.PreServiceTraineeRequest;
import com.insa.dto.response.PreServiceCourseResponse;
import com.insa.dto.response.PreServiceTraineeResponse;
import com.insa.entity.CheckedDocument;
import com.insa.entity.PreServiceCourse;
import com.insa.entity.PreServiceTrainee;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.PreServiceCourseMapper;
import com.insa.mapper.PreServiceTraineeMapper;
import com.insa.repository.CheckedDocumentRepository;
import com.insa.repository.PreServiceCourseRepository;
import com.insa.repository.PreServiceTraineeRepository;
import com.insa.utility.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PreServiceTraineeService {

    private final PreServiceTraineeRepository preServiceTraineeRepository;
    private final PreServiceTraineeMapper preServiceTraineeMapper;
    private final TenantServiceClient tenantServiceClient;
    private final LeaveServiceClient leaveServiceClient;
    private final CheckedDocumentRepository checkedDocumentRepository;
    private final PreServiceCourseRepository preServiceCourseRepository;
    private final PreServiceCourseMapper preServiceCourseMapper;

    @Transactional
    public PreServiceTraineeResponse createPreServiceTrainee(Long tenantId,
                                                          PreServiceTraineeRequest request,
                                                          MultipartFile file) throws IOException {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        BudgetYearDto budgetYear = leaveServiceClient
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        LocationDto location = tenantServiceClient
                .getLocationById(request.getLocationId(), tenant.getId());
        EducationLevelDto educationLevel = tenantServiceClient
                .getEducationLevelById(request.getEducationLevelId(), tenant.getId());
        FieldOfStudyDto fieldOfStudy = tenantServiceClient
                .getFieldOfStudyById(request.getFieldOfStudyId(), tenant.getId());
        // for documents been checked
        Set<CheckedDocument> checkedDocuments = new HashSet<>();
        for (Long documentId : request.getDocumentIds()) {
            CheckedDocument checkedDocument = checkedDocumentRepository.findById(documentId)
                    .filter(doc -> doc.getTenantId().equals(tenant.getId()))
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Document not found with id '" + documentId + "' in the specified tenant"));
            checkedDocuments.add(checkedDocument);

        }
        if (preServiceTraineeRepository.existsByTenantIdAndTraineeId(tenant.getId(), request.getTraineeId())) {
            throw new ResourceNotFoundException(
                    "Trainee with id '" + request.getTraineeId() + "' already exists in the specified tenant");
        }
        PreServiceTrainee preServiceTrainee = preServiceTraineeMapper.mapToEntity(request,file);
        preServiceTrainee.setTenantId(tenant.getId());
        preServiceTrainee.setBudgetYearId(budgetYear.getId());
        preServiceTrainee.setLocationId(location.getId());
        preServiceTrainee.setEducationLevelId(educationLevel.getId());
        preServiceTrainee.setFieldOfStudyId(fieldOfStudy.getId());
        preServiceTrainee.setCheckedDocuments(checkedDocuments);
        preServiceTrainee = preServiceTraineeRepository.save(preServiceTrainee);
        return preServiceTraineeMapper.mapToDto(preServiceTrainee);
    }

    public List<PreServiceTraineeResponse> getAllPreServiceTrainees(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<PreServiceTrainee> preServiceTrainees = preServiceTraineeRepository.findAll();
        return preServiceTrainees.stream()
                .filter(trainee -> trainee.getTenantId().equals(tenant.getId()))
                .map(preServiceTraineeMapper::mapToDto)
                .toList();
    }

    public PreServiceTraineeResponse getPreServiceTraineeById(Long tenantId,
                                                              Long traineeId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceTrainee preServiceTrainee = preServiceTraineeRepository.findById(traineeId)
                .filter(trainee -> trainee.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pre Service Trainee not found with id '" + traineeId + "' in the specified tenant"));
        return preServiceTraineeMapper.mapToDto(preServiceTrainee);
    }

    public List<PreServiceTraineeResponse> getPreServiceTraineesByYear(Long tenantId,
                                                                       Long yearId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<PreServiceTrainee> preServiceTrainees = preServiceTraineeRepository
                .findByTenantIdAndBudgetYearId(tenant.getId(), yearId);
        return preServiceTrainees.stream()
                .filter(trainee -> trainee.getTenantId().equals(tenant.getId()))
                .map(preServiceTraineeMapper::mapToDto)
                .toList();
    }

    public byte[] getTraineeProfileImageById(Long tenantId,
                                             Long traineeId,
                                             MediaType mediaType) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceTrainee trainee = preServiceTraineeRepository.findById(traineeId)
                .filter(t -> t.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trainee not found with id '" + traineeId + "' in the specified tenant"));
        if (trainee.getImageType() == null) {
            throw new ResourceNotFoundException("Trainee image type is not specified");
        }

        switch (trainee.getImageType()) {
            case "image/jpeg":
                mediaType = MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "image/png":
                mediaType = MediaType.valueOf(MediaType.IMAGE_PNG_VALUE);
                break;
            case "image/gif":
                mediaType = MediaType.valueOf(MediaType.IMAGE_GIF_VALUE);
                break;
            default:
                throw new IllegalArgumentException("Unsupported file type: " + trainee.getImageType());
        }
        byte[] imageBytes = FileUtils.decompressFile(trainee.getImage());
        if (imageBytes.length == 0) {
            throw new ResourceNotFoundException("Image data is not available");
        }
        return imageBytes;
    }

    @Transactional
    public List<PreServiceCourseResponse> addCoursesToTrainee(Long tenantId,
                                                        Long traineeId,
                                                        Set<Long> courseIds) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceTrainee preServiceTrainee = preServiceTraineeRepository.findById(traineeId)
                .filter(trainee -> trainee.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pre Service Trainee not found with id '" + traineeId + "' in the specified tenant"));
        if (courseIds != null && !courseIds.isEmpty()) {
            Set<PreServiceCourse> preServiceCourses = new HashSet<>();
            for (Long courseId : courseIds) {
                PreServiceCourse course = preServiceCourseRepository.findById(courseId)
                        .filter(c -> c.getTenantId().equals(tenant.getId()))
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Pre Service Course not found with id '" + courseId + "' in the specified tenant"));
                preServiceCourses.add(course);
            }
            preServiceTrainee.setPreServiceCourses(preServiceCourses);
        }
        preServiceTrainee = preServiceTraineeRepository.save(preServiceTrainee);
        Set<PreServiceCourse> traineeCourses = preServiceTrainee.getPreServiceCourses();
        return traineeCourses.stream()
                .map(preServiceCourseMapper::mapToDto)
                .toList();
    }

    @Transactional
    public PreServiceTraineeResponse updatePreServiceTrainee(Long tenantId,
                                                             Long traineeId,
                                                             PreServiceTraineeRequest request,
                                                             MultipartFile file) throws IOException {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        BudgetYearDto budgetYear = leaveServiceClient
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        LocationDto location = tenantServiceClient
                .getLocationById(request.getLocationId(), tenant.getId());
        EducationLevelDto educationLevel = tenantServiceClient
                .getEducationLevelById(request.getEducationLevelId(), tenant.getId());
        FieldOfStudyDto fieldOfStudy = tenantServiceClient
                .getFieldOfStudyById(request.getFieldOfStudyId(), tenant.getId());
        // for documents been checked
        Set<CheckedDocument> checkedDocuments = new HashSet<>();
        for (Long documentId : request.getDocumentIds()) {
            CheckedDocument checkedDocument = checkedDocumentRepository.findById(documentId)
                    .filter(doc -> doc.getTenantId().equals(tenant.getId()))
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Document not found with id '" + documentId + "' in the specified tenant"));
            checkedDocuments.add(checkedDocument);
        }
        PreServiceTrainee preServiceTrainee = preServiceTraineeRepository.findById(traineeId)
                .filter(trainee -> trainee.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pre Service Trainee not found with id '" + traineeId + "' in the specified tenant"));
        preServiceTrainee = preServiceTraineeMapper.updateEntity(preServiceTrainee, request, file);
        if (request.getLocationId() != null) {
            preServiceTrainee.setLocationId(location.getId());
        }
        if (request.getBudgetYearId() != null) {
            preServiceTrainee.setBudgetYearId(budgetYear.getId());
        }
        preServiceTrainee.setBudgetYearId(request.getBudgetYearId());
        if (request.getEducationLevelId() != null) {
            preServiceTrainee.setEducationLevelId(educationLevel.getId());
        }
        if (request.getFieldOfStudyId() != null) {
            preServiceTrainee.setFieldOfStudyId(fieldOfStudy.getId());
        }
        if (request.getDocumentIds() != null) {
            preServiceTrainee.setCheckedDocuments(checkedDocuments);
        }
        preServiceTrainee = preServiceTraineeRepository.save(preServiceTrainee);
        return preServiceTraineeMapper.mapToDto(preServiceTrainee);
    }

    @Transactional
    public void deletePreServiceTrainee(Long tenantId,
                                        Long traineeId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceTrainee preServiceTrainee = preServiceTraineeRepository.findById(traineeId)
                .filter(trainee -> trainee.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pre Service Trainee not found with id '" + traineeId + "' in the specified tenant"));
        Set<CheckedDocument> checkedDocuments = preServiceTrainee.getCheckedDocuments();
        preServiceTrainee.getCheckedDocuments().removeAll(checkedDocuments);
        preServiceTraineeRepository.delete(preServiceTrainee);
    }
}
