package com.recruitment_service.service;

import com.recruitment_service.dto.clientDto.TenantDto;
import com.recruitment_service.dto.request.EducationRequest;
import com.recruitment_service.dto.response.EducationResponse;
import com.recruitment_service.model.Applicant;
import com.recruitment_service.model.Education;
import com.recruitment_service.exception.ResourceNotFoundException;
import com.recruitment_service.mapper.EducationMapper;
import com.recruitment_service.repository.EducationRepository;
import com.recruitment_service.utility.FileUtil;
import com.recruitment_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final EducationMapper educationMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public EducationResponse addEducation(UUID tenantId,
                                          UUID applicantId,
                                          EducationRequest request,
                                          MultipartFile file) throws IOException {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Education education = educationMapper.mapToEntity(tenant, applicant, request, file);
        education = educationRepository.save(education);
        return educationMapper.mapToDto(education);
    }

    public List<EducationResponse> getAllEducations(UUID tenantId,
                                                    UUID applicantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        List<Education> educations = educationRepository
                .findByTenantIdAndApplicant(tenant.getId(), applicant);
        return educations.stream()
                .map(educationMapper::mapToDto)
                .toList();
    }

    public EducationResponse getEducationById(UUID tenantId,
                                              UUID applicantId,
                                              UUID educationId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Education education = getEducationById(tenant.getId(), applicant, educationId);
        return educationMapper.mapToDto(education);
    }

    public byte[] getEducationDocumentById(UUID tenantId,
                                           UUID applicantId,
                                           UUID educationId,
                                           MediaType mediaType) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Education education = getEducationById(tenant.getId(), applicant, educationId);
        switch (education.getFileType()) {
            case "application/pdf":
                mediaType = MediaType.valueOf(MediaType.APPLICATION_PDF_VALUE);
                break;
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                mediaType = MediaType.valueOf(
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                break;
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
                throw new IllegalArgumentException("Unsupported file type: " + education.getFileType());
        }
        byte[] fileBytes = FileUtil.decompressFile(education.getFileBytes());
        if (fileBytes.length == 0) {
            throw new ResourceNotFoundException("Education file is not available");
        }
        return fileBytes;
    }

    @Transactional
    public EducationResponse updateEducation(UUID tenantId,
                                             UUID applicantId,
                                             UUID educationId,
                                             EducationRequest request,
                                             MultipartFile file) throws IOException {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Education education = getEducationById(tenant.getId(), applicant, educationId);
        education = educationMapper.updateEducation(tenant, education, request, file);
        education = educationRepository.save(education);
        return educationMapper.mapToDto(education);
    }

    @Transactional
    public void deleteEducation(UUID tenantId,
                                UUID applicantId,
                                UUID educationId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Education education = getEducationById(tenant.getId(), applicant, educationId);
        educationRepository.delete(education);
    }

    private Education getEducationById(UUID tenantId, Applicant applicant, UUID educationId) {

        return educationRepository.findById(educationId)
                .filter(edu -> edu.getTenantId().equals(tenantId))
                .filter(edu -> edu.getApplicant().equals(applicant))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Education not found with id '" + educationId + "'"));
    }
}