package com.training_service.config;

import com.training_service.enums.TrainingServiceResourceName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final RoleConverter roleConverter;

    /* Annual Training Plan Permissions */
    public void addAnnualTrainingPlanPermission() {
        checkPermission(TrainingServiceResourceName.ADD_ANNUAL_TRAINING_PLAN);
    }

    public void getAllAnnualTrainingPlansPermission() {
        checkPermission(TrainingServiceResourceName.GET_ALL_ANNUAL_TRAINING_PLANS);
    }

    public void getAnnualTrainingPlansByDepartmentIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_ANNUAL_TRAINING_PLANS_BY_DEPARTMENT_ID);
    }

    public void getAnnualTrainingPlanByIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_ANNUAL_TRAINING_PLAN_BY_ID);
    }

    public void updateAnnualTrainingPlanPermission() {
        checkPermission(TrainingServiceResourceName.UPDATE_ANNUAL_TRAINING_PLAN);
    }

    public void deleteAnnualTrainingPlanPermission() {
        checkPermission(TrainingServiceResourceName.DELETE_ANNUAL_TRAINING_PLAN);
    }

    /* Training Permissions */
    public void addTrainingPermission() {
        checkPermission(TrainingServiceResourceName.ADD_TRAINING);
    }

    public void getAllTrainingsPermission() {
        checkPermission(TrainingServiceResourceName.GET_ALL_TRAININGS);
    }

    public void getTrainingsByStatusPermission() {
        checkPermission(TrainingServiceResourceName.GET_TRAININGS_BY_STATUS);
    }

    public void getTrainingByIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_TRAINING_BY_ID);
    }

    public void updateTrainingPermission() {
        checkPermission(TrainingServiceResourceName.UPDATE_TRAINING);
    }

    public void approveTrainingPermission() {
        checkPermission(TrainingServiceResourceName.APPROVE_TRAINING);
    }

    public void deleteTrainingPermission() {
        checkPermission(TrainingServiceResourceName.DELETE_TRAINING);
    }

    /* Training Participant Permissions */
    public void addTrainingParticipantPermission() {
        checkPermission(TrainingServiceResourceName.ADD_TRAINING_PARTICIPANT);
    }

    public void getAllTrainingParticipantsPermission() {
        checkPermission(TrainingServiceResourceName.GET_ALL_TRAINING_PARTICIPANTS);
    }

    public void getTrainingParticipantByIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_TRAINING_PARTICIPANT_BY_ID);
    }

    public void updateTrainingParticipantPermission() {
        checkPermission(TrainingServiceResourceName.UPDATE_TRAINING_PARTICIPANT);
    }

    public void deleteTrainingParticipantPermission() {
        checkPermission(TrainingServiceResourceName.DELETE_TRAINING_PARTICIPANT);
    }

    /* Training Institution Permissions */
    public void addInstitutionPermission() {
        checkPermission(TrainingServiceResourceName.ADD_INSTITUTION);
    }

    public void getAllInstitutionsPermission() {
        checkPermission(TrainingServiceResourceName.GET_ALL_INSTITUTIONS);
    }

    public void getInstitutionByIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_INSTITUTION_BY_ID);
    }

    public void updateInstitutionPermission() {
        checkPermission(TrainingServiceResourceName.UPDATE_INSTITUTION);
    }

    public void deleteInstitutionPermission() {
        checkPermission(TrainingServiceResourceName.DELETE_INSTITUTION);
    }

    /* Training Course Permissions */
    public void addTrainingCoursePermission() {
        checkPermission(TrainingServiceResourceName.ADD_TRAINING_COURSE);
    }

    public void getAllTrainingCoursesByCategoryIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_ALL_TRAINING_COURSES_BY_CATEGORY_ID);
    }

    public void getTrainingCourseByIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_TRAINING_COURSE_BY_ID);
    }

    public void updateTrainingCoursePermission() {
        checkPermission(TrainingServiceResourceName.UPDATE_TRAINING_COURSE);
    }

    public void deleteTrainingCoursePermission() {
        checkPermission(TrainingServiceResourceName.DELETE_TRAINING_COURSE);
    }

    /* Training Course Category Permissions */
    public void addTrainingCourseCategoryPermission() {
        checkPermission(TrainingServiceResourceName.ADD_TRAINING_COURSE_CATEGORY);
    }

    public void getAllTrainingCourseCategoriesPermission() {
        checkPermission(TrainingServiceResourceName.GET_ALL_TRAINING_COURSE_CATEGORIES);
    }

    public void getTrainingCourseCategoryByIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_TRAINING_COURSE_CATEGORY_BY_ID);
    }

    public void updateTrainingCourseCategoryPermission() {
        checkPermission(TrainingServiceResourceName.UPDATE_TRAINING_COURSE_CATEGORY);
    }

    public void deleteTrainingCourseCategoryPermission() {
        checkPermission(TrainingServiceResourceName.DELETE_TRAINING_COURSE_CATEGORY);
    }

    /* Pre Service Trainee Result Permissions */
    public void addPreServiceTraineeResultPermission() {
        checkPermission(TrainingServiceResourceName.ADD_PRE_SERVICE_TRAINEE_RESULT);
    }

    public void getAllTraineeResultsByCourseIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_ALL_TRAINEE_RESULTS_BY_COURSE_ID);
    }

    public void getPreServiceTraineeResultByIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_PRE_SERVICE_TRAINEE_RESULT_BY_ID);
    }

    public void updatePreServiceTraineeResultPermission() {
        checkPermission(TrainingServiceResourceName.UPDATE_PRE_SERVICE_TRAINEE_RESULT);
    }

    public void deletePreServiceTraineeResultPermission() {
        checkPermission(TrainingServiceResourceName.DELETE_PRE_SERVICE_TRAINEE_RESULT);
    }

    /* Pre Service Trainee Permissions */
    public void addPreServiceTraineePermission() {
        checkPermission(TrainingServiceResourceName.ADD_PRE_SERVICE_TRAINEE);
    }

    public void addCoursesToPreServiceTraineePermission() {
        checkPermission(TrainingServiceResourceName.ADD_COURSES_TO_PRE_SERVICE_TRAINEE);
    }

    public void getAllPreServiceTraineesPermission() {
        checkPermission(TrainingServiceResourceName.GET_ALL_PRE_SERVICE_TRAINEES);
    }

    public void getPreServiceTraineesByBudgetYearIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_PRE_SERVICE_TRAINEES_BY_BUDGET_YEAR_ID);
    }

    public void getPreServiceTraineeByIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_PRE_SERVICE_TRAINEE_BY_ID);
    }

    public void downloadPreServiceTraineeImagePermission() {
        checkPermission(TrainingServiceResourceName.DOWNLOAD_PRE_SERVICE_TRAINEE_IMAGE);
    }

    public void updatePreServiceTraineePermission() {
        checkPermission(TrainingServiceResourceName.UPDATE_PRE_SERVICE_TRAINEE);
    }

    public void deletePreServiceTraineePermission() {
        checkPermission(TrainingServiceResourceName.DELETE_PRE_SERVICE_TRAINEE);
    }

    /* Pre Service Trainee Checked Document Permissions */
    public void addPreServiceCheckedDocumentPermission() {
        checkPermission(TrainingServiceResourceName.ADD_PRE_SERVICE_CHECKED_DOCUMENT);
    }

    public void getAllPreServiceCheckedDocumentsPermission() {
        checkPermission(TrainingServiceResourceName.GET_ALL_PRE_SERVICE_CHECKED_DOCUMENTS);
    }

    public void getPreServiceCheckedDocumentsByTraineeIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_PRE_SERVICE_CHECKED_DOCUMENTS_BY_TRAINEE_ID);
    }

    public void getPreServiceCheckedDocumentByIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_PRE_SERVICE_CHECKED_DOCUMENT_BY_ID);
    }

    public void updatePreServiceCheckedDocumentPermission() {
        checkPermission(TrainingServiceResourceName.UPDATE_PRE_SERVICE_CHECKED_DOCUMENT);
    }

    public void removeTraineeCheckedDocumentPermission() {
        checkPermission(TrainingServiceResourceName.REMOVE_TRAINEE_CHECKED_DOCUMENT);
    }

    public void deletePreServiceCheckedDocumentPermission() {
        checkPermission(TrainingServiceResourceName.DELETE_PRE_SERVICE_CHECKED_DOCUMENT);
    }

    /* Pre Service Course Type Permissions */
    public void addPreServiceCourseTypePermission() {
        checkPermission(TrainingServiceResourceName.ADD_PRE_SERVICE_COURSE_TYPE);
    }

    public void getAllPreServiceCourseTypesPermission() {
        checkPermission(TrainingServiceResourceName.GET_ALL_PRE_SERVICE_COURSE_TYPES);
    }

    public void getPreServiceCourseTypeByIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_PRE_SERVICE_COURSE_TYPE_BY_ID);
    }

    public void updatePreServiceCourseTypePermission() {
        checkPermission(TrainingServiceResourceName.UPDATE_PRE_SERVICE_COURSE_TYPE);
    }

    public void deletePreServiceCourseTypePermission() {
        checkPermission(TrainingServiceResourceName.DELETE_PRE_SERVICE_COURSE_TYPE);
    }

    /* Pre Service Course Permissions */
    public void addPreServiceCoursePermission() {
        checkPermission(TrainingServiceResourceName.ADD_PRE_SERVICE_COURSE);
    }

    public void getAllPreServiceCoursesPermission() {
        checkPermission(TrainingServiceResourceName.GET_ALL_PRE_SERVICE_COURSES);
    }

    public void getPreServiceCoursesByTraineeIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_PRE_SERVICE_COURSES_BY_TRAINEE_ID);
    }

    public void getPreServiceCoursesByCourseTypeIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_PRE_SERVICE_COURSES_BY_COURSE_TYPE_ID);
    }

    public void getPreServiceCourseByIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_PRE_SERVICE_COURSE_BY_ID);
    }

    public void updatePreServiceCoursePermission() {
        checkPermission(TrainingServiceResourceName.UPDATE_PRE_SERVICE_COURSE);
    }

    public void removeCourseByTraineeIdPermission() {
        checkPermission(TrainingServiceResourceName.REMOVE_COURSE_BY_TRAINEE_ID);
    }

    public void deletePreServiceCoursePermission() {
        checkPermission(TrainingServiceResourceName.DELETE_PRE_SERVICE_COURSE);
    }

    /* University Permissions */
    public void addUniversityPermission() {
        checkPermission(TrainingServiceResourceName.ADD_UNIVERSITY);
    }

    public void getAllUniversitiesPermission() {
        checkPermission(TrainingServiceResourceName.GET_ALL_UNIVERSITIES);
    }

    public void getUniversityByIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_UNIVERSITY_BY_ID);
    }

    public void updateUniversityPermission() {
        checkPermission(TrainingServiceResourceName.UPDATE_UNIVERSITY);
    }

    public void deleteUniversityPermission() {
        checkPermission(TrainingServiceResourceName.DELETE_UNIVERSITY);
    }

    /* Internship Student Permissions */
    public void addInternshipStudentPermission() {
        checkPermission(TrainingServiceResourceName.ADD_INTERNSHIP_STUDENT);
    }

    public void getAllInternshipStudentsPermission() {
        checkPermission(TrainingServiceResourceName.GET_ALL_INTERNSHIP_STUDENTS);
    }

    public void getInternshipStudentsByBudgetYearIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_INTERNSHIP_STUDENTS_BY_BUDGET_YEAR_ID);
    }

    public void getInternshipStudentByIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_INTERNSHIP_STUDENT_BY_ID);
    }

    public void updateInternshipStudentPermission() {
        checkPermission(TrainingServiceResourceName.UPDATE_INTERNSHIP_STUDENT);
    }

    public void assignDepartmentToInternshipStudentPermission() {
        checkPermission(TrainingServiceResourceName.ASSIGN_DEPARTMENT_TO_INTERNSHIP_STUDENT);
    }

    public void assignStatusToInternshipStudentPermission() {
        checkPermission(TrainingServiceResourceName.ASSIGN_STATUS_TO_INTERNSHIP_STUDENT);
    }

    public void deleteInternshipStudentPermission() {
        checkPermission(TrainingServiceResourceName.DELETE_INTERNSHIP_STUDENT);
    }

    /* Internship Payment Permissions */
    public void addInternshipPaymentPermission() {
        checkPermission(TrainingServiceResourceName.ADD_INTERNSHIP_PAYMENT);
    }

    public void getAllInternshipPaymentsPermission() {
        checkPermission(TrainingServiceResourceName.GET_ALL_INTERNSHIP_PAYMENTS);
    }

    public void getInternshipPaymentByIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_INTERNSHIP_PAYMENT_BY_ID);
    }

    public void updateInternshipPaymentPermission() {
        checkPermission(TrainingServiceResourceName.UPDATE_INTERNSHIP_PAYMENT);
    }

    /* Education Opportunity Permissions */
    public void addEducationOpportunityPermission() {
        checkPermission(TrainingServiceResourceName.ADD_EDUCATION_OPPORTUNITY);
    }

    public void getAllEducationOpportunitiesPermission() {
        checkPermission(TrainingServiceResourceName.GET_ALL_EDUCATION_OPPORTUNITIES);
    }

    public void getEducationOpportunitiesByEmployeeIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_EDUCATION_OPPORTUNITIES_BY_EMPLOYEE_ID);
    }

    public void getEducationOpportunityByIdPermission() {
        checkPermission(TrainingServiceResourceName.GET_EDUCATION_OPPORTUNITY_BY_ID);
    }

    public void updateEducationOpportunityPermission() {
        checkPermission(TrainingServiceResourceName.UPDATE_EDUCATION_OPPORTUNITY);
    }

    public void deleteEducationOpportunityPermission() {
        checkPermission(TrainingServiceResourceName.DELETE_EDUCATION_OPPORTUNITY);
    }

    private void checkPermission(TrainingServiceResourceName resourceName) {
        boolean hasPermission = roleConverter.hasPermission(resourceName.getValue());
        if (!hasPermission) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}