package com.insa.service;

import com.insa.dto.requestDto.DepartmentTypeRequest;
// import com.insa.dto.requestDto.LocationTypeRequest;
import com.insa.dto.responseDto.DepartmentTypeResponse;
// import com.insa.dto.responseDto.LocationTypeResponse;
import com.insa.entity.Department;
import com.insa.entity.DepartmentType;
// import com.insa.entity.LocationType;
import com.insa.entity.Tenant;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.DepartmentTypeMapper;
// import com.insa.mapper.LocationTypeMapper;
import com.insa.repository.DepartmentRepository;
import com.insa.repository.DepartmentTypeRepository;
// import com.insa.repository.LocationTypeRepository;
import com.insa.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentTypeService {
        private final DepartmentTypeRepository departmentTypeRepository;
        private final DepartmentTypeMapper departmentTypeMapper;
        private final TenantRepository tenantRepository;
        private final DepartmentRepository departmentRepository;

        public DepartmentTypeResponse createDepartmentType(Long tenantId,
                        DepartmentTypeRequest departmentTypeRequest) {
                Tenant tenant = tenantRepository.findById(tenantId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Tenant not found with id: " + tenantId + " "));
                if (departmentTypeRepository.existsByDepartmentTypeNameAndTenantId(departmentTypeRequest.getDepartmentTypeName(),tenant.getId())) {
                        throw new ResourceExistsException("Department type with Name " + departmentTypeRequest.getDepartmentTypeName() + " already exists");
                }
                DepartmentType departmentType = departmentTypeMapper.mapToEntity(departmentTypeRequest);
                departmentType.setTenant(tenant);
                departmentType = departmentTypeRepository.save(departmentType);
                return departmentTypeMapper.mapToDto(departmentType);
        }

        public List<DepartmentTypeResponse> getAllDepartmentTypes(Long tenantId) {
                Tenant tenant = tenantRepository.findById(tenantId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Tenant not found with id: " + tenantId + " "));
                List<DepartmentType> locationTypes = departmentTypeRepository.findAll();
                return locationTypes.stream()
                                .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                                .map(departmentTypeMapper::mapToDto)
                                .collect(Collectors.toList());
        }

        public DepartmentTypeResponse getDepartmentTypeById(Long id, Long tenantId) {
                Tenant tenant = tenantRepository.findById(tenantId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Tenant not found with id: " + tenantId + " "));
                DepartmentType locationType = departmentTypeRepository.findById(id)
                                .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Department not found with id: " + id + " for the specified tenant "));
                return departmentTypeMapper.mapToDto(locationType);
        }

        public DepartmentTypeResponse updateDepartmentType(Long id, Long tenantId,
                        DepartmentTypeRequest departmentTypeRequest) {
                Tenant tenant = tenantRepository.findById(tenantId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Tenant not found with id: " + tenantId + " "));
                DepartmentType location = departmentTypeRepository.findById(id)
                                .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "LocationType is not found with id: " + id
                                                                + " for the specified tenant "));
                location = departmentTypeMapper.updateDepartmentType(location, departmentTypeRequest);
                location = departmentTypeRepository.save(location);
                return departmentTypeMapper.mapToDto(location);
        }

        public void deleteDepartmentType(Long id, Long tenantId) {
                Tenant tenant = tenantRepository.findById(tenantId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Tenant not found with id: " + tenantId + " "));
                DepartmentType location = departmentTypeRepository.findById(id)
                                .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "DepartmentType is not found with id: " + id
                                                                + " for the specified tenant "));
                departmentTypeRepository.delete(location);
        }
}
