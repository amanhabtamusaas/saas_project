package com.auth_service.data;

import com.auth_service.dto.clientDto.TenantDto;
import com.auth_service.enums.RecruitmentServiceResourceName;
import com.auth_service.exception.ResourceExistsException;
import com.auth_service.model.Resource;
import com.auth_service.repository.ResourceRepository;
import com.auth_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class RecruitmentServiceResource {

    private final ResourceRepository resourceRepository;
    private final ValidationUtil validationUtil;

    public void storeEndpoints(TenantDto tenant) {

        List<Resource> resources = new ArrayList<>();

        /* Shortlist Criteria */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_SHORTLIST_CRITERIA.getValue(), "Shortlist Criteria",
                null, tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_SHORTLIST_CRITERIA.getValue(), "Shortlist Criteria",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_SHORTLIST_CRITERIA_BY_ID.getValue(), "Shortlist Criteria",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_SHORTLIST_CRITERIA.getValue(), "Shortlist Criteria",
                null, tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_SHORTLIST_CRITERIA.getValue(), "Shortlist Criteria",
                null, tenant.getId()));

        /* Recruitment */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_RECRUITMENT.getValue(),
                "Recruitment", null, tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_RECRUITMENTS.getValue(),
                "Recruitment", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_RECRUITMENT_BY_ID.getValue(),
                "Recruitment", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_RECRUITMENT_BY_VACANCY_NUMBER.getValue(),
                "Recruitment", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_RECRUITMENTS_BY_STATUS.getValue(),
                "Recruitment", null, tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_RECRUITMENT.getValue(),
                "Recruitment", null, tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.APPROVE_RECRUITMENT.getValue(),
                "Recruitment", null, tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_RECRUITMENT.getValue(),
                "Recruitment", null, tenant.getId()));

        /* Media Type */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_MEDIA_TYPE.getValue(),
                "Media Type", null, tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_MEDIA_TYPES.getValue(),
                "Media Type", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_MEDIA_TYPES_BY_ADVERTISEMENT_ID.getValue(),
                "Media Type", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_MEDIA_TYPE_BY_ID.getValue(),
                "Media Type", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_MEDIA_TYPE.getValue(),
                "Media Type", null, tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_MEDIA_TYPE.getValue(),
                "Media Type", null, tenant.getId()));

        /* Exam Result */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_EXAM_RESULT.getValue(),
                "Exam Result", null, tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_EXAM_RESULTS.getValue(),
                "Exam Result", null, tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_EXAM_RESULT_BY_ID.getValue(),
                "Exam Result", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_EXAM_RESULT.getValue(),
                "Exam Result", null, tenant.getId()));

        /* Assessment Weight */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_ASSESSMENT_WEIGHT.getValue(),
                "Assessment Weight", null, tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_ASSESSMENT_WEIGHTS.getValue(),
                "Assessment Weight", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ASSESSMENT_WEIGHT_BY_RECRUITMENT_ID.getValue(),
                "Assessment Weight", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ASSESSMENT_WEIGHT_BY_ID.getValue(),
                "Assessment Weight", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_ASSESSMENT_WEIGHT.getValue(),
                "Assessment Weight", null, tenant.getId()));

        /* Applicant */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_APPLICANT.getValue(),
                "Applicant", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_APPLICANTS.getValue(),
                "Applicant", null, tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_APPLICANT_BY_ID.getValue(),
                "Applicant", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_APPLICANT.getValue(),
                "Applicant", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_APPLICANT.getValue(),
                "Applicant", null, tenant.getId()));

        /* Applicant Training or Certificate */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_TRAINING.getValue(),
                "Applicant Training or Certificate", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_TRAININGS.getValue(),
                "Applicant Training or Certificate", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_TRAINING_BY_ID.getValue(),
                "Applicant Training or Certificate", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DOWNLOAD_TRAINING_CERTIFICATE.getValue(),
                "Applicant Training or Certificate", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_TRAINING.getValue(),
                "Applicant Training or Certificate", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_TRAINING.getValue(),
                "Applicant Training or Certificate", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));

        /* Applicant Reference */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_REFERENCE.getValue(),
                "Applicant Reference", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_REFERENCES.getValue(),
                "Applicant Reference", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_REFERENCE_BY_ID.getValue(),
                "Applicant Reference", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_REFERENCE.getValue(),
                "Applicant Reference", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_REFERENCE.getValue(),
                "Applicant Reference", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));

        /* Applicant Language */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_LANGUAGE.getValue(),
                "Applicant Language", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_LANGUAGES.getValue(),
                "Applicant Language", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_LANGUAGE_BY_ID.getValue(),
                "Applicant Language", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_LANGUAGE.getValue(),
                "Applicant Language", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_LANGUAGE.getValue(),
                "Applicant Language", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));

        /* Applicant Experience */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_EXPERIENCE.getValue(),
                "Applicant Experience", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_EXPERIENCES.getValue(),
                "Applicant Experience", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_EXPERIENCE_BY_ID.getValue(),
                "Applicant Experience", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DOWNLOAD_EXPERIENCE_DOCUMENT.getValue(),
                "Applicant Experience", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_EXPERIENCE.getValue(),
                "Applicant Experience", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_EXPERIENCE.getValue(),
                "Applicant Experience", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));

        /* Applicant Education */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_EDUCATION.getValue(),
                "Applicant Education", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_EDUCATIONS.getValue(),
                "Applicant Education", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_EDUCATION_BY_ID.getValue(),
                "Applicant Education", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DOWNLOAD_EDUCATION_DOCUMENT.getValue(),
                "Applicant Education", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_EDUCATION.getValue(),
                "Applicant Education", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_EDUCATION.getValue(),
                "Applicant Education", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));

        /* Advertisement */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_ADVERTISEMENT.getValue(),
                "Advertisement", null, tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_ADVERTISEMENTS.getValue(),
                "Advertisement", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ADVERTISEMENT_BY_ID.getValue(),
                "Advertisement", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ADVERTISEMENT_BY_VACANCY_NUMBER.getValue(),
                "Advertisement", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_ADVERTISEMENT.getValue(),
                "Advertisement", null, tenant.getId()));
        resources.add(mapToEntity(RecruitmentServiceResourceName.REMOVE_ADVERTISEMENT_MEDIA_TYPE.getValue(),
                "Advertisement", null, tenant.getId()));

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
