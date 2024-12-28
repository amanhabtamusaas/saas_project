package com.organization_service.service;

import com.organization_service.dto.requestDto.DepartmentRequest;
import com.organization_service.dto.responseDto.DepartmentResponse;
import com.organization_service.model.Department;
import com.organization_service.model.DepartmentHistory;
import com.organization_service.model.DepartmentType;
import com.organization_service.model.Tenant;
import com.organization_service.exception.ResourceExistsException;
import com.organization_service.exception.ResourceNotFoundException;
import com.organization_service.mapper.DepartmentMapper;
import com.organization_service.repository.DepartmentHistoryRepository;
import com.organization_service.repository.DepartmentRepository;
import com.organization_service.repository.DepartmentTypeRepository;
import com.organization_service.repository.TenantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

        private final DepartmentRepository departmentRepository;
        private final DepartmentMapper departmentMapper;
        private final TenantRepository tenantRepository;
        private final DepartmentHistoryRepository departmentHistoryRepository;
        private final DepartmentTypeRepository departmentTypeRepository;

        public DepartmentResponse createDepartment(UUID tenantId, DepartmentRequest departmentRequest) {

                if (tenantId == null || departmentRequest == null) {
                        throw new IllegalArgumentException("TenantId and DepartmentRequest must not be null");
                }

                // Retrieve the tenant from the repository
                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

                // Retrieve the department type from the repository
                DepartmentType departmentType = departmentTypeRepository
                        .findById(departmentRequest.getDepartmentTypeId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Department type not found with id: " + departmentRequest.getDepartmentTypeId()));

                // Check if department with the same name already exists
                if (departmentRepository.existsByDepartmentNameAndTenantId(
                        departmentRequest.getDepartmentName(), tenant.getId())) {
                        throw new ResourceExistsException("Department with Name " +
                                departmentRequest.getDepartmentName() + " already exists");
                }

                Department department = departmentMapper.mapToEntity(departmentRequest);
                department.setTenant(tenant);
                department.setDepartmentType(departmentType);

                department = departmentRepository.save(department);

                // Map the saved department to response DTO
                return departmentMapper.mapToDto(department);
        }

        public List<DepartmentResponse> getAllDepartments(UUID tenantId) {

                if (tenantId == null) {
                        throw new IllegalArgumentException("TenantId must not be null");
                }

                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

                List<Department> departments = departmentRepository.findAll();

                return departments.stream()
                        .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                        .map(departmentMapper::mapToDto)
                        .collect(Collectors.toList());
        }

        public DepartmentResponse getDepartmentById(UUID id, UUID tenantId) {

                validateIds(id, tenantId);

                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

                Department department = departmentRepository.findById(id)
                        .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                        .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id +
                                " for the specified tenant"));

                return departmentMapper.mapToDto(department);
        }

        private void validateIds(UUID id, UUID tenantId) {
                if (id == null || tenantId == null) {
                        throw new IllegalArgumentException("DepartmentId and TenantId must not be null");
                }
        }

        public DepartmentResponse updateDepartment(UUID id, UUID tenantId, DepartmentRequest departmentRequest) {

                validateIdsAndRequest(id, tenantId, departmentRequest);

                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));
                DepartmentType departmentType = departmentTypeRepository
                        .findById(departmentRequest.getDepartmentTypeId())
                        .orElseThrow(() -> new ResourceNotFoundException("Department type not found with id: " +
                                departmentRequest.getDepartmentTypeId()));

                Department department = departmentRepository.findById(id)
                        .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                        .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id +
                                " for the specified tenant"));

                saveDepartmentHistory(department, tenant);

                department = departmentMapper.updateDepartment(department, departmentRequest);
                department.setDepartmentType(departmentType);
                department = departmentRepository.save(department);

                return departmentMapper.mapToDto(department);
        }

        private void validateIdsAndRequest(UUID id, UUID tenantId, DepartmentRequest departmentRequest) {
                if (id == null || tenantId == null || departmentRequest == null) {
                        throw new IllegalArgumentException(
                                "DepartmentId, TenantId, and DepartmentRequest must not be null");
                }
        }

        private void saveDepartmentHistory(Department department, Tenant tenant) {
                DepartmentHistory departmentHistory = new DepartmentHistory();
                departmentHistory.setDepartment(department);
                departmentHistory.setTenant(tenant);
                departmentHistory.setDepartmentName(department.getDepartmentName());
                departmentHistory.setEstablishedDate(department.getEstablishedDate());
                departmentHistory.setCreatedAt(department.getCreatedAt());
                departmentHistory.setCreatedBy(department.getCreatedBy());
                departmentHistory.setUpdatedAt(department.getUpdatedAt());
                departmentHistory.setUpdatedBy(department.getUpdatedBy());

                departmentHistoryRepository.save(departmentHistory);
        }

        public void deleteDepartment(UUID id, UUID tenantId) {

                validateIds(id, tenantId);

                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

                Department department = departmentRepository.findById(id)
                        .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                        .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id +
                                " for the specified tenant"));

                departmentRepository.delete(department);
        }

        public DepartmentResponse addSubDepartment(UUID parentId, UUID tenantId,
                                                   DepartmentRequest subDepartmentRequest) {

                validateIds(parentId, tenantId);

                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

                Department parentDepartment = departmentRepository.findById(parentId)
                        .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                        .orElseThrow(() -> new ResourceNotFoundException("Parent department not found with id: " +
                                parentId + " for the specified tenant"));

                if (departmentRepository.existsByDepartmentNameAndParentDepartment(
                        subDepartmentRequest.getDepartmentName(), parentDepartment)) {
                        throw new ResourceExistsException("Sub-department with Name " +
                                subDepartmentRequest.getDepartmentName() +
                                " already exists under parent department with id: " + parentId);
                }

                Department subDepartment = departmentMapper.mapToEntity(subDepartmentRequest);
                subDepartment.setTenant(tenant);
                subDepartment.setParentDepartment(parentDepartment);
                subDepartment.setDepartmentType(parentDepartment.getDepartmentType());

                subDepartment = departmentRepository.save(subDepartment);
                return departmentMapper.mapToDto(subDepartment);
        }

        public void removeSubDepartment(UUID parentId, UUID tenantId, UUID subId) {

                validateIds(parentId, tenantId);

                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

                Department parentDepartment = departmentRepository.findById(parentId)
                        .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                        .orElseThrow(() -> new ResourceNotFoundException("Parent department not found with id: " +
                                parentId + " for the specified tenant"));

                Department subDepartment = departmentRepository.findById(subId)
                        .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                        .filter(dept -> dept.getParentDepartment() != null &&
                                dept.getParentDepartment().getId().equals(parentId))
                        .orElseThrow(() -> new ResourceNotFoundException("Sub-department not found with id: " +
                                subId + " for the specified parent department"));

                departmentRepository.delete(subDepartment);
        }

        public List<DepartmentResponse> getAllDepartmentsWithoutChildren() {
                List<Department> departments = departmentRepository.findAll();

                // Map departments to DTOs
                return departments.stream()
                        .map(departmentMapper::mapToDto)
                        .collect(Collectors.toList());
        }

        public Optional<Department> findById(UUID departmentId) {
                return departmentRepository.findById(departmentId);
        }

        public List<Department> getAllChildDepartments(UUID departmentId, UUID tenantId) {

                Department department = departmentRepository.findById(departmentId)
                        .filter(dept -> dept.getTenant().getId().equals(tenantId))
                        .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " +
                                departmentId + " for the specified tenant"));

                return department.getAllChildDepartments();
        }

        public List<DepartmentResponse> getAllParentDepartments(UUID tenantId) {

                List<Department> parentDepartments = departmentRepository.findAllParentDepartments();
                return parentDepartments.stream()
                        .map(departmentMapper::mapToDto)
                        .collect(Collectors.toList());
        }

        public DepartmentResponse transferChildDepartment(UUID tenantId, UUID childDepartmentId,
                                                          UUID newParentDepartmentId) {

                // Fetch the departments from the database
                Department childDepartment = departmentRepository.findById(childDepartmentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Child department not found"));
                Department newParentDepartment = departmentRepository.findById(newParentDepartmentId)
                        .orElseThrow(() -> new ResourceNotFoundException("New parent department not found"));
                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

                // Change the parent department
                childDepartment.setParentDepartment(newParentDepartment);

                // Save changes to the database
                childDepartment = departmentRepository.save(childDepartment);

                // Return the response DTO
                return departmentMapper.mapToDto(childDepartment);
        }

        private void saveDepartmentChangeHistory(Department childDepartment,
                                                 Department newParentDepartment) {
                DepartmentHistory departmentHistory = new DepartmentHistory();
                departmentHistory.setDepartment(childDepartment);
                departmentHistory.setDepartment(newParentDepartment);

                departmentHistoryRepository.save(departmentHistory);
        }

        @Transactional
        public DepartmentResponse transferParentDepartment(UUID tenantId, UUID parentDepartmentId,
                                                           UUID newParentDepartmentId) {

                // Fetch the departments from the database
                Department parentDepartment = departmentRepository.findById(parentDepartmentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Parent department not found"));
                Department newParentDepartment = departmentRepository.findById(newParentDepartmentId)
                        .orElseThrow(() -> new ResourceNotFoundException("New parent department not found"));
                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

                // Change the parent department for the parent department
                parentDepartment.setParentDepartment(newParentDepartment);

                // Save changes to the database
                parentDepartment = departmentRepository.save(parentDepartment);

                // Return the response DTO
                return departmentMapper.mapToDto(parentDepartment);
        }
}