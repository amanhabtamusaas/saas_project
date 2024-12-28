package com.employee_service.service;

import com.employee_service.dto.clientDto.TenantDto;
import com.employee_service.dto.request.ExperienceRequest;
import com.employee_service.dto.response.ExperienceResponse;
import com.employee_service.model.Employee;
import com.employee_service.model.Experience;
import com.employee_service.exception.ResourceNotFoundException;
import com.employee_service.mapper.ExperienceMapper;
import com.employee_service.repository.ExperienceRepository;
import com.employee_service.utility.FileUtil;
import com.employee_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
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

    public ExperienceResponse addExperience(UUID tenantId,
                                            UUID employeeId,
                                            ExperienceRequest request,
                                            MultipartFile file) throws IOException {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Experience experience = experienceMapper.mapToEntity(tenant, employee, request, file);
        experience = experienceRepository.save(experience);
        return experienceMapper.mapToDto(experience);
    }

    public List<ExperienceResponse> getAllExperiences(UUID tenantId,
                                                      UUID employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        List<Experience> experiences = experienceRepository.findByEmployeeId(employee.getId());
        return experiences.stream()
                .filter(exp -> exp.getTenantId().equals(tenant.getId()))
                .map(experienceMapper::mapToDto)
                .toList();
    }

    public List<ExperienceResponse> getEmployeeExperiences(UUID tenantId,
                                                           String employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeByEmployeeId(tenant.getId(), employeeId);
        List<Experience> experiences = experienceRepository.findByEmployeeId(employee.getId());
        return experiences.stream()
                .filter(exp -> exp.getTenantId().equals(tenant.getId()))
                .map(experienceMapper::mapToDto)
                .toList();
    }

    public ExperienceResponse getExperienceById(UUID tenantId,
                                                UUID employeeId,
                                                UUID experienceId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Experience experience = getExperienceById(tenant.getId(), employee, experienceId);
        return experienceMapper.mapToDto(experience);
    }

    public byte[] getExperienceDocumentById(UUID tenantId,
                                            UUID employeeId,
                                            UUID experienceId,
                                            MediaType mediaType) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Experience experience = getExperienceById(tenant.getId(), employee, experienceId);
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

    public ExperienceResponse updateExperience(UUID tenantId,
                                               UUID employeeId,
                                               UUID experienceId,
                                               ExperienceRequest request,
                                               MultipartFile file) throws IOException {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Experience experience = getExperienceById(tenant.getId(), employee, experienceId);
        experience = experienceMapper.updateExperience(experience, request, file);
        experience = experienceRepository.save(experience);
        return experienceMapper.mapToDto(experience);
    }

    public void deleteExperience(UUID tenantId,
                                 UUID employeeId,
                                 UUID experienceId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Experience experience = getExperienceById(tenant.getId(), employee, experienceId);
        experienceRepository.delete(experience);
    }

    private Experience getExperienceById(UUID tenantId, Employee employee, UUID experienceId) {

        return experienceRepository.findById(experienceId)
                .filter(exp -> exp.getTenantId().equals(tenantId))
                .filter(exp -> exp.getEmployee().equals(employee))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Experience not found with id '" + experienceId + "'"));
    }
}