package com.auth_service.data;

import com.auth_service.dto.clientDto.TenantDto;
import com.auth_service.enums.EmployeeServiceResourceName;
import com.auth_service.exception.ResourceExistsException;
import com.auth_service.model.Resource;
import com.auth_service.repository.ResourceRepository;
import com.auth_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class EmployeeServiceResource {

    private final ResourceRepository resourceRepository;
    private final ValidationUtil validationUtil;

    public void storeEndpoints(TenantDto tenant) {

        List<Resource> resources = new ArrayList<>();

        /* Employee */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_EMPLOYEE.getValue(), "Employee",
                null, tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_EMPLOYEES.getValue(), "Employee",
                null, tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_EMPLOYEE_BY_ID.getValue(), "Employee",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_EMPLOYEE_BY_NAME.getValue(), "Employee",
                null, tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_EMPLOYEE_BY_EMPLOYEE_ID.getValue(), "Employee",
                null, tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.DOWNLOAD_EMPLOYEE_IMAGE.getValue(), "Employee",
                null, tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_EMPLOYEES_BY_DEPARTMENT_ID.getValue(), "Employee",
                null, tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_EMPLOYEE.getValue(), "Employee",
                null, tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_EMPLOYEE.getValue(), "Employee",
                null, tenant.getId()));

        /* Employee Training or Certificate */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_TRAINING.getValue(), "Employee Training",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_TRAININGS.getValue(), "Employee Training",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_TRAINING_BY_ID.getValue(), "Employee Training",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_TRAININGS_BY_EMPLOYEE_ID.getValue(), "Employee Training",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.DOWNLOAD_TRAINING_CERTIFICATE.getValue(), "Employee Training",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_TRAINING.getValue(), "Employee Training",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_TRAINING.getValue(), "Employee Training",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));

        /* Title Name */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_TITLE_NAME.getValue(), "Title Name",
                null, tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_TITLE_NAMES.getValue(), "Title Name",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_TITLE_NAME_BY_ID.getValue(), "Title Name",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_TITLE_NAME.getValue(), "Title Name",
                null, tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_TITLE_NAME.getValue(), "Title Name",
                null, tenant.getId()));

        /* Employee Skill */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_SKILL.getValue(),
                "Employee Skill", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_SKILLS.getValue(),
                "Employee Skill", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_SKILL_BY_ID.getValue(),
                "Employee Skill", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_SKILLS_BY_EMPLOYEE_ID.getValue(),
                "Employee Skill", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_SKILL.getValue(),
                "Employee Skill", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_SKILL.getValue(),
                "Employee Skill", tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));

        /* Employee Reference */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_REFERENCE.getValue(), "Employee Reference",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_REFERENCES.getValue(), "Employee Reference",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_REFERENCE_BY_ID.getValue(), "Employee Reference",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_REFERENCES_BY_EMPLOYEE_ID.getValue(), "Employee Reference",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_REFERENCE.getValue(), "Employee Reference",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_REFERENCE.getValue(), "Employee Reference",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));

        /* Employee Language */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_LANGUAGE.getValue(), "Employee Language",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_LANGUAGES.getValue(), "Employee Language",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_LANGUAGE_BY_ID.getValue(), "Employee Language",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_LANGUAGES_BY_EMPLOYEE_ID.getValue(), "Employee Language",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_LANGUAGE.getValue(), "Employee Language",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_LANGUAGE.getValue(), "Employee Language",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));

        /* Language Name */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_LANGUAGE_NAME.getValue(), "Language",
                null, tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_LANGUAGE_NAMES.getValue(), "Language",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_LANGUAGE_NAME_BY_ID.getValue(), "Language",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_LANGUAGE_NAME.getValue(), "Language",
                null, tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_LANGUAGE_NAME.getValue(), "Language",
                null, tenant.getId()));

        /* Employee Family */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_FAMILY.getValue(), "Employee Family",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_FAMILIES.getValue(), "Employee Family",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_FAMILY_BY_ID.getValue(), "Employee Family",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_FAMILIES_BY_EMPLOYEE_ID.getValue(), "Employee Family",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_FAMILY.getValue(), "Employee Family",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_FAMILY.getValue(), "Employee Family",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));

        /* Employee Experience */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_EXPERIENCE.getValue(), "Employee Experience",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_EXPERIENCES.getValue(), "Employee Experience",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_EXPERIENCE_BY_ID.getValue(), "Employee Experience",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_EXPERIENCES_BY_EMPLOYEE_ID.getValue(), "Employee Experience",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.DOWNLOAD_EXPERIENCE_DOCUMENT.getValue(), "Employee Experience",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_EXPERIENCE.getValue(), "Employee Experience",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_EXPERIENCE.getValue(), "Employee Experience",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));

        /* Employee Education */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_EDUCATION.getValue(), "Employee Education",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_EDUCATIONS.getValue(), "Employee Education",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_EDUCATION_BY_ID.getValue(), "Employee Education",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_EDUCATIONS_BY_EMPLOYEE_ID.getValue(), "Employee Education",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.DOWNLOAD_EDUCATION_DOCUMENT.getValue(), "Employee Education",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_EDUCATION.getValue(), "Employee Education",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_EDUCATION.getValue(), "Employee Education",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));

        /* Employee Address */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_ADDRESS.getValue(), "Employee Address",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_ADDRESSES.getValue(), "Employee Address",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ADDRESS_BY_ID.getValue(), "Employee Address",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ADDRESSES_BY_EMPLOYEE_ID.getValue(), "Employee Address",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_ADDRESS.getValue(), "Employee Address",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_ADDRESS.getValue(), "Employee Address",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));

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