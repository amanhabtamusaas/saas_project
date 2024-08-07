package com.insa.mapper;

import com.insa.dto.request.PreServiceTraineeRequest;
import com.insa.dto.response.CheckedDocumentResponse;
import com.insa.dto.response.PreServiceTraineeResponse;
import com.insa.entity.PreServiceTrainee;
import com.insa.enums.Gender;
import com.insa.utility.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PreServiceTraineeMapper {

    private final CheckedDocumentMapper checkedDocumentMapper;

    public PreServiceTrainee mapToEntity(PreServiceTraineeRequest request,
                                         MultipartFile file) throws IOException {

        PreServiceTrainee preServiceTrainee = new PreServiceTrainee();
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

        if (file != null) {
            preServiceTrainee.setImageName (file.getOriginalFilename ());
            preServiceTrainee.setImageType(file.getContentType ());
            preServiceTrainee.setImage (FileUtils.compressFile (file.getBytes ()));
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

    public PreServiceTrainee updateEntity(PreServiceTrainee preServiceTrainee,
                                          PreServiceTraineeRequest request,
                                          MultipartFile file) throws IOException {
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

        if (file != null && !file.isEmpty()) {
            preServiceTrainee.setImageName (file.getOriginalFilename ());
            preServiceTrainee.setImageType(file.getContentType ());
            preServiceTrainee.setImage (FileUtils.compressFile (file.getBytes ()));
        }

        return preServiceTrainee;
    }
}
