package com.insa.service;

import com.insa.dto.apiDto.EducationLevelDto;
import com.insa.dto.apiDto.FieldOfStudyDto;
import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.EducationRequest;
import com.insa.dto.response.EducationResponse;
import com.insa.entity.Education;
import com.insa.entity.Employee;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.EducationMapper;
import com.insa.repository.EducationRepository;
import com.insa.repository.EmployeeRepository;
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
public class EducationService  {

    private final EducationRepository educationRepository;
    private final EducationMapper educationMapper;
    private final EmployeeRepository employeeRepository;
    private final TenantServiceClient tenantServiceClient;

    public EducationResponse addEducation(Long tenantId,
                                          Long employeeId,
                                          EducationRequest request,
                                          MultipartFile file) throws IOException {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        EducationLevelDto educationLevel = tenantServiceClient
                .getEducationLevelById(request.getEducationLevelId(), tenant.getId());
        FieldOfStudyDto fieldOfStudy = tenantServiceClient
                .getFieldOfStudyById(request.getFieldOfStudyId(), tenant.getId());
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Education education = educationMapper.mapToEntity (request, file);
        education.setTenantId (tenant.getId ());
        education.setEmployee (employee);
        education.setEducationLevelId(educationLevel.getId());
        education.setFieldOfStudyId(fieldOfStudy.getId());
        education = educationRepository.save (education);
        return educationMapper.mapToDto (education);
    }

    public List<EducationResponse> getAllEducations(Long tenantId,
                                                    Long employeeId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        List<Education> educations = educationRepository.findByEmployeeId(employee.getId());
        return educations.stream ()
                .filter (edu -> edu.getTenantId ().equals (tenant.getId ()))
                .map (educationMapper::mapToDto)
                .toList();
    }

    public List<EducationResponse> getEmployeeEducations(Long tenantId,
                                                         String employeeId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        List<Education> educations = educationRepository.findByEmployeeId(employee.getId());
        return educations.stream ()
                .filter (edu -> edu.getTenantId ().equals (tenant.getId ()))
                .map (educationMapper::mapToDto)
                .toList();
    }

    public EducationResponse getEducationById(Long tenantId,
                                              Long employeeId,
                                              Long educationId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Education education = educationRepository.findById (educationId)
                .filter (edu -> edu.getTenantId ().equals (tenant.getId ()))
                .filter (edu -> edu.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Education not found with id '" + educationId + "' in the specified employee"));
        return educationMapper.mapToDto (education);
    }

    public byte[] getEducationDocumentById(Long tenantId,
                                           Long employeeId,
                                           Long educationId,
                                           MediaType mediaType) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Education education = educationRepository.findById (educationId)
                .filter (edu -> edu.getTenantId ().equals (tenant.getId ()))
                .filter (edu -> edu.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Education not found with id '" + educationId + "' in the specified employee"));

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

    public EducationResponse updateEducation(Long tenantId,
                                             Long employeeId,
                                             Long educationId,
                                             EducationRequest request,
                                             MultipartFile file) throws IOException {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        EducationLevelDto educationLevel = tenantServiceClient
                .getEducationLevelById(request.getEducationLevelId(), tenant.getId());
        FieldOfStudyDto fieldOfStudy = tenantServiceClient
                .getFieldOfStudyById(request.getFieldOfStudyId(), tenant.getId());
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Education education = educationRepository.findById (educationId)
                .filter (edu -> edu.getTenantId ().equals (tenant.getId ()))
                .filter (edu -> edu.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Education not found with id '" + educationId + "' in the specified employee"));
        if (request.getEducationLevelId() != null) {
            education.setEducationLevelId (educationLevel.getId());
        }
        if (request.getFieldOfStudyId() != null) {
            education.setFieldOfStudyId (fieldOfStudy.getId());
        }
        education = educationMapper.updateEducation (education, request, file);
        education = educationRepository.save (education);
        return educationMapper.mapToDto (education);
    }

    public void deleteEducation(Long tenantId,
                                Long employeeId,
                                Long educationId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Education education = educationRepository.findById (educationId)
                .filter (edu -> edu.getTenantId ().equals (tenant.getId ()))
                .filter (edu -> edu.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Education not found with id '" + educationId + "' in the specified employee"));
        educationRepository.delete (education);
    }
}
