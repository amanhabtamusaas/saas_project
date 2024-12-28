package com.training_service.utility;

import com.training_service.dto.clientDto.*;
import com.training_service.exception.ResourceNotFoundException;
import com.training_service.model.*;
import com.training_service.repository.*;
import com.training_service.client.EmployeeServiceClient;
import com.training_service.client.LeaveServiceClient;
import com.training_service.client.OrganizationServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ValidationUtil {

    private final OrganizationServiceClient organizationServiceClient;
    private final EmployeeServiceClient employeeServiceClient;
    private final LeaveServiceClient leaveServiceClient;
    private final TrainingInstitutionRepository trainingInstitutionRepository;
    private final TrainingCourseCategoryRepository trainingCourseCategoryRepository;
    private final TrainingCourseRepository trainingCourseRepository;
    private final CheckedDocumentRepository checkedDocumentRepository;
    private final PreServiceTraineeRepository preServiceTraineeRepository;
    private final InternshipStudentRepository internshipStudentRepository;
    private final UniversityRepository universityRepository;
    private final PreServiceCourseTypeRepository preServiceCourseTypeRepository;
    private final PreServiceCourseRepository preServiceCourseRepository;
    private final TrainingRepository trainingRepository;

    public TenantDto getTenantById(UUID tenantId) {

        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenant == null) {
            throw new ResourceNotFoundException(
                    "Tenant not found with id '" + tenantId + "'");
        }
        return tenant;
    }

    public EmployeeDto getEmployeeByName(UUID tenantId, String firstName, String middleName, String lastName) {

        EmployeeDto employee = employeeServiceClient.getEmployeeByName(tenantId, firstName, middleName, lastName);
        if (employee == null) {
            throw new ResourceNotFoundException(
                    "Employee not found with name '" + firstName + " " + middleName + " " + lastName + "'");
        }
        return employee;
    }

    public EmployeeDto getEmployeeByEmployeeId(UUID tenantId, String employeeId) {

        EmployeeDto employee = employeeServiceClient.getEmployeeByEmployeeId(tenantId, employeeId);
        if (employee == null) {
            throw new ResourceNotFoundException("Employee not found with employee id '" + employeeId + "'");
        }
        return employee;
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

    public QualificationDto getQualificationById(UUID tenantId, UUID qualificationId) {

        QualificationDto qualification = organizationServiceClient
                .getQualificationById(qualificationId, tenantId);
        if (qualification == null) {
            throw new ResourceNotFoundException(
                    "Qualification not found with id '" + qualificationId + "'");
        }
        return qualification;
    }

    public BudgetYearDto getBudgetYearById(UUID tenantId, UUID budgetYearId) {

        BudgetYearDto budgetYear = leaveServiceClient.getBudgetYearById(tenantId, budgetYearId);
        if (budgetYear == null) {
            throw new ResourceNotFoundException(
                    "Budget year not found with id '" + budgetYearId + "'");
        }
        return budgetYear;
    }

    public TrainingInstitution getInstitutionById(UUID tenantId, UUID institutionId) {

        return trainingInstitutionRepository.findById(institutionId)
                .filter(institution -> institution.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training institution not found with id '" + institutionId + "'"));
    }

    public TrainingCourseCategory getCategoryById(UUID tenantId, UUID categoryId) {

        return trainingCourseCategoryRepository.findById(categoryId)
                .filter(category -> category.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course category not found with id '" + categoryId + "'"));
    }

    public TrainingCourse getTrainingCourseById(UUID tenantId, UUID courseId) {

        return trainingCourseRepository.findById(courseId)
                .filter(course -> course.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training course not found with id '" + courseId + "'"));
    }

    public CheckedDocument getCheckedDocumentById(UUID tenantId, UUID documentId) {

        return checkedDocumentRepository.findById(documentId)
                .filter(title -> title.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Document not found with id '" + documentId + "'"));
    }

    public PreServiceTrainee getTraineeById(UUID tenantId, UUID traineeId) {

        return preServiceTraineeRepository.findById(traineeId)
                .filter(trainee -> trainee.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pre Service Trainee not found with id '" + traineeId + "'"));
    }

    public InternshipStudent getStudentById(UUID tenantId, UUID studentId) {

        return internshipStudentRepository.findById(studentId)
                .filter(intern -> intern.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Internship student not found with id '" + studentId + "'"));
    }

    public University getUniversityById(UUID tenantId, UUID universityId) {

        return universityRepository.findById(universityId)
                .filter(univ -> univ.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "University not found with id '" + universityId + "'"));
    }

    public PreServiceCourseType getCourseTypeById(UUID tenantId, UUID courseTypeId) {

        return preServiceCourseTypeRepository.findById(courseTypeId)
                .filter(type -> type.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course type not found with id '" + courseTypeId + "'"));
    }

    public PreServiceCourse getPreServiceCourseById(UUID tenantId, UUID courseId) {

        return preServiceCourseRepository.findById(courseId)
                .filter(course -> course.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pre Service Course Not Found with id '" + courseId + "'"));
    }

    public Training getTrainingById(UUID tenantId, UUID trainingId) {

        return trainingRepository.findById(trainingId)
                .filter(train -> train.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training not found with id '" + trainingId + "'"));
    }
}
