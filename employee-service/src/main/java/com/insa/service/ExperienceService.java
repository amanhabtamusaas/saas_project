package com.insa.service;

import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.ExperienceRequest;
import com.insa.dto.response.ExperienceResponse;
import com.insa.entity.Employee;
import com.insa.entity.Experience;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.ExperienceMapper;
import com.insa.repository.EmployeeRepository;
import com.insa.repository.ExperienceRepository;
import com.insa.utility.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;
    private final EmployeeRepository employeeRepository;
    private final TenantServiceClient tenantServiceClient;

    public ExperienceResponse addExperience(Long tenantId,
                                            Long employeeId,
                                            ExperienceRequest request,
                                            MultipartFile file) throws IOException {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Experience experience = experienceMapper.mapToEntity (request, file);
        experience.setTenantId (tenant.getId ());
        experience.setEmployee (employee);
        experience = experienceRepository.save (experience);
        return experienceMapper.mapToDto (experience);
    }

    public List<ExperienceResponse> getAllExperiences(Long tenantId,
                                                      Long employeeId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        List<Experience> experiences = experienceRepository.findByEmployeeId(employee.getId());
        return experiences.stream ()
                .filter (exp -> exp.getTenantId ().equals (tenant.getId ()))
                .map (experienceMapper::mapToDto)
                .toList();
    }

    public List<ExperienceResponse> getEmployeeExperiences(Long tenantId,
                                                           String employeeId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        List<Experience> experiences = experienceRepository.findByEmployeeId(employee.getId());
        return experiences.stream ()
                .filter (exp -> exp.getTenantId ().equals (tenant.getId ()))
                .map (experienceMapper::mapToDto)
                .toList();
    }

    public ExperienceResponse getExperienceById(Long tenantId,
                                                Long employeeId,
                                                Long experienceId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Experience experience = experienceRepository.findById (experienceId)
                .filter (exp -> exp.getTenantId ().equals (tenant.getId ()))
                .filter (exp -> exp.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Experience not found with id '" + experienceId + "' in the specified employee"));
        return experienceMapper.mapToDto (experience);
    }

    public byte[] getExperienceDocumentById(Long tenantId,
                                            Long employeeId,
                                            Long experienceId,
                                            MediaType mediaType) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Experience experience = experienceRepository.findById (experienceId)
                .filter (exp -> exp.getTenantId ().equals (tenant.getId ()))
                .filter (exe -> exe.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Experience not found with id '" + experienceId + "' in the specified employee"));

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

    public ExperienceResponse updateExperience(Long tenantId,
                                               Long employeeId,
                                               Long experienceId,
                                               ExperienceRequest request,
                                               MultipartFile file) throws IOException {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Experience experience = experienceRepository.findById (experienceId)
                .filter (exp -> exp.getTenantId ().equals (tenant.getId ()))
                .filter (exp -> exp.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "experience not found with id '" + experienceId + "' in the specified employee"));
        experience = experienceMapper.updateExperience (experience, request, file);
        experience = experienceRepository.save (experience);
        return experienceMapper.mapToDto (experience);
    }

    public void deleteExperience(Long tenantId,
                                 Long employeeId,
                                 Long experienceId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Experience experience = experienceRepository.findById (experienceId)
                .filter (exp -> exp.getTenantId ().equals (tenant.getId ()))
                .filter (exp -> exp.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Experience not found with id '" + experienceId + "' in the specified employee"));
        experienceRepository.delete (experience);
    }
}
