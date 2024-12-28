package com.recruitment_service.utility;

import com.recruitment_service.dto.clientDto.*;
import com.recruitment_service.exception.ResourceNotFoundException;
import com.recruitment_service.model.Applicant;
import com.recruitment_service.model.MediaType;
import com.recruitment_service.model.Recruitment;
import com.recruitment_service.repository.ApplicantRepository;
import com.recruitment_service.repository.MediaTypeRepository;
import com.recruitment_service.repository.RecruitmentRepository;
import com.recruitment_service.client.EmployeeServiceClient;
import com.recruitment_service.client.OrganizationServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ValidationUtil {

    private final OrganizationServiceClient organizationServiceClient;
    private final EmployeeServiceClient employeeServiceClient;
    private final RecruitmentRepository recruitmentRepository;
    private final MediaTypeRepository mediaTypeRepository;
    private final ApplicantRepository applicantRepository;

    public TenantDto getTenantById(UUID tenantId) {

        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenant == null) {
            throw new ResourceNotFoundException("Tenant not found with id '" + tenantId + "'");
        }
        return tenant;
    }

    public EmployeeDto getEmployeeByEmployeeId(UUID tenantId, String employeeId) {

        EmployeeDto employee = employeeServiceClient.getEmployeeByEmployeeId(tenantId, employeeId);
        if (employee == null) {
            throw new ResourceNotFoundException("Employee not found with employee id '" + employeeId + "'");
        }
        return employee;
    }

    public LanguageNameDto getLanguageNameById(UUID tenantId, UUID languageId) {

        LanguageNameDto languageName = employeeServiceClient.getLanguageName(tenantId, languageId);
        if (languageName == null) {
            throw new ResourceNotFoundException("Language name not found with id '" + languageId + "'");
        }
        return languageName;
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

    public Recruitment getRecruitmentById(UUID tenantId, UUID recruitmentId) {

        return recruitmentRepository.findById(recruitmentId)
                .filter(rec -> rec.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with id '" + recruitmentId + "'"));
    }

    public MediaType getMediaTypeById(UUID tenantId, UUID mediaTypeId) {

        return mediaTypeRepository.findById(mediaTypeId)
                .filter(mt -> mt.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Media type not found with id '" + mediaTypeId + "'"));
    }

    public Recruitment getRecruitmentByVacancyNumber(UUID tenantId, String vacancyNumber) {

        return recruitmentRepository.findByVacancyNumber(vacancyNumber)
                .filter(adv -> adv.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with vacancy number '" + vacancyNumber + "'"));
    }

    public Applicant getApplicantById(UUID tenantId, UUID applicantId) {

        return applicantRepository.findById(applicantId)
                .filter(app -> app.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "'"));
    }
}
