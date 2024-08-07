package com.insa.service;

import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.ExperienceRequest;
import com.insa.dto.response.ExperienceResponse;
import com.insa.entity.Applicant;
import com.insa.entity.Experience;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.ExperienceMapper;
import com.insa.repository.ApplicantRepository;
import com.insa.repository.ExperienceRepository;
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
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ApplicantRepository applicantRepository;
    private final TenantServiceClient tenantServiceClient;
    private final ExperienceMapper experienceMapper;

    @Transactional
    public ExperienceResponse addExperience(Long tenantId,
                                            Long applicantId,
                                            ExperienceRequest request,
                                            MultipartFile file) throws IOException {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Experience experience = experienceMapper.mapToEntity (request, file);
        experience.setTenantId (tenant.getId ());
        experience.setApplicant (applicant);
        experience = experienceRepository.save (experience);
        return experienceMapper.mapToDto (experience);
    }

    public List<ExperienceResponse> getAllExperiences(Long tenantId,
                                                      Long applicantId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        List<Experience> experiences = experienceRepository.findAll ();
        return experiences.stream ()
                .filter (exp -> exp.getTenantId ().equals (tenant.getId ()))
                .filter (exp -> exp.getApplicant ().equals (applicant))
                .map (experienceMapper::mapToDto)
                .toList();
    }

    public ExperienceResponse getExperienceById(Long tenantId,
                                                Long applicantId,
                                                Long experienceId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Experience experience = experienceRepository.findById (experienceId)
                .filter (exp -> exp.getTenantId ().equals (tenant.getId ()))
                .filter (exp -> exp.getApplicant ().equals (applicant))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Experience not found with id '" + experienceId + "' in the specified applicant"));
        return experienceMapper.mapToDto (experience);
    }

    public byte[] getExperienceDocumentById(Long tenantId,
                                            Long applicantId,
                                            Long experienceId,
                                            MediaType mediaType) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Experience experience = experienceRepository.findById (experienceId)
                .filter (exp -> exp.getTenantId ().equals (tenant.getId ()))
                .filter (exe -> exe.getApplicant ().equals (applicant))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Experience not found with id '" + experienceId + "' in the specified applicant"));

        switch (experience.getFileType()) {
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
        return FileUtils.decompressFile (experience.getFileBytes ());
    }

    @Transactional
    public ExperienceResponse updateExperience(Long tenantId,
                                               Long applicantId,
                                               Long experienceId,
                                               ExperienceRequest request,
                                               MultipartFile file) throws IOException {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Experience experience = experienceRepository.findById (experienceId)
                .filter (exp -> exp.getTenantId ().equals (tenant.getId ()))
                .filter (exp -> exp.getApplicant ().equals (applicant))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Experience not found with id '" + experienceId + "' in the specified applicant"));
        experience = experienceMapper.updateExperience (experience, request, file);
        experience = experienceRepository.save (experience);
        return experienceMapper.mapToDto (experience);
    }

    @Transactional
    public void deleteExperience(Long tenantId,
                                 Long applicantId,
                                 Long experienceId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Experience experience = experienceRepository.findById (experienceId)
                .filter (exp -> exp.getTenantId ().equals (tenant.getId ()))
                .filter (exp -> exp.getApplicant ().equals (applicant))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Experience not found with id '" + experienceId + "' in the specified applicant"));
        experienceRepository.delete (experience);
    }
}
