package com.insa.mapper;

import com.insa.dto.request.EducationRequest;
import com.insa.dto.response.EducationResponse;
import com.insa.entity.Education;
import com.insa.enums.EducationType;
import com.insa.utility.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class EducationMapper {

    public Education mapToEntity(EducationRequest request,
                                 MultipartFile file) throws IOException {

        Education education = new Education ();
        education.setEducationType (EducationType.valueOf(
                request.getEducationType ().toUpperCase()));
        education.setInstitution (request.getInstitution ());
        education.setStartDate (request.getStartDate ());
        education.setEndDate (request.getEndDate ());
        education.setResult (request.getResult ());

        if (file != null){
            education.setFileName (file.getOriginalFilename ());
            education.setFileType (file.getContentType ());
            education.setFileBytes (FileUtils.compressFile (file.getBytes ()));
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

    public Education updateEducation(Education education,
                                     EducationRequest request,
                                     MultipartFile file) throws IOException {

        if (request.getEducationType () != null)
            education.setEducationType (EducationType.valueOf(
                    request.getEducationType ().toUpperCase()));
        if (request.getInstitution () != null)
            education.setInstitution (request.getInstitution ());
        if (request.getStartDate () != null)
            education.setStartDate (request.getStartDate ());
        if (request.getEndDate () != null)
            education.setEndDate (request.getEndDate ());
        if (request.getResult () != null)
            education.setResult (request.getResult ());

        if (file != null && !file.isEmpty ()) {
            education.setFileName (file.getOriginalFilename ());
            education.setFileType (file.getContentType ());
            education.setFileBytes (FileUtils.compressFile (file.getBytes ()));
        }

        return education;
    }
}
