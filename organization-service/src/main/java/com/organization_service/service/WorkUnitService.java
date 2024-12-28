package com.organization_service.service;

import com.organization_service.dto.requestDto.WorkUnitRequest;
import com.organization_service.dto.responseDto.WorkUnitResponse;
import com.organization_service.model.Tenant;
import com.organization_service.model.WorkUnit;
import com.organization_service.exception.ResourceExistsException;
import com.organization_service.exception.ResourceNotFoundException;
import com.organization_service.mapper.WorkUnitMapper;
import com.organization_service.repository.TenantRepository;
import com.organization_service.repository.WorkUnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkUnitService {

    private final WorkUnitRepository workUnitRepository;
    private final WorkUnitMapper workUnitMapper;
    private final TenantRepository tenantRepository;

    public WorkUnitResponse createWorkUnit(UUID tenantId, WorkUnitRequest workUnitRequest) {
        Tenant tenant = getTenantById(tenantId);

        if (workUnitRepository.existsByWorkUnitNameAndTenantId(
                workUnitRequest.getWorkUnitName(), tenant.getId())) {
            throw new ResourceExistsException("WorkUnit with Name " +
                    workUnitRequest.getWorkUnitName() + " already exists");
        }

        WorkUnit workUnit = workUnitMapper.mapToEntity(workUnitRequest);
        workUnit.setTenant(tenant);

        workUnit = workUnitRepository.save(workUnit);
        return workUnitMapper.mapToDto(workUnit);
    }

    public List<WorkUnitResponse> getAllWorkUnits(UUID tenantId) {
        Tenant tenant = getTenantById(tenantId);

        List<WorkUnit> workUnits = workUnitRepository.findAll();
        return workUnits.stream()
                .filter(workUnit -> workUnit.getTenant().getId().equals(tenant.getId()))
                .map(workUnitMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public WorkUnitResponse getWorkUnitById(UUID id, UUID tenantId) {
        Tenant tenant = getTenantById(tenantId);

        WorkUnit workUnit = workUnitRepository.findById(id)
                .filter(wu -> wu.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "WorkUnit not found with id: " + id + " for the specified tenant"));

        return workUnitMapper.mapToDto(workUnit);
    }

    public WorkUnitResponse updateWorkUnit(UUID id, UUID tenantId, WorkUnitRequest workUnitRequest) {
        Tenant tenant = getTenantById(tenantId);

        WorkUnit workUnit = workUnitRepository.findById(id)
                .filter(wu -> wu.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "WorkUnit not found with id: " + id + " for the specified tenant"));

        workUnit = workUnitMapper.updateWorkUnit(workUnit, workUnitRequest);
        workUnit = workUnitRepository.save(workUnit);

        return workUnitMapper.mapToDto(workUnit);
    }

    public void deleteWorkUnit(UUID id, UUID tenantId) {
        Tenant tenant = getTenantById(tenantId);

        WorkUnit workUnit = workUnitRepository.findById(id)
                .filter(wu -> wu.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "WorkUnit not found with id: " + id + " for the specified tenant"));

        workUnitRepository.delete(workUnit);
    }

    private Tenant getTenantById(UUID tenantId) {
        return tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));
    }
}