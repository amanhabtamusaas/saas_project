package com.employee_service.utility;

import com.employee_service.dto.clientDto.*;
import com.employee_service.exception.ResourceNotFoundException;
import com.employee_service.model.Employee;
import com.employee_service.model.LanguageName;
import com.employee_service.model.TitleName;
import com.employee_service.repository.EmployeeRepository;
import com.employee_service.repository.LanguageNameRepository;
import com.employee_service.repository.TitleNameRepository;
import com.employee_service.client.OrganizationServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ValidationUtil {

    private final OrganizationServiceClient organizationServiceClient;
    private final EmployeeRepository employeeRepository;
    private final TitleNameRepository titleNameRepository;
    private final LanguageNameRepository languageNameRepository;

    public TenantDto getTenantById(UUID tenantId) {

        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenant == null) {
            throw new ResourceNotFoundException(
                    "Tenant not found with id '" + tenantId + "'");
        }
        return tenant;
    }

    public Employee getEmployeeById(UUID tenantId,
                                    UUID employeeId) {

        return employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "'"));

    }

    public Employee getEmployeeByEmployeeId(UUID tenantId,
                                            String employeeId) {

        return employeeRepository.findByEmployeeId(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with employee id '" + employeeId + "'"));
    }

    public LocationDto getLocationById(UUID tenantId,
                                       UUID locationId) {

        LocationDto location = organizationServiceClient
                .getLocationById(locationId, tenantId);
        if (location == null) {
            throw new ResourceNotFoundException(
                    "Location not found with id '" + locationId + "'");
        }
        return location;
    }

    public EducationLevelDto getEducationLevelById(UUID tenantId,
                                                   UUID educationLevelId) {

        EducationLevelDto educationLevel = organizationServiceClient
                .getEducationLevelById(educationLevelId, tenantId);
        if (educationLevel == null) {
            throw new ResourceNotFoundException(
                    "Education level not found with id '" + educationLevelId + "'");
        }
        return educationLevel;
    }

    public FieldOfStudyDto getFieldOfStudyById(UUID tenantId, UUID fieldOfStudyId) {

        FieldOfStudyDto fieldOfStudy = organizationServiceClient
                .getFieldOfStudyById(fieldOfStudyId, tenantId);
        if (fieldOfStudy == null) {
            throw new ResourceNotFoundException(
                    "Field of study not found with id '" + fieldOfStudyId + "'");
        }
        return fieldOfStudy;
    }

    public DepartmentDto getDepartmentById(UUID tenantId, UUID departmentId) {

        DepartmentDto department = organizationServiceClient
                .getDepartmentById(departmentId, tenantId);
        if (department == null) {
            throw new ResourceNotFoundException(
                    "Department not found with id '" + departmentId + "'");
        }
        return department;
    }

    public JobDto getJobById(UUID tenantId, UUID jobId) {

        JobDto job = organizationServiceClient
                .getJobById(jobId, tenantId);
        if (job == null) {
            throw new ResourceNotFoundException(
                    "Job not found with id '" + jobId + "'");
        }
        return job;
    }

    public PayGradeDto getPayGradeById(UUID tenantId, UUID payGradeId) {

        PayGradeDto payGrade = organizationServiceClient
                .getPayGradeById(payGradeId, tenantId);
        if (payGrade == null) {
            throw new ResourceNotFoundException(
                    "Pay-grade not found with id '" + payGradeId + "'");
        }
        return payGrade;
    }

    public TitleName getTitleNameById(UUID tenantId, UUID titleNameId) {

        return titleNameRepository.findById(titleNameId)
                .filter(title -> title.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Title name not found with id '" + titleNameId + "'"));
    }

    public LanguageName getLanguageNameById(UUID tenantId, UUID languageId) {

        return languageNameRepository.findById(languageId)
                .filter(language -> language.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Language not found with id '" + languageId + "'"));
    }
}
