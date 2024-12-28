package com.auth_service.data;

import com.auth_service.dto.clientDto.TenantDto;
import com.auth_service.enums.HrPlanningServiceResourceName;
import com.auth_service.exception.ResourceExistsException;
import com.auth_service.model.Resource;
import com.auth_service.repository.ResourceRepository;
import com.auth_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class HrPlanningServiceResource {

    private final ResourceRepository resourceRepository;
    private final ValidationUtil validationUtil;

    public void storeEndpoints(TenantDto tenant) {

        List<Resource> resources = new ArrayList<>();

        /* Annual Recruitment and Promotion */
        resources.add(mapToEntity(HrPlanningServiceResourceName.ADD_ANNUAL_RECRUITMENT_AND_PROMOTION.getValue(),
                "Annual Recruitment and Promotion", tenant.getId()));
        resources.add(mapToEntity(HrPlanningServiceResourceName.GET_ALL_ANNUAL_RECRUITMENT_AND_PROMOTIONS.getValue(),
                "Annual Recruitment and Promotion", tenant.getId()));
        resources.add(mapToEntity(HrPlanningServiceResourceName.GET_ANNUAL_RECRUITMENT_AND_PROMOTION_BY_ID.getValue(),
                "Annual Recruitment and Promotion", tenant.getId()));
        resources.add(mapToEntity(HrPlanningServiceResourceName.UPDATE_ANNUAL_RECRUITMENT_AND_PROMOTION.getValue(),
                "Annual Recruitment and Promotion", tenant.getId()));
        resources.add(mapToEntity(HrPlanningServiceResourceName.DELETE_ANNUAL_RECRUITMENT_AND_PROMOTION.getValue(),
                "Annual Recruitment and Promotion", tenant.getId()));

        /* HR Need Request */
        resources.add(mapToEntity(HrPlanningServiceResourceName.ADD_HR_NEED_REQUEST.getValue(),
                "HR Need Request", tenant.getId()));
        resources.add(mapToEntity(HrPlanningServiceResourceName.GET_ALL_HR_NEED_REQUESTS.getValue(),
                "HR Need Request", tenant.getId()));
        resources.add(mapToEntity(HrPlanningServiceResourceName.GET_HR_NEED_REQUEST_BY_ID.getValue(),
                "HR Need Request", tenant.getId()));
        resources.add(mapToEntity(HrPlanningServiceResourceName.UPDATE_HR_NEED_REQUEST.getValue(),
                "HR Need Request", tenant.getId()));
        resources.add(mapToEntity(HrPlanningServiceResourceName.DELETE_HR_NEED_REQUEST.getValue(),
                "HR Need Request", tenant.getId()));

        /* HR Analysis */
        resources.add(mapToEntity(HrPlanningServiceResourceName.ADD_HR_ANALYSIS.getValue(),
                "HR Analysis", tenant.getId()));
        resources.add(mapToEntity(HrPlanningServiceResourceName.GET_ALL_HR_ANALYSES.getValue(),
                "HR Analysis", tenant.getId()));
        resources.add(mapToEntity(HrPlanningServiceResourceName.GET_HR_ANALYSIS_BY_ID.getValue(),
                "HR Analysis", tenant.getId()));
        resources.add(mapToEntity(HrPlanningServiceResourceName.UPDATE_HR_ANALYSIS.getValue(),
                "HR Analysis", tenant.getId()));
        resources.add(mapToEntity(HrPlanningServiceResourceName.DELETE_HR_ANALYSIS.getValue(),
                "HR Analysis", tenant.getId()));

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
        resource.setRequiredRoles(roles);

        return resource;
    }
}
