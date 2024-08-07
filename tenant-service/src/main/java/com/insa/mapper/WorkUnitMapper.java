package com.insa.mapper;
import com.insa.dto.requestDto.WorkUnitRequest;
import com.insa.dto.responseDto.WorkUnitResponse;
import com.insa.entity.WorkUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkUnitMapper {
    @Autowired
    private TenantMapper tenantMapper;
    @Autowired
    private JobRegistrationMapper jobRegistrationMapper;

    public WorkUnit mapToEntity(WorkUnitRequest workUnitRequest) {
        WorkUnit workUnit = new WorkUnit();
        if (workUnitRequest != null) {
            workUnit.setWorkUnitName(workUnitRequest.getWorkUnitName());
            workUnit.setDescription(workUnitRequest.getDescription());
            // Assuming tenant mapping is required
//            workUnit.setTenant(tenantMapper.mapToEntity(workUnitRequest.getTenant()));
//            // Assuming job registration mapping is required
//            workUnit.setJobRegistration(jobRegistrationMapper.mapToEntity(workUnitRequest.getJobRegistration()));
        }
        return workUnit;
    }

    public WorkUnitResponse mapToDto(WorkUnit workUnit) {
        WorkUnitResponse response = new WorkUnitResponse();
        if (workUnit != null) {
            response.setId(workUnit.getId());
            response.setWorkUnitName(workUnit.getWorkUnitName());
            response.setDescription(workUnit.getDescription());
            // Assuming tenant ID mapping is required
            response.setTenantId(workUnit.getTenant().getId());
            // Assuming job registration ID mapping is required
            //response.setJobRegistrationId(workUnit.getJobRegistration().getId());
        }
        return response;
    }

    public WorkUnit updateWorkUnit(WorkUnit workUnit, WorkUnitRequest workUnitRequest) {
        if (workUnit != null && workUnitRequest != null) {
            workUnit.setWorkUnitName(workUnitRequest.getWorkUnitName());
            workUnit.setDescription(workUnitRequest.getDescription());
            // Assuming tenant mapping is required
//            workUnit.setTenant(tenantMapper.mapToEntity(workUnitRequest.getTenant()));
//            // Assuming job registration mapping is required
//            workUnit.setJobRegistration(jobRegistrationMapper.mapToEntity(workUnitRequest.getJobRegistration()));
        }
        return workUnit;
    }
}

