package com.insa.service;

import com.insa.dto.apiDto.EducationLevelDto;
import com.insa.dto.apiDto.FieldOfStudyDto;
import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.EducationRequest;
import com.insa.dto.response.EducationResponse;
import com.insa.entity.Applicant;
import com.insa.entity.Education;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.EducationMapper;
import com.insa.repository.ApplicantRepository;
import com.insa.repository.EducationRepository;
import com.insa.utility.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final ApplicantRepository applicantRepository;
    private final TenantServiceClient tenantServiceClient;
    private final EducationMapper educationMapper;

    @Transactional
    public EducationResponse addEducation(Long tenantId,
                                          Long applicantId,
                                          EducationRequest request,
                                          MultipartFile file) throws IOException {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        FieldOfStudyDto fieldOfStudy = tenantServiceClient
                .getFieldOfStudyById(request.getFieldOfStudyId(), tenant.getId());
        EducationLevelDto educationLevel = tenantServiceClient
                .getEducationLevelById(request.getEducationLevelId(), tenant.getId());
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(app -> app.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Education education = educationMapper.mapToEntity (request, file);
        education.setTenantId (tenant.getId ());
        education.setEducationLevelId(educationLevel.getId());
        education.setFieldOfStudyId(fieldOfStudy.getId());
        education.setApplicant (applicant);
        education = educationRepository.save (education);
        return educationMapper.mapToDto (education);
    }

    public List<EducationResponse> getAllEducations(Long tenantId,
                                                    Long applicantId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(app -> app.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        List<Education> educations = educationRepository.findAll ();
        return educations.stream ()
                .filter (edu -> edu.getTenantId ().equals (tenant.getId ()))
                .filter (edu -> edu.getApplicant ().equals (applicant))
                .map (educationMapper::mapToDto)
                .toList();
    }

    public EducationResponse getEducationById(Long tenantId,
                                              Long applicantId,
                                              Long educationId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(app -> app.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Education education = educationRepository.findById (educationId)
                .filter (edu -> edu.getTenantId ().equals (tenant.getId ()))
                .filter (edu -> edu.getApplicant ().equals (applicant))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Education not found with id '" + educationId + "' in the specified applicant"));
        return educationMapper.mapToDto (education);
    }

    public byte[] getEducationDocumentById(Long tenantId,
                                           Long applicantId,
                                           Long educationId,
                                           MediaType mediaType) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(app -> app.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Education education = educationRepository.findById (educationId)
                .filter (edu -> edu.getTenantId ().equals (tenant.getId ()))
                .filter (edu -> edu.getApplicant ().equals (applicant))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Education not found with id '" + educationId + "' in the specified applicant"));

        switch (education.getFileType()) {
            case "application/pdf":
                mediaType = MediaType.valueOf (MediaType.APPLICATION_PDF_VALUE);
                break;
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                mediaType = MediaType.valueOf(
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                break;
            case "image/jpeg":
                mediaType = MediaType.valueOf (MediaType.IMAGE_JPEG_VALUE);
                break;
            case "image/png":
                mediaType = MediaType.valueOf (MediaType.IMAGE_PNG_VALUE);
                break;
            case "image/gif":
                mediaType = MediaType.valueOf (MediaType.IMAGE_GIF_VALUE);
                break;
            default:
                return "Unsupported file type".getBytes ();
        }
        return FileUtils.decompressFile (education.getFileBytes ());
    }

    @Transactional
    public EducationResponse updateEducation(Long tenantId,
                                             Long applicantId,
                                             Long educationId,
                                             EducationRequest request,
                                             MultipartFile file) throws IOException {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        FieldOfStudyDto fieldOfStudy = tenantServiceClient
                .getFieldOfStudyById(request.getFieldOfStudyId(), tenant.getId());
        EducationLevelDto educationLevel = tenantServiceClient
                .getEducationLevelById(request.getEducationLevelId(), tenant.getId());
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(app -> app.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Education education = educationRepository.findById (educationId)
                .filter (edu -> edu.getTenantId ().equals (tenant.getId ()))
                .filter (edu -> edu.getApplicant ().equals (applicant))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Education not found with id '" + educationId + "' in the specified applicant"));
        education = educationMapper.updateEducation (education, request, file);
        if (request.getFieldOfStudyId() != null) {
            education.setFieldOfStudyId(fieldOfStudy.getId());
        }
        if (request.getEducationLevelId() != null) {
            education.setEducationLevelId(educationLevel.getId());
        }
        education = educationRepository.save (education);
        return educationMapper.mapToDto (education);
    }

    @Transactional
    public void deleteEducation(Long tenantId,
                                Long applicantId,
                                Long educationId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(app -> app.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Education education = educationRepository.findById (educationId)
                .filter (edu -> edu.getTenantId ().equals (tenant.getId ()))
                .filter (edu -> edu.getApplicant ().equals (applicant))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Education not found with id '" + educationId + "' in the specified applicant"));

        educationRepository.delete (education);
    }
}
