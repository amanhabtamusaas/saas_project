package com.training_service.mapper;

import com.training_service.dto.clientDto.*;
import com.training_service.dto.request.PreServiceTraineeRequest;
import com.training_service.dto.response.CheckedDocumentResponse;
import com.training_service.dto.response.PreServiceTraineeResponse;
import com.training_service.model.CheckedDocument;
import com.training_service.model.PreServiceTrainee;
import com.training_service.enums.Gender;
import com.training_service.utility.FileUtil;
import com.training_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PreServiceTraineeMapper {

    private final CheckedDocumentMapper checkedDocumentMapper;
    private final ValidationUtil validationUtil;

    public PreServiceTrainee mapToEntity(TenantDto tenant,
                                         PreServiceTraineeRequest request,
                                         MultipartFile file) throws IOException {

        BudgetYearDto budgetYear = validationUtil
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        LocationDto location = validationUtil
                .getLocationById(tenant.getId(), request.getLocationId());
        EducationLevelDto educationLevel = validationUtil
                .getEducationLevelById(tenant.getId(), request.getEducationLevelId());
        FieldOfStudyDto fieldOfStudy = validationUtil
                .getFieldOfStudyById(tenant.getId(), request.getFieldOfStudyId());

        PreServiceTrainee preServiceTrainee = new PreServiceTrainee();
        preServiceTrainee.setTenantId(tenant.getId());
        preServiceTrainee.setBudgetYearId(budgetYear.getId());
        preServiceTrainee.setLocationId(location.getId());
        preServiceTrainee.setEducationLevelId(educationLevel.getId());
        preServiceTrainee.setFieldOfStudyId(fieldOfStudy.getId());
        preServiceTrainee.setBatchCode(request.getBatchCode());
        preServiceTrainee.setFirstName(request.getFirstName());
        preServiceTrainee.setMiddleName(request.getMiddleName());
        preServiceTrainee.setLastName(request.getLastName());
        preServiceTrainee.setAmharicFirstName(request.getAmharicFirstName());
        preServiceTrainee.setAmharicMiddleName(request.getAmharicMiddleName());
        preServiceTrainee.setAmharicLastName(request.getAmharicLastName());
        preServiceTrainee.setTraineeId(request.getTraineeId());
        preServiceTrainee.setTelephoneNumber(request.getTelephoneNumber());
        preServiceTrainee.setMobileNumber(request.getMobileNumber());
        preServiceTrainee.setGender(Gender.valueOf(request.getGender().toUpperCase()));
        preServiceTrainee.setRemark(request.getRemark());

        Set<CheckedDocument> checkedDocuments = new HashSet<>();
        for (UUID documentId : request.getDocumentIds()) {
            CheckedDocument checkedDocument = validationUtil
                    .getCheckedDocumentById(tenant.getId(), documentId);
            checkedDocuments.add(checkedDocument);

        }
        preServiceTrainee.setCheckedDocuments(checkedDocuments);

        if (file != null) {
            preServiceTrainee.setImageName (file.getOriginalFilename ());
            preServiceTrainee.setImageType(file.getContentType ());
            preServiceTrainee.setImage (FileUtil.compressFile (file.getBytes ()));
        }

        return preServiceTrainee;
    }

    public PreServiceTraineeResponse mapToDto(PreServiceTrainee preServiceTrainee) {

        PreServiceTraineeResponse response = new PreServiceTraineeResponse();
        response.setId(preServiceTrainee.getId());
        response.setBudgetYearId(preServiceTrainee.getBudgetYearId());
        response.setBatchCode(preServiceTrainee.getBatchCode());
        response.setTraineeId(preServiceTrainee.getTraineeId());
        response.setFirstName(preServiceTrainee.getFirstName());
        response.setMiddleName(preServiceTrainee.getMiddleName());
        response.setLastName(preServiceTrainee.getLastName());
        response.setAmharicFirstName(preServiceTrainee.getAmharicFirstName());
        response.setAmharicMiddleName(preServiceTrainee.getAmharicMiddleName());
        response.setAmharicLastName(preServiceTrainee.getAmharicLastName());
        response.setLocationId(preServiceTrainee.getLocationId());
        response.setTelephoneNumber(preServiceTrainee.getTelephoneNumber());
        response.setMobileNumber(preServiceTrainee.getMobileNumber());
        response.setRemark(preServiceTrainee.getRemark());
        response.setEducationLevelId(preServiceTrainee.getEducationLevelId());
        response.setFieldOfStudyId(preServiceTrainee.getFieldOfStudyId());
        response.setGender(preServiceTrainee.getGender().getGender());
        response.setImageName(preServiceTrainee.getImageName());
        response.setImageType(preServiceTrainee.getImageType());
        response.setImage(preServiceTrainee.getImage());
        response.setTenantId(preServiceTrainee.getTenantId());
        response.setCreatedAt(preServiceTrainee.getCreatedAt());
        response.setUpdatedAt(preServiceTrainee.getUpdatedAt());
        response.setCreatedBy(preServiceTrainee.getCreatedBy());
        response.setUpdatedBy(preServiceTrainee.getUpdatedBy());

        if (preServiceTrainee.getCheckedDocuments() != null) {
            List<CheckedDocumentResponse> checkedDocuments = preServiceTrainee
                    .getCheckedDocuments().stream()
                    .map(checkedDocumentMapper::mapToDto)
                    .toList();
            response.setCheckedDocuments(checkedDocuments);
        }

        return response;
    }

    public PreServiceTrainee updateEntity(TenantDto tenant,
                                          PreServiceTrainee preServiceTrainee,
                                          PreServiceTraineeRequest request,
                                          MultipartFile file) throws IOException {

        BudgetYearDto budgetYear = validationUtil
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        LocationDto location = validationUtil
                .getLocationById(tenant.getId(), request.getLocationId());
        EducationLevelDto educationLevel = validationUtil
                .getEducationLevelById(tenant.getId(), request.getEducationLevelId());
        FieldOfStudyDto fieldOfStudy = validationUtil
                .getFieldOfStudyById(tenant.getId(), request.getFieldOfStudyId());

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
        if (request.getBatchCode() != null) {
            preServiceTrainee.setBatchCode(request.getBatchCode());
        }
        if (request.getFirstName() != null) {
            preServiceTrainee.setFirstName(request.getFirstName());
        }
        if (request.getMiddleName() != null) {
            preServiceTrainee.setMiddleName(request.getMiddleName());
        }
        if (request.getLastName() != null) {
            preServiceTrainee.setLastName(request.getLastName());
        }
        if (request.getAmharicFirstName() != null) {
            preServiceTrainee.setAmharicFirstName(request.getAmharicFirstName());
        }
        if (request.getAmharicMiddleName() != null) {
            preServiceTrainee.setAmharicMiddleName(request.getAmharicMiddleName());
        }
        if (request.getAmharicLastName() != null) {
            preServiceTrainee.setAmharicLastName(request.getAmharicLastName());
        }
        if (request.getTraineeId() != null) {
            preServiceTrainee.setTraineeId(request.getTraineeId());
        }
        if (request.getTelephoneNumber() != null) {
            preServiceTrainee.setTelephoneNumber(request.getTelephoneNumber());
        }
        if (request.getMobileNumber() != null) {
            preServiceTrainee.setMobileNumber(request.getMobileNumber());
        }
        if (request.getGender() != null) {
            preServiceTrainee.setGender(Gender.valueOf(request.getGender().toUpperCase()));
        }
        if (request.getRemark() != null) {
            preServiceTrainee.setRemark(request.getRemark());
        }

        Set<CheckedDocument> checkedDocuments = new HashSet<>();
        for (UUID documentId : request.getDocumentIds()) {
            CheckedDocument checkedDocument = validationUtil
                    .getCheckedDocumentById(tenant.getId(), documentId);
            checkedDocuments.add(checkedDocument);

        }
        if (request.getDocumentIds() != null) {
            preServiceTrainee.setCheckedDocuments(checkedDocuments);
        }

        if (file != null && !file.isEmpty()) {
            preServiceTrainee.setImageName (file.getOriginalFilename ());
            preServiceTrainee.setImageType(file.getContentType ());
            preServiceTrainee.setImage (FileUtil.compressFile (file.getBytes ()));
        }

        return preServiceTrainee;
    }
}
