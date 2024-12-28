package com.employee_service.service;

import com.employee_service.client.AuthServiceClient;
import com.employee_service.dto.clientDto.*;
import com.employee_service.dto.request.EmployeeRequest;
import com.employee_service.dto.response.EmployeeResponse;
import com.employee_service.model.Employee;
import com.employee_service.exception.ResourceExistsException;
import com.employee_service.exception.ResourceNotFoundException;
import com.employee_service.mapper.EmployeeMapper;
import com.employee_service.repository.EmployeeRepository;
import com.employee_service.utility.FileUtil;
import com.employee_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final ValidationUtil validationUtil;
    private final AuthServiceClient authServiceClient;

    @Transactional
    public EmployeeResponse addEmployee(UUID tenantId,
                                        EmployeeRequest request,
                                        MultipartFile file) throws IOException {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        if (employeeRepository.existsByEmployeeIdAndTenantId(
                request.getEmployeeId(), tenant.getId())) {
            throw new ResourceExistsException(
                    "Employee with id '" + request.getEmployeeId() + "' already exists");
        }
        Employee employee = employeeMapper.mapToEntity(tenant, request, file);
        employee = employeeRepository.save(employee);
        //authServiceClient.createUser(tenant.getId(), employee.getEmployeeId());
        return employeeMapper.mapToDto(employee);
    }

    public List<EmployeeResponse> getAllEmployees(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .filter(emp -> emp.getTenantId().equals(tenant.getId()))
                .map(employeeMapper::mapToDto)
                .toList();
    }

    public EmployeeResponse getEmployeeById(UUID tenantId,
                                            UUID employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        return employeeMapper.mapToDto(employee);
    }

    public EmployeeResponse getEmployeeByEmployeeId(UUID tenantId,
                                                    String employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeByEmployeeId(tenant.getId(), employeeId);
        return employeeMapper.mapToDto(employee);
    }

    public EmployeeResponse getEmployeeByName(UUID tenantId,
                                              String firstName,
                                              String middleName,
                                              String lastName) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = employeeRepository.findByName(firstName, middleName, lastName)
                .filter(emp -> emp.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with name '" + firstName + " " + middleName + " " + lastName + "'"));
        return employeeMapper.mapToDto(employee);
    }

    public List<EmployeeResponse> getEmployeesByDepartmentId(UUID tenantId,
                                                             UUID departmentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        DepartmentDto department = validationUtil.getDepartmentById(tenant.getId(), departmentId);
        List<Employee> employees = employeeRepository.findByDepartmentId(department.getId());
        return employees.stream()
                .filter(emp -> emp.getTenantId().equals(tenant.getId()))
                .map(employeeMapper::mapToDto)
                .toList();
    }

    public byte[] getEmployeeProfileImageById(UUID tenantId,
                                              UUID employeeId,
                                              MediaType mediaType) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        switch (employee.getProfileImageType()) {
            case "image/jpeg":
                mediaType = MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "image/png":
                mediaType = MediaType.valueOf(MediaType.IMAGE_PNG_VALUE);
                break;
            case "image/gif":
                mediaType = MediaType.valueOf(MediaType.IMAGE_GIF_VALUE);
                break;
            default:
                throw new IllegalArgumentException("Unsupported file type: " + employee.getProfileImageType());
        }
        byte[] fileBytes = FileUtil.decompressFile(employee.getProfileImageBytes());
        if (fileBytes.length == 0) {
            throw new ResourceNotFoundException("Image data is not available");
        }
        return fileBytes;
    }

    @Transactional
    public EmployeeResponse updateEmployee(UUID tenantId,
                                           UUID employeeId,
                                           EmployeeRequest request,
                                           MultipartFile file) throws IOException {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        employee = employeeMapper.updateEmployee(tenant, employee, request, file);
        employee = employeeRepository.save(employee);
        String username = employee.getEmployeeId();
        UserDto user = authServiceClient.getUserByUsername(tenant.getId(), username);
        authServiceClient.updateUser(tenant.getId(), user.getId(), employee.getId());
        return employeeMapper.mapToDto(employee);
    }

    @Transactional
    public void deleteEmployee(UUID tenantId, UUID employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        String username = employee.getEmployeeId();
        UserDto user = authServiceClient.getUserByUsername(tenant.getId(), username);
        authServiceClient.deleteUser(tenant.getId(), user.getId());
        employeeRepository.delete(employee);
    }
}