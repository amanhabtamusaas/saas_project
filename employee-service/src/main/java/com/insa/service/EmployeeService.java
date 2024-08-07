package com.insa.service;

import com.insa.dto.apiDto.DepartmentDto;
import com.insa.dto.apiDto.JobDto;
import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.EmployeeRequest;
import com.insa.dto.response.EmployeeResponse;
import com.insa.entity.Employee;
import com.insa.entity.TitleName;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.EmployeeMapper;
import com.insa.repository.EmployeeRepository;
import com.insa.repository.TitleNameRepository;
import com.insa.utility.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final TenantServiceClient tenantServiceClient;
    private final TitleNameRepository titleNameRepository;

    public EmployeeResponse addEmployee(Long tenantId,
                                        EmployeeRequest request,
                                        MultipartFile file) throws IOException {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        DepartmentDto department = tenantServiceClient
                .getDepartmentById(request.getDepartmentId(), tenant.getId ());
        JobDto job = tenantServiceClient
                .getJobById(request.getJobId(), tenant.getId ());
        TitleName titleName = titleNameRepository.findById(request.getTitleNameId())
                .filter(title -> title.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Title name not found with id '" + request.getTitleNameId() + "' in the specified tenant"));
        if (employeeRepository.existsByEmployeeIdAndTenantId (
                request.getEmployeeId(), tenant.getId ())) {
            throw new ResourceExistsException (
                    "Employee with id '" + request.getEmployeeId() + "' already exists in the specified tenant");
        }
        Employee employee = employeeMapper.mapToEntity (request, file);
        employee.setTenantId (tenant.getId ());
        employee.setDepartmentId (department.getId ());
        employee.setJobId (job.getId ());
        employee.setTitleName (titleName);
        employee = employeeRepository.save(employee);
        return employeeMapper.mapToDto (employee);
    }

    public List<EmployeeResponse> getAllEmployees(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<Employee> employees = employeeRepository.findAll ();
        return employees.stream ()
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .map ((employeeMapper::mapToDto))
                .toList();
    }

    public EmployeeResponse getEmployeeById(Long tenantId,
                                            Long employeeId) {
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Employee employee = employeeRepository.findById (employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        return employeeMapper.mapToDto (employee);
    }

    public EmployeeResponse getEmployeeByEmployeeId(Long tenantId,
                                                    String employeeId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        return employeeMapper.mapToDto(employee);
    }

    public EmployeeResponse getEmployeeByName(Long tenantId,
                                              String firstName,
                                              String middleName,
                                              String lastName) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Employee employee = employeeRepository.findByName(firstName, middleName, lastName)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Employee not found with name '" + firstName + " " + middleName + " " + lastName +
                                "' in the specified tenant"));
        return employeeMapper.mapToDto(employee);
    }

    public List<EmployeeResponse> getEmployeesByDepartmentId(Long tenantId,
                                                             Long departmentId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        DepartmentDto department = tenantServiceClient
                .getDepartmentById(departmentId, tenant.getId ());
        List<Employee> employees = employeeRepository
                .findByDepartmentId (department.getId ());
        return employees.stream()
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .map (employeeMapper::mapToDto)
                .toList();
    }

public byte[] getEmployeeProfileImageById(Long tenantId,
                                          Long employeeId,
                                          MediaType mediaType) {

    TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
    Employee employee = employeeRepository.findById(employeeId)
            .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Employee not found with id '" + employeeId + "' in the specified tenant"));

    switch (employee.getProfileImageType ()) {
        case "image/jpeg":
            mediaType = MediaType.valueOf (MediaType.IMAGE_JPEG_VALUE);
            break;
        case "image/png":
            mediaType = MediaType.valueOf (MediaType.IMAGE_PNG_VALUE);
            break;
        case "image/gif":
            mediaType = MediaType.valueOf (MediaType.IMAGE_GIF_VALUE);
            break;
        default:
            return "Unsupported file type".getBytes ();
    }
    return FileUtils.decompressFile (employee.getProfileImageBytes ());
}

    public EmployeeResponse updateEmployee(Long tenantId,
                                           Long employeeId,
                                           EmployeeRequest request,
                                           MultipartFile file) throws IOException {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById (employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        DepartmentDto department = tenantServiceClient
                .getDepartmentById(request.getDepartmentId(), tenant.getId ());
        JobDto job = tenantServiceClient
                .getJobById(request.getJobId(), tenant.getId ());
        TitleName titleName = titleNameRepository.findById(request.getTitleNameId())
                .filter(title -> title.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Title name not found with id '" + request.getTitleNameId() + "' in the specified tenant"));
        employee = employeeMapper.updateEmployee (employee, request, file);
        if (request.getDepartmentId () != null)
            employee.setDepartmentId (department.getId ());
        if (request.getJobId () != null)
            employee.setJobId (job.getId ());
        if (request.getTitleNameId() != null) {
            employee.setTitleName(titleName);
        }
        employee = employeeRepository.save (employee);
        return employeeMapper.mapToDto (employee);
    }

    public void deleteEmployee(Long tenantId,
                               Long employeeId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById (employeeId)
                .filter (emp -> emp.getTenantId ().equals (tenant.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        employeeRepository.delete (employee);
    }

}
