package com.auth_service.data;

import com.auth_service.dto.clientDto.TenantDto;
import com.auth_service.enums.OrganizationServiceResourceName;
import com.auth_service.exception.ResourceExistsException;
import com.auth_service.model.Resource;
import com.auth_service.repository.ResourceRepository;
import com.auth_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class OrganizationServiceResource {

    private final ResourceRepository resourceRepository;
    private final ValidationUtil validationUtil;

    public void storeEndpoints(TenantDto tenant) {

        List<Resource> resources = new ArrayList<>();

        /* Tenant */
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_TENANT_BY_ID.getValue(), "Tenant",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.DOWNLOAD_TENANT_LOGO.getValue(), "Tenant",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_TENANT.getValue(), "Tenant",
                null, tenant.getId()));

        /* Work Unit */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_WORK_UNIT.getValue(), "Work Unit",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_WORK_UNITS.getValue(), "Work Unit",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_WORK_UNIT_BY_ID.getValue(), "Work Unit",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_WORK_UNIT.getValue(), "Work Unit",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_WORK_UNIT.getValue(), "Work Unit",
                null, tenant.getId()));

        /* Staff Plan */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_STAFF_PLAN.getValue(), "Staff Plan",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_STAFF_PLANS.getValue(), "Staff Plan",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_STAFF_PLANS_BY_DEPARTMENT_ID.getValue(), "Staff Plan",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_STAFF_PLAN.getValue(), "Staff Plan",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_STAFF_PLAN.getValue(), "Staff Plan",
                null, tenant.getId()));

        /* Qualification */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_QUALIFICATION.getValue(), "Qualification",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_QUALIFICATIONS.getValue(), "Qualification",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_QUALIFICATION_BY_ID.getValue(), "Qualification",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_QUALIFICATION.getValue(), "Qualification",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_QUALIFICATION.getValue(), "Qualification",
                null, tenant.getId()));

        /* Pay Grade */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_PAY_GRADE.getValue(), "Pay Grade",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_PAY_GRADES.getValue(), "Pay Grade",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_PAY_GRADE_BY_ID.getValue(), "Pay Grade",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_PAY_GRADES_BY_JOB_GRADE_ID.getValue(), "Pay Grade",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_PAY_GRADE.getValue(), "Pay Grade",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_PAY_GRADE.getValue(), "Pay Grade",
                null, tenant.getId()));

        /* Location */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_LOCATION.getValue(), "Location",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_SUB_LOCATION.getValue(), "Location",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_LOCATIONS.getValue(), "Location",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_LOCATION_BY_ID.getValue(), "Location",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_PARENT_LOCATIONS.getValue(), "Location",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_SUB_LOCATIONS.getValue(), "Location",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_LOCATION.getValue(), "Location",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_SUB_LOCATION.getValue(), "Location",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_LOCATION.getValue(), "Location",
                null, tenant.getId()));

        /* Job */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_JOB.getValue(), "Job",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_JOBS.getValue(), "Job",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_JOB_BY_ID.getValue(), "Job",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_JOBS_BY_DEPARTMENT_ID.getValue(), "Job",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_JOB.getValue(), "Job",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_JOB.getValue(), "Job",
                null, tenant.getId()));

        /* Job Grade */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_JOB_GRADE.getValue(), "Job Grade",
                null,  tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_JOB_GRADES.getValue(), "Job Grade",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_JOB_GRADE_BY_ID.getValue(), "Job Grade",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_JOB_GRADE.getValue(), "Job Grade",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_JOB_GRADE.getValue(), "Job Grade",
                null, tenant.getId()));

        /* Job Category */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_JOB_CATEGORY.getValue(), "Job Category",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_JOB_CATEGORIES.getValue(), "Job Category",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_JOB_CATEGORY_BY_ID.getValue(), "Job Category",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_JOB_CATEGORY.getValue(), "Job Category",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_JOB_CATEGORY.getValue(), "Job Category",
                null, tenant.getId()));

        /* Field of Study */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_FIELD_OF_STUDY.getValue(), "Field of Study",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_FIELD_OF_STUDIES.getValue(), "Field of Study",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_FIELD_OF_STUDY_BY_ID.getValue(), "Field of Study",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_FIELD_OF_STUDY.getValue(), "Field of Study",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_FIELD_OF_STUDY.getValue(), "Field of Study",
                null, tenant.getId()));

        /* Education Level */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_EDUCATION_LEVEL.getValue(), "Education Level",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_EDUCATION_LEVELS.getValue(), "Education Level",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_EDUCATION_LEVEL_BY_ID.getValue(), "Education Level",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_EDUCATION_LEVEL.getValue(), "Education Level",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_EDUCATION_LEVEL.getValue(), "Education Level",
                null, tenant.getId()));

        /* Department */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_DEPARTMENT.getValue(), "Department",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_SUB_DEPARTMENT.getValue(), "Department",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_DEPARTMENTS.getValue(), "Department",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_DEPARTMENT_BY_ID.getValue(), "Department",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_PARENT_DEPARTMENTS.getValue(), "Department",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_SUB_DEPARTMENTS.getValue(), "Department",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.TRANSFER_PARENT_DEPARTMENT.getValue(), "Department",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.TRANSFER_SUB_DEPARTMENT.getValue(), "Department",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_DEPARTMENT.getValue(), "Department",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_SUB_DEPARTMENT.getValue(), "Department",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_DEPARTMENT.getValue(), "Department",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_DEPARTMENT_HISTORIES.getValue(), "Department",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));

        /* Department Type */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_DEPARTMENT_TYPE.getValue(), "Department Type",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_DEPARTMENT_TYPES.getValue(), "Department Type",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_DEPARTMENT_TYPE_BY_ID.getValue(), "Department Type",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_DEPARTMENT_TYPE.getValue(), "Department Type",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_DEPARTMENT_TYPE.getValue(), "Department Type",
                null, tenant.getId()));

        /* Address */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_ADDRESS.getValue(), "Address",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_ADDRESSES.getValue(), "Address",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ADDRESSES_BY_DEPARTMENT_ID.getValue(), "Address",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_ADDRESS.getValue(), "Address",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_ADDRESS.getValue(), "Address",
                null, tenant.getId()));

        /* Location Type */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_LOCATION_TYPE.getValue(), "Location Type",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_LOCATION_TYPES.getValue(), "Location Type",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_LOCATION_TYPE_BY_ID.getValue(), "Location Type",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_LOCATION_TYPE.getValue(), "Location Type",
                null, tenant.getId()));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_LOCATION_TYPE.getValue(), "Location Type",
                null, tenant.getId()));

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
