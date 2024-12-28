package com.recruitment_service.mapper;

import com.recruitment_service.dto.clientDto.EducationLevelDto;
import com.recruitment_service.dto.clientDto.FieldOfStudyDto;
import com.recruitment_service.dto.clientDto.TenantDto;
import com.recruitment_service.dto.request.EducationRequest;
import com.recruitment_service.dto.response.EducationResponse;
import com.recruitment_service.model.Applicant;
import com.recruitment_service.model.Education;
import com.recruitment_service.enums.EducationType;
import com.recruitment_service.utility.FileUtil;
import com.recruitment_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class EducationMapper {

    private final ValidationUtil validationUtil;

    public Education mapToEntity(TenantDto tenant,
                                 Applicant applicant,
                                 EducationRequest request,
                                 MultipartFile file) throws IOException {

        FieldOfStudyDto fieldOfStudy = validationUtil
                .getFieldOfStudyById(request.getFieldOfStudyId(), tenant.getId());
        EducationLevelDto educationLevel = validationUtil
                .getEducationLevelById(request.getEducationLevelId(), tenant.getId());

        Education education = new Education ();
        education.setTenantId(tenant.getId());
        education.setApplicant(applicant);
        education.setEducationLevelId(educationLevel.getId());
        education.setFieldOfStudyId(fieldOfStudy.getId());
        education.setEducationType (EducationType.valueOf(
                request.getEducationType ().toUpperCase()));
        education.setInstitution (request.getInstitution ());
        education.setStartDate (request.getStartDate ());
        education.setEndDate (request.getEndDate ());
        education.setResult (request.getResult ());

        if (file != null){
            education.setFileName (file.getOriginalFilename ());
            education.setFileType (file.getContentType ());
            education.setFileBytes (FileUtil.compressFile (file.getBytes ()));
        }

        return education;
    }

    public EducationResponse mapToDto(Education education) {

        EducationResponse response = new EducationResponse ();
        response.setId (education.getId ());
        response.setApplicantId(education.getApplicant().getId());
        response.setEducationLevelId (education.getEducationLevelId());
        response.setEducationType (education.getEducationType ().getEducationType());
        response.setFieldOfStudyId (education.getFieldOfStudyId ());
        response.setInstitution (education.getInstitution ());
        response.setStartDate (education.getStartDate ());
        response.setEndDate (education.getEndDate ());
        response.setResult (education.getResult ());
        response.setFileName (education.getFileName ());
        response.setFileType (education.getFileType ());
        response.setFileBytes (education.getFileBytes ());
        response.setTenantId (education.getTenantId ());
        response.setCreatedAt (education.getCreatedAt ());
        response.setUpdatedAt (education.getUpdatedAt ());
        response.setCreatedBy (education.getCreatedBy ());
        response.setUpdatedBy (education.getUpdatedBy ());

        return response;
    }

    public Education updateEducation(TenantDto tenant,
                                     Education education,
                                     EducationRequest request,
                                     MultipartFile file) throws IOException {

        FieldOfStudyDto fieldOfStudy = validationUtil
                .getFieldOfStudyById(request.getFieldOfStudyId(), tenant.getId());
        EducationLevelDto educationLevel = validationUtil
                .getEducationLevelById(request.getEducationLevelId(), tenant.getId());

        if (request.getEducationLevelId() != null) {
            education.setEducationLevelId(educationLevel.getId());
        }
        if (request.getFieldOfStudyId() != null) {
            education.setFieldOfStudyId(fieldOfStudy.getId());
        }
        if (request.getEducationType () != null) {
            education.setEducationType(EducationType.valueOf(
                    request.getEducationType().toUpperCase()));
        }
        if (request.getInstitution () != null) {
            education.setInstitution(request.getInstitution());
        }
        if (request.getStartDate () != null) {
            education.setStartDate(request.getStartDate());
        }
        if (request.getEndDate () != null) {
            education.setEndDate(request.getEndDate());
        }
        if (request.getResult () != null) {
            education.setResult(request.getResult());
        }

        if (file != null && !file.isEmpty ()) {
            education.setFileName (file.getOriginalFilename ());
            education.setFileType (file.getContentType ());
            education.setFileBytes (FileUtil.compressFile (file.getBytes ()));
        }

        return education;
    }
}
