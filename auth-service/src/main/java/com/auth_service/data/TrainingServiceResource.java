package com.auth_service.data;

import com.auth_service.dto.clientDto.TenantDto;
import com.auth_service.enums.TrainingServiceResourceName;
import com.auth_service.exception.ResourceExistsException;
import com.auth_service.model.Resource;
import com.auth_service.repository.ResourceRepository;
import com.auth_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class TrainingServiceResource {

    private final ResourceRepository resourceRepository;
    private final ValidationUtil validationUtil;

    public void storeEndpoints(TenantDto tenant) {

        List<Resource> resources = new ArrayList<>();

        /* Annual Training Plan */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_ANNUAL_TRAINING_PLAN.getValue(),
                "Annual Training Plan", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_ANNUAL_TRAINING_PLANS.getValue(),
                "Annual Training Plan", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ANNUAL_TRAINING_PLANS_BY_DEPARTMENT_ID.getValue(),
                "Annual Training Plan", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ANNUAL_TRAINING_PLAN_BY_ID.getValue(),
                "Annual Training Plan", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_ANNUAL_TRAINING_PLAN.getValue(),
                "Annual Training Plan", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_ANNUAL_TRAINING_PLAN.getValue(),
                "Annual Training Plan", null, tenant.getId()));

        /* Training */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_TRAINING.getValue(),
                "Training", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_TRAININGS.getValue(),
                "Training", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_TRAININGS_BY_STATUS.getValue(),
                "Training", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_TRAINING_BY_ID.getValue(),
                "Training", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_TRAINING.getValue(),
                "Training", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.APPROVE_TRAINING.getValue(),
                "Training", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_TRAINING.getValue(),
                "Training", null, tenant.getId()));

        /* Training Participant */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_TRAINING_PARTICIPANT.getValue(),
                "Training Participant", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_TRAINING_PARTICIPANTS.getValue(),
                "Training Participant", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_TRAINING_PARTICIPANT_BY_ID.getValue(),
                "Training Participant", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_TRAINING_PARTICIPANT.getValue(),
                "Training Participant", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_TRAINING_PARTICIPANT.getValue(),
                "Training Participant", null, tenant.getId()));

        /* Training Institution */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_INSTITUTION.getValue(),
                "Training Institution", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_INSTITUTIONS.getValue(),
                "Training Institution", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_INSTITUTION_BY_ID.getValue(),
                "Training Institution", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_INSTITUTION.getValue(),
                "Training Institution", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_INSTITUTION.getValue(),
                "Training Institution", null, tenant.getId()));

        /* Training Course */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_TRAINING_COURSE.getValue(),
                "Training Course", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_TRAINING_COURSES_BY_CATEGORY_ID.getValue(),
                "Training Course", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_TRAINING_COURSE_BY_ID.getValue(),
                "Training Course", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_TRAINING_COURSE.getValue(),
                "Training Course", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_TRAINING_COURSE.getValue(),
                "Training Course", null, tenant.getId()));

        /* Training Course Category */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_TRAINING_COURSE_CATEGORY.getValue(),
                "Training Course Category", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_TRAINING_COURSE_CATEGORIES.getValue(),
                "Training Course Category", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_TRAINING_COURSE_CATEGORY_BY_ID.getValue(),
                "Training Course Category", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_TRAINING_COURSE_CATEGORY.getValue(),
                "Training Course Category", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_TRAINING_COURSE_CATEGORY.getValue(),
                "Training Course Category", null, tenant.getId()));

        /* Pre Service Trainee Result */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_PRE_SERVICE_TRAINEE_RESULT.getValue(),
                "Pre Service Trainee Result", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_TRAINEE_RESULTS_BY_COURSE_ID.getValue(),
                "Pre Service Trainee Result", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_PRE_SERVICE_TRAINEE_RESULT_BY_ID.getValue(),
                "Pre Service Trainee Result", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_PRE_SERVICE_TRAINEE_RESULT.getValue(),
                "Pre Service Trainee Result", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_PRE_SERVICE_TRAINEE_RESULT.getValue(),
                "Pre Service Trainee Result", null, tenant.getId()));

        /* Pre Service Trainee */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_PRE_SERVICE_TRAINEE.getValue(),
                "Pre Service Trainee", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_COURSES_TO_PRE_SERVICE_TRAINEE.getValue(),
                "Pre Service Trainee", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_PRE_SERVICE_TRAINEES.getValue(),
                "Pre Service Trainee", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_PRE_SERVICE_TRAINEES_BY_BUDGET_YEAR_ID.getValue(),
                "Pre Service Trainee", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_PRE_SERVICE_TRAINEE_BY_ID.getValue(),
                "Pre Service Trainee", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.DOWNLOAD_PRE_SERVICE_TRAINEE_IMAGE.getValue(),
                "Pre Service Trainee", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_PRE_SERVICE_TRAINEE.getValue(),
                "Pre Service Trainee", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_PRE_SERVICE_TRAINEE.getValue(),
                "Pre Service Trainee", null, tenant.getId()));

        /* Pre Service Trainee Checked Document */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_PRE_SERVICE_CHECKED_DOCUMENT.getValue(),
                "Pre Service Trainee Checked Document", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_PRE_SERVICE_CHECKED_DOCUMENTS.getValue(),
                "Pre Service Trainee Checked Document", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_PRE_SERVICE_CHECKED_DOCUMENTS_BY_TRAINEE_ID.getValue(),
                "Pre Service Trainee Checked Document", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_PRE_SERVICE_CHECKED_DOCUMENT_BY_ID.getValue(),
                "Pre Service Trainee Checked Document", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_PRE_SERVICE_CHECKED_DOCUMENT.getValue(),
                "Pre Service Trainee Checked Document", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.REMOVE_TRAINEE_CHECKED_DOCUMENT.getValue(),
                "Pre Service Trainee Checked Document", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_PRE_SERVICE_CHECKED_DOCUMENT.getValue(),
                "Pre Service Trainee Checked Document", null, tenant.getId()));

        /* Pre Service Course Type */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_PRE_SERVICE_COURSE_TYPE.getValue(),
                "Pre Service Course Type", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_PRE_SERVICE_COURSE_TYPES.getValue(),
                "Pre Service Course Type", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_PRE_SERVICE_COURSE_TYPE_BY_ID.getValue(),
                "Pre Service Course Type", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_PRE_SERVICE_COURSE_TYPE.getValue(),
                "Pre Service Course Type", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_PRE_SERVICE_COURSE_TYPE.getValue(),
                "Pre Service Course Type", null, tenant.getId()));

        /* Pre Service Course */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_PRE_SERVICE_COURSE.getValue(),
                "Pre Service Course", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_PRE_SERVICE_COURSES.getValue(),
                "Pre Service Course", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_PRE_SERVICE_COURSES_BY_TRAINEE_ID.getValue(),
                "Pre Service Course", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_PRE_SERVICE_COURSES_BY_COURSE_TYPE_ID.getValue(),
                "Pre Service Course", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_PRE_SERVICE_COURSE_BY_ID.getValue(),
                "Pre Service Course", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_PRE_SERVICE_COURSE.getValue(),
                "Pre Service Course", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.REMOVE_COURSE_BY_TRAINEE_ID.getValue(),
                "Pre Service Course", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_PRE_SERVICE_COURSE.getValue(),
                "Pre Service Course", null, tenant.getId()));

        /* University */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_UNIVERSITY.getValue(),
                "University", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_UNIVERSITIES.getValue(),
                "University", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_UNIVERSITY_BY_ID.getValue(),
                "University", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_UNIVERSITY.getValue(),
                "University", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_UNIVERSITY.getValue(),
                "University", null, tenant.getId()));

        /* Internship Student */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_INTERNSHIP_STUDENT.getValue(),
                "Internship Student", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_INTERNSHIP_STUDENTS.getValue(),
                "Internship Student", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_INTERNSHIP_STUDENTS_BY_BUDGET_YEAR_ID.getValue(),
                "Internship Student", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_INTERNSHIP_STUDENT_BY_ID.getValue(),
                "Internship Student", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_INTERNSHIP_STUDENT.getValue(),
                "Internship Student", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.ASSIGN_DEPARTMENT_TO_INTERNSHIP_STUDENT.getValue(),
                "Internship Student", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.ASSIGN_STATUS_TO_INTERNSHIP_STUDENT.getValue(),
                "Internship Student", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_INTERNSHIP_STUDENT.getValue(),
                "Internship Student", null, tenant.getId()));

        /* Internship Payment */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_INTERNSHIP_PAYMENT.getValue(),
                "Internship Payment", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_INTERNSHIP_PAYMENTS.getValue(),
                "Internship Payment", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_INTERNSHIP_PAYMENT_BY_ID.getValue(),
                "Internship Payment", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_INTERNSHIP_PAYMENT.getValue(),
                "Internship Payment", null, tenant.getId()));

        /* Education Opportunity */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_EDUCATION_OPPORTUNITY.getValue(),
                "Education Opportunity", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_EDUCATION_OPPORTUNITIES.getValue(),
                "Education Opportunity", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_EDUCATION_OPPORTUNITIES_BY_EMPLOYEE_ID.getValue(),
                "Education Opportunity", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_EDUCATION_OPPORTUNITY_BY_ID.getValue(),
                "Education Opportunity", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_EDUCATION_OPPORTUNITY.getValue(),
                "Education Opportunity", null, tenant.getId()));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_EDUCATION_OPPORTUNITY.getValue(),
                "Education Opportunity", null, tenant.getId()));

        for (Resource resource : resources) {
            if (resourceRepository.existsByTenantIdAndResourceName(tenant.getId(), resource.getResourceName())) {
                throw new ResourceExistsException(
                        "Resource already exists with name '" + resource.getResourceName() + "'");
            }
            resourceRepository.save(resource);
        }
    }

    private Resource mapToEntity(String requestName,
                                 String groupName,
                                 String roleName,
                                 UUID tenantId) {

        Resource resource = new Resource();
        resource.setResourceName(requestName);
        resource.setGroupName(groupName);
        resource.setTenantId(tenantId);
        resource.setDescription("");

        Set<String> roles = new HashSet<>();
        TenantDto tenant = validationUtil.getTenantById(tenantId);
        String adminRole = tenant.getAbbreviatedName().toLowerCase() + "_admin";
        roles.add(adminRole);
        if (roleName != null) {
            roles.add(roleName);
        }
        resource.setRequiredRoles(roles);

        return resource;
    }
}
