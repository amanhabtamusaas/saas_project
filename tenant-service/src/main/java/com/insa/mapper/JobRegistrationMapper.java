package com.insa.mapper;

import com.insa.dto.requestDto.JobRegistrationRequest;
import com.insa.dto.responseDto.JobRegistrationResponse;
import com.insa.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobRegistrationMapper {

    @Autowired
    private TenantMapper tenantMapper; // Assuming you have this mapper already defined

    private void setCommonFields(JobRegistration jobRegistration, JobRegistrationRequest jobRegistrationRequest) {
        if (jobRegistrationRequest.getJobTitle() != null) {
            jobRegistration.setJobTitle(jobRegistrationRequest.getJobTitle());
        }
        if (jobRegistrationRequest.getJobCode() != null) {
            jobRegistration.setJobCode(jobRegistrationRequest.getJobCode());
        }
        if (jobRegistrationRequest.getReportsTo() != null) {
            jobRegistration.setReportsTo(jobRegistrationRequest.getReportsTo());
        }
        if (jobRegistrationRequest.getJobType() != null) {
            jobRegistration.setJobType(jobRegistrationRequest.getJobType());
        }
        if (jobRegistrationRequest.getMinExperience() != null) {
            jobRegistration.setMinExperience(jobRegistrationRequest.getMinExperience());
        }
        if (jobRegistrationRequest.getDuties() != null) {
            jobRegistration.setDuties(jobRegistrationRequest.getDuties());
        }
        if (jobRegistrationRequest.getLanguage() != null) {
            jobRegistration.setLanguage(jobRegistrationRequest.getLanguage());
        }
        if (jobRegistrationRequest.getSkills() != null) {
            jobRegistration.setSkills(jobRegistrationRequest.getSkills());
        }
        if (jobRegistrationRequest.getDescription() != null) {
            jobRegistration.setDescription(jobRegistrationRequest.getDescription());
        }
        if (jobRegistrationRequest.getAlternativeExperience() != null) {
            jobRegistration.setAlternativeExperience(jobRegistrationRequest.getAlternativeExperience());
        }
        if (jobRegistrationRequest.getRelativeExperience() != null) {
            jobRegistration.setRelativeExperience(jobRegistrationRequest.getRelativeExperience());
        }
    }

    public JobRegistration mapToEntity(JobRegistrationRequest jobRegistrationRequest) {
        JobRegistration jobRegistration = new JobRegistration();
        setCommonFields(jobRegistration, jobRegistrationRequest);

        if (jobRegistrationRequest.getQualificationId() != null) {
            Qualification qualification = new Qualification();
            qualification.setId(jobRegistrationRequest.getQualificationId());
            jobRegistration.setQualification(qualification);
        }

        if (jobRegistrationRequest.getWorkUnitId() != null) {
            WorkUnit workUnit = new WorkUnit();
            workUnit.setId(jobRegistrationRequest.getWorkUnitId());
            jobRegistration.setWorkUnit(workUnit);
        }

        if (jobRegistrationRequest.getEducationLevelId() != null) {
            EducationLevel educationLevel = new EducationLevel();
            educationLevel.setId(jobRegistrationRequest.getEducationLevelId());
            jobRegistration.setEducationLevel(educationLevel);
        }

        if (jobRegistrationRequest.getJobGradeId() != null) {
            JobGrade jobGrade = new JobGrade();
            jobGrade.setId(jobRegistrationRequest.getJobGradeId());
            jobRegistration.setJobGrade(jobGrade);
        }

        if (jobRegistrationRequest.getJobCategoryId() != null) {
            JobCategory jobCategory = new JobCategory();
            jobCategory.setId(jobRegistrationRequest.getJobCategoryId());
            jobRegistration.setJobCategory(jobCategory);
        }

        if (jobRegistrationRequest.getDepartmentId() != null) {
            Department department = new Department();
            department.setId(jobRegistrationRequest.getDepartmentId());
            jobRegistration.setDepartment(department);
        }

//        // Set tenant if required
//        if (jobRegistrationRequest.getTenantId() != null) {
//            Tenant tenant = new Tenant();
//            tenant.setId(jobRegistrationRequest.getTenantId());
//            jobRegistration.setTenant(tenant);
//        }

        return jobRegistration;
    }

    public JobRegistrationResponse mapToDto(JobRegistration jobRegistration) {
        JobRegistrationResponse response = new JobRegistrationResponse();
        response.setId(jobRegistration.getId());
        response.setJobTitle(jobRegistration.getJobTitle());
        response.setJobCode(jobRegistration.getJobCode());
        response.setReportsTo(jobRegistration.getReportsTo());
        response.setJobType(jobRegistration.getJobType());
        response.setMinExperience(jobRegistration.getMinExperience());
        response.setDuties(jobRegistration.getDuties());
        response.setLanguage(jobRegistration.getLanguage());
        response.setSkills(jobRegistration.getSkills());
        response.setDescription(jobRegistration.getDescription());
        response.setCreatedAt(jobRegistration.getCreatedAt());
        response.setUpdatedAt(jobRegistration.getUpdatedAt());
        response.setCreatedBy(jobRegistration.getCreatedBy());
        response.setUpdatedBy(jobRegistration.getUpdatedBy());
        response.setAlternativeExperience(jobRegistration.getAlternativeExperience());
        response.setRelativeExperience(jobRegistration.getRelativeExperience());

        if (jobRegistration.getTenant() != null) {
            response.setTenantId(jobRegistration.getTenant().getId());
        }
        if (jobRegistration.getJobGrade() != null) {
            response.setJobGradeId(jobRegistration.getJobGrade().getId());
        }
        if (jobRegistration.getEducationLevel() != null) {
            response.setEducationLevelId(jobRegistration.getEducationLevel().getId());
        }
        if (jobRegistration.getQualification() != null) {
            response.setQualificationId(jobRegistration.getQualification().getId());
        }
        if (jobRegistration.getWorkUnit() != null) {
            response.setWorkUnitId(jobRegistration.getWorkUnit().getId());
        }
        if (jobRegistration.getJobCategory() != null) {
            response.setJobCategoryId(jobRegistration.getJobCategory().getId());
        }
        if (jobRegistration.getDepartment() != null) {
            response.setDepartmentId(jobRegistration.getDepartment().getId());
        }

        return response;
    }

    public JobRegistration updateJobRegistration(JobRegistration jobRegistration, JobRegistrationRequest jobRegistrationRequest) {
        if (jobRegistrationRequest.getJobTitle() != null) {
            jobRegistration.setJobTitle(jobRegistrationRequest.getJobTitle());
        }
        if (jobRegistrationRequest.getJobCode() != null) {
            jobRegistration.setJobCode(jobRegistrationRequest.getJobCode());
        }
        if (jobRegistrationRequest.getReportsTo() != null) {
            jobRegistration.setReportsTo(jobRegistrationRequest.getReportsTo());
        }
        if (jobRegistrationRequest.getJobType() != null) {
            jobRegistration.setJobType(jobRegistrationRequest.getJobType());
        }
        if (jobRegistrationRequest.getMinExperience() != null) {
            jobRegistration.setMinExperience(jobRegistrationRequest.getMinExperience());
        }
        if (jobRegistrationRequest.getDuties() != null) {
            jobRegistration.setDuties(jobRegistrationRequest.getDuties());
        }
        if (jobRegistrationRequest.getLanguage() != null) {
            jobRegistration.setLanguage(jobRegistrationRequest.getLanguage());
        }
        if (jobRegistrationRequest.getSkills() != null) {
            jobRegistration.setSkills(jobRegistrationRequest.getSkills());
        }
        if (jobRegistrationRequest.getDescription() != null) {
            jobRegistration.setDescription(jobRegistrationRequest.getDescription());
        }
        if (jobRegistrationRequest.getAlternativeExperience() != null) {
            jobRegistration.setAlternativeExperience(jobRegistrationRequest.getAlternativeExperience());
        }
        if (jobRegistrationRequest.getRelativeExperience() != null) {
            jobRegistration.setRelativeExperience(jobRegistrationRequest.getRelativeExperience());
        }

        if (jobRegistrationRequest.getQualificationId() != null) {
            Qualification qualification = new Qualification();
            qualification.setId(jobRegistrationRequest.getQualificationId());
            jobRegistration.setQualification(qualification);
        }

        if (jobRegistrationRequest.getWorkUnitId() != null) {
            WorkUnit workUnit = new WorkUnit();
            workUnit.setId(jobRegistrationRequest.getWorkUnitId());
            jobRegistration.setWorkUnit(workUnit);
        }

        if (jobRegistrationRequest.getEducationLevelId() != null) {
            EducationLevel educationLevel = new EducationLevel();
            educationLevel.setId(jobRegistrationRequest.getEducationLevelId());
            jobRegistration.setEducationLevel(educationLevel);
        }

        if (jobRegistrationRequest.getJobGradeId() != null) {
            JobGrade jobGrade = new JobGrade();
            jobGrade.setId(jobRegistrationRequest.getJobGradeId());
            jobRegistration.setJobGrade(jobGrade);
        }

        if (jobRegistrationRequest.getJobCategoryId() != null) {
            JobCategory jobCategory = new JobCategory();
            jobCategory.setId(jobRegistrationRequest.getJobCategoryId());
            jobRegistration.setJobCategory(jobCategory);
        }

        if (jobRegistrationRequest.getDepartmentId() != null) {
            Department department = new Department();
            department.setId(jobRegistrationRequest.getDepartmentId());
            jobRegistration.setDepartment(department);
        }

//        // Update tenant if required
//        if (jobRegistrationRequest.getTenantId() != null) {
//            Tenant tenant = new Tenant();
//            tenant.setId(jobRegistrationRequest.getTenantId());
//            jobRegistration.setTenant(tenant);
//        }

        return jobRegistration;
    }
}
