package com.insa.service;

import com.insa.dto.requestDto.WorkUnitRequest;
import com.insa.dto.responseDto.WorkUnitResponse;
import com.insa.entity.Tenant;
import com.insa.entity.WorkUnit;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.WorkUnitMapper;
import com.insa.repository.TenantRepository;
import com.insa.repository.WorkUnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkUnitService {

    private final WorkUnitRepository workUnitRepository;
    private final WorkUnitMapper workUnitMapper;
    private final TenantRepository tenantRepository;

    public WorkUnitResponse createWorkUnit(Long tenantId, WorkUnitRequest workUnitRequest) {
        Tenant tenant = getTenantById(tenantId);
        if (workUnitRepository.existsByWorkUnitNameAndTenantId(workUnitRequest.getWorkUnitName(),tenant.getId())) {
            throw new ResourceExistsException("WorkUnit with Name " + workUnitRequest.getWorkUnitName() + " already exists");
        }

        WorkUnit workUnit = workUnitMapper.mapToEntity(workUnitRequest);
        workUnit.setTenant(tenant);

        workUnit = workUnitRepository.save(workUnit);

        return workUnitMapper.mapToDto(workUnit);
    }

    public List<WorkUnitResponse> getAllWorkUnits(Long tenantId) {
        Tenant tenant = getTenantById(tenantId);

        List<WorkUnit> workUnits = workUnitRepository.findAll();
        return workUnits.stream()
                .filter(workUnit -> workUnit.getTenant().getId().equals(tenant.getId()))
                .map(workUnitMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public WorkUnitResponse getWorkUnitById(Long id, Long tenantId) {
        Tenant tenant = getTenantById(tenantId);

        WorkUnit workUnit = workUnitRepository.findById(id)
                .filter(wu -> wu.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "WorkUnit not found with id: " + id + " for the specified tenant"));

        return workUnitMapper.mapToDto(workUnit);
    }

    public WorkUnitResponse updateWorkUnit(Long id, Long tenantId, WorkUnitRequest workUnitRequest) {
        Tenant tenant = getTenantById(tenantId);

        WorkUnit workUnit = workUnitRepository.findById(id)
                .filter(wu -> wu.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "WorkUnit not found with id: " + id + " for the specified tenant"));

        workUnit = workUnitMapper.updateWorkUnit(workUnit, workUnitRequest);
        workUnit = workUnitRepository.save(workUnit);

        return workUnitMapper.mapToDto(workUnit);
    }

    public void deleteWorkUnit(Long id, Long tenantId) {
        Tenant tenant = getTenantById(tenantId);

        WorkUnit workUnit = workUnitRepository.findById(id)
                .filter(wu -> wu.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "WorkUnit not found with id: " + id + " for the specified tenant"));

        workUnitRepository.delete(workUnit);
    }

    private Tenant getTenantById(Long tenantId) {
        return tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));
    }
}
