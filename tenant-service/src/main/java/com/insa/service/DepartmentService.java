package com.insa.service;

import com.insa.dto.requestDto.DepartmentRequest;
import com.insa.dto.responseDto.DepartmentResponse;
import com.insa.entity.Department;
import com.insa.entity.DepartmentHistory;
import com.insa.entity.DepartmentType;
import com.insa.entity.Tenant;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.DepartmentMapper;
import com.insa.repository.DepartmentHistoryRepository;
import com.insa.repository.DepartmentRepository;
import com.insa.repository.DepartmentTypeRepository;
import com.insa.repository.TenantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

        private final DepartmentRepository departmentRepository;
        private final DepartmentMapper departmentMapper;
        private final TenantRepository tenantRepository;
        private final DepartmentHistoryRepository departmentHistoryRepository;
        private final DepartmentTypeRepository departmentTypeRepository;

        // 1. createDepartment Method
        // 1. createDepartment Method
        public DepartmentResponse createDepartment(Long tenantId, DepartmentRequest departmentRequest) {
                // Check if tenantId and departmentRequest are provided
                if (tenantId == null || departmentRequest == null) {
                        throw new IllegalArgumentException("TenantId and DepartmentRequest must not be null");
                }

                // Retrieve the tenant from the repository
                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

                // Retrieve the department type from the repository
                DepartmentType departmentType = departmentTypeRepository.findById(departmentRequest.getDepartmentTypeId())
                        .orElseThrow(() -> new ResourceNotFoundException("Department type not found with id: " + departmentRequest.getDepartmentTypeId()));

                // Check if department with the same name already exists
                if (departmentRepository.existsByDepartmentNameAndTenantId(departmentRequest.getDepartmentName(),tenant.getId())) {
                        throw new ResourceExistsException("Department with Name " + departmentRequest.getDepartmentName() + " already exists");
                }

                Department department = departmentMapper.mapToEntity(departmentRequest);
                department.setTenant(tenant);
                department.setDepartmentType(departmentType);

                department = departmentRepository.save(department);

                // Map the saved department to response DTO
                return departmentMapper.mapToDto(department);
        }

        // 2. getAllDepartments Method
        public List<DepartmentResponse> getAllDepartments(Long tenantId) {
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


        public DepartmentResponse getDepartmentById(Long id, Long tenantId) {
                validateIds(id, tenantId);

                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

                Department department = departmentRepository.findById(id)
                        .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                        .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id + " for the specified tenant"));

                return departmentMapper.mapToDto(department);
        }

        private void validateIds(Long id, Long tenantId) {
                if (id == null || tenantId == null) {
                        throw new IllegalArgumentException("DepartmentId and TenantId must not be null");
                }
        }

        // 4. updateDepartment Method
        public DepartmentResponse updateDepartment(Long id, Long tenantId, DepartmentRequest departmentRequest) {
                validateIdsAndRequest(id, tenantId, departmentRequest);

                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));
                DepartmentType departmentType = departmentTypeRepository.findById(departmentRequest.getDepartmentTypeId())
                        .orElseThrow(() -> new ResourceNotFoundException("Department type not found with id: " + departmentRequest.getDepartmentTypeId()));

                Department department = departmentRepository.findById(id)
                        .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                        .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id + " for the specified tenant"));

                saveDepartmentHistory(department, tenant);

                department = departmentMapper.updateDepartment(department, departmentRequest);
                department.setDepartmentType(departmentType);
                department = departmentRepository.save(department);

                return departmentMapper.mapToDto(department);
        }

        private void validateIdsAndRequest(Long id, Long tenantId, DepartmentRequest departmentRequest) {
                if (id == null || tenantId == null || departmentRequest == null) {
                        throw new IllegalArgumentException("DepartmentId, TenantId, and DepartmentRequest must not be null");
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

        // 5. deleteDepartment Method
        public void deleteDepartment(Long id, Long tenantId) {
                validateIds(id, tenantId);

                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

                Department department = departmentRepository.findById(id)
                        .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                        .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id + " for the specified tenant"));


                departmentRepository.delete(department);
        }


        public DepartmentResponse addSubDepartment(Long parentId, Long tenantId, DepartmentRequest subDepartmentRequest) {
                validateIds(parentId, tenantId);

                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

                Department parentDepartment = departmentRepository.findById(parentId)
                        .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                        .orElseThrow(() -> new ResourceNotFoundException("Parent department not found with id: " + parentId + " for the specified tenant"));

                if (departmentRepository.existsByDepartmentNameAndParentDepartment(subDepartmentRequest.getDepartmentName(), parentDepartment)) {
                        throw new ResourceExistsException("Sub-department with Name " + subDepartmentRequest.getDepartmentName() + " already exists under parent department with id: " + parentId);
                }

                Department subDepartment = departmentMapper.mapToEntity(subDepartmentRequest);
                subDepartment.setTenant(tenant);
                subDepartment.setParentDepartment(parentDepartment);
                subDepartment.setDepartmentType(parentDepartment.getDepartmentType());

                subDepartment = departmentRepository.save(subDepartment);
                return departmentMapper.mapToDto(subDepartment);
        }

        // 9. removeSubDepartment Method
        // 9. removeSubDepartment Method
        public void removeSubDepartment(Long parentId, Long tenantId, Long subId) {
                // Check if parentId, tenantId, and subId are provided
                if (parentId == null || tenantId == null || subId == null) {
                        throw new IllegalArgumentException("ParentId, TenantId, and SubId must not be null");
                }

                // Retrieve the tenant from the repository
                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

                // Retrieve the parent department by id, ensuring it belongs to the specified tenant
                Department parentDepartment = departmentRepository.findById(parentId)
                        .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                        .orElseThrow(() -> new ResourceNotFoundException("Parent department not found with id: " + parentId + " for the specified tenant"));

                // Retrieve the sub department by id, ensuring it belongs to the specified tenant and is a child of the parent department
                Department subDepartment = departmentRepository.findById(subId)
                        .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                        .filter(dept -> dept.getParentDepartment() != null && dept.getParentDepartment().getId().equals(parentId))
                        .orElseThrow(() -> new ResourceNotFoundException("Sub-department not found with id: " + subId + " for the specified parent department"));

                // Delete the sub department
                departmentRepository.delete(subDepartment);
        }

        public List<DepartmentResponse> getAllDepartmentsWithoutChildren() {
                List<Department> departments = departmentRepository.findAll();

                // Map departments to DTOs
                return departments.stream()
                        .map(departmentMapper::mapToDto)
                        .collect(Collectors.toList());
        }

        public Optional<Department> findById(Long departmentId) {
                return departmentRepository.findById(departmentId);
        }

        public List<Department> getAllChildDepartments(Long departmentId,Long tenantId) {
                //Department department = departmentRepository.findById(departmentId,tenantId).orElseThrow(() -> new RuntimeException("Department not found"));
                Department department = departmentRepository.findById(departmentId)
                      .filter(dept -> dept.getTenant().getId().equals(tenantId))
                       .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId + " for the specified tenant"));

                return department.getAllChildDepartments();
        }
        public List<DepartmentResponse> getAllParentDepartments(@PathVariable Long tenantId) {
                List<Department> parentDepartments = departmentRepository.findAllParentDepartments();
                return parentDepartments.stream()
                        .map(departmentMapper::mapToDto)
                        .collect(Collectors.toList());
        }

        public DepartmentResponse transferChildDepartment(Long tenantId,Long childDepartmentId, Long newParentDepartmentId) {
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

//                // Update the department using the mapper
//                childDepartment = departmentMapper.updateDepartment(childDepartment, departmentRequest);
//                childDepartment.setDepartmentType(departmentType);

                // Save the final state of the department
                childDepartment = departmentRepository.save(childDepartment);

                // Return the response DTO
                return departmentMapper.mapToDto(childDepartment);
        }
        private void saveDepartmentChangeHistory( Department childDepartment, Department newParentDepartment) {
                DepartmentHistory departmentHistory = new DepartmentHistory();
                departmentHistory.setDepartment(childDepartment);
                departmentHistory.setDepartment(newParentDepartment);

                departmentHistoryRepository.save(departmentHistory);
        }
        @Transactional
        public DepartmentResponse transferParentDepartment( Long tenantId,Long parentDepartmentId, Long newParentDepartmentId) {
                // Fetch the departments from the database
                Department parentDepartment = departmentRepository.findById(parentDepartmentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Parent department not found"));
                Department newParentDepartment = departmentRepository.findById(newParentDepartmentId)
                        .orElseThrow(() -> new ResourceNotFoundException("New parent department not found"));
                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));


                // Update each child department to point to the new parent department
//                for (Department child : parentDepartment.getAllChildDepartments()) {
//                        child.setParentDepartment(newParentDepartment);
//                        departmentRepository.save(child);
//                }

                // Change the parent department for the parent department
                parentDepartment.setParentDepartment(newParentDepartment);

                // Save changes to the database
                parentDepartment = departmentRepository.save(parentDepartment);

//                // Update the department using the mapper
//                departmentMapper.updateDepartmentFromRequest(departmentRequest, parentDepartment);
//                parentDepartment.setDepartmentType(departmentType);

                // Save the final state of the department
                parentDepartment = departmentRepository.save(parentDepartment);

                // Return the response DTO
                return departmentMapper.mapToDto(parentDepartment);
        }



}

