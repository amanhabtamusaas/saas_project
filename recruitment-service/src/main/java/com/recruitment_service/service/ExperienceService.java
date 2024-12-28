package com.recruitment_service.service;

import com.recruitment_service.dto.clientDto.TenantDto;
import com.recruitment_service.dto.request.ExperienceRequest;
import com.recruitment_service.dto.response.ExperienceResponse;
import com.recruitment_service.model.Applicant;
import com.recruitment_service.model.Experience;
import com.recruitment_service.exception.ResourceNotFoundException;
import com.recruitment_service.mapper.ExperienceMapper;
import com.recruitment_service.repository.ExperienceRepository;
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
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public ExperienceResponse addExperience(UUID tenantId,
                                            UUID applicantId,
                                            ExperienceRequest request,
                                            MultipartFile file) throws IOException {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Experience experience = experienceMapper.mapToEntity(tenant, applicant, request, file);
        experience = experienceRepository.save(experience);
        return experienceMapper.mapToDto(experience);
    }

    public List<ExperienceResponse> getAllExperiences(UUID tenantId,
                                                      UUID applicantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        List<Experience> experiences = experienceRepository
                .findByTenantIdAndApplicant(tenant.getId(), applicant);
        return experiences.stream()
                .map(experienceMapper::mapToDto)
                .toList();
    }

    public ExperienceResponse getExperienceById(UUID tenantId,
                                                UUID applicantId,
                                                UUID experienceId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Experience experience = getExperienceById(tenant.getId(), applicant, experienceId);
        return experienceMapper.mapToDto(experience);
    }

    public byte[] getExperienceDocumentById(UUID tenantId,
                                            UUID applicantId,
                                            UUID experienceId,
                                            MediaType mediaType) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Experience experience = getExperienceById(tenant.getId(), applicant, experienceId);
        switch (experience.getFileType()) {
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
                throw new IllegalArgumentException("Unsupported file type: " + experience.getFileType());
        }
        byte[] fileBytes = FileUtil.decompressFile(experience.getFileBytes());
        if (fileBytes.length == 0) {
            throw new ResourceNotFoundException("Experience file is not available");
        }
        return fileBytes;
    }

    @Transactional
    public ExperienceResponse updateExperience(UUID tenantId,
                                               UUID applicantId,
                                               UUID experienceId,
                                               ExperienceRequest request,
                                               MultipartFile file) throws IOException {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Experience experience = getExperienceById(tenant.getId(), applicant, experienceId);
        experience = experienceMapper.updateExperience(experience, request, file);
        experience = experienceRepository.save(experience);
        return experienceMapper.mapToDto(experience);
    }

    @Transactional
    public void deleteExperience(UUID tenantId,
                                 UUID applicantId,
                                 UUID experienceId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Experience experience = getExperienceById(tenant.getId(), applicant, experienceId);
        experienceRepository.delete(experience);
    }

    private Experience getExperienceById(UUID tenantId, Applicant applicant, UUID experienceId) {

        return experienceRepository.findById(experienceId)
                .filter(exp -> exp.getTenantId().equals(tenantId))
                .filter(exp -> exp.getApplicant().equals(applicant))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Experience not found with id '" + experienceId + "'"));
    }
}