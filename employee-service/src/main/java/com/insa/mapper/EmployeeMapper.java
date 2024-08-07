package com.insa.mapper;

import com.insa.dto.request.EmployeeRequest;
import com.insa.dto.response.EmployeeResponse;
import com.insa.entity.Employee;
import com.insa.enums.EmploymentType;
import com.insa.enums.Gender;
import com.insa.enums.MaritalStatus;
import com.insa.utility.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

@Component
public class EmployeeMapper {

    public Employee mapToEntity(EmployeeRequest request,
                                MultipartFile file) throws IOException {
        Employee employee = new Employee ();
        employee.setEmployeeId (request.getEmployeeId ());
        employee.setFirstName (request.getFirstName ());
        employee.setMiddleName (request.getMiddleName ());
        employee.setLastName (request.getLastName ());
        employee.setGender (Gender.valueOf(request.getGender ().toUpperCase()));
        employee.setDateOfBirth (request.getDateOfBirth ());
        employee.setDateOfBirth(request.getDateOfBirth ());
        employee.setMaritalStatus (MaritalStatus.valueOf(request.getMaritalStatus ().toUpperCase()));
        employee.setEmploymentType (EmploymentType.valueOf(request.getEmploymentType ().toUpperCase()));
        employee.setHiredDate (request.getHiredDate ());
        employee.setEndDate (request.getEndDate ());
        employee.setDutyStation (request.getDutyStation ());
        employee.setFydaNumber (request.getFydaNumber ());
        employee.setPassportNumber (request.getPassportNumber ());
        employee.setTinNumber (request.getTinNumber ());
        employee.setPensionNumber (request.getPensionNumber ());
        employee.setNationality (request.getNationality ());

        if (file != null) {
            employee.setProfileImageName (file.getOriginalFilename ());
            employee.setProfileImageType (file.getContentType ());
            employee.setProfileImageBytes (FileUtils.compressFile (file.getBytes ()));
        }

        return employee;
    }

    public EmployeeResponse mapToDto(Employee employee) {
        EmployeeResponse response = new EmployeeResponse ();
        response.setId (employee.getId ());
        response.setTenantId (employee.getTenantId ());
        response.setEmployeeId (employee.getEmployeeId ());
        response.setDepartmentId (employee.getDepartmentId ());
        response.setTitleNameId (employee.getTitleName().getId());
        response.setFirstName (employee.getFirstName ());
        response.setMiddleName (employee.getMiddleName ());
        response.setLastName (employee.getLastName ());
        response.setGender (employee.getGender ().getGender());
        response.setDateOfBirth (employee.getDateOfBirth ());
        response.setAge (calculateAge (employee.getDateOfBirth ()));
        response.setMaritalStatus (employee.getMaritalStatus ().getMaritalStatus());
        response.setEmploymentType (employee.getEmploymentType ().getEmploymentType());
        response.setJobId (employee.getJobId ());
        response.setDutyStation (employee.getDutyStation ());
        response.setNationality(employee.getNationality());
        response.setFydaNumber (employee.getFydaNumber ());
        response.setPassportNumber (employee.getPassportNumber ());
        response.setTinNumber (employee.getTinNumber ());
        response.setPensionNumber (employee.getPensionNumber ());
        response.setHiredDate (employee.getHiredDate ());
        response.setEndDate (employee.getEndDate ());
        response.setProfileImageName (employee.getProfileImageName ());
        response.setProfileImageType (employee.getProfileImageType ());
        response.setProfileImageBytes (employee.getProfileImageBytes ());
        response.setCreatedAt (employee.getCreatedAt ());
        response.setUpdatedAt (employee.getUpdatedAt ());
        response.setCreatedBy (employee.getCreatedBy ());
        response.setUpdatedBy (employee.getUpdatedBy ());

        return response;
    }

    public Employee updateEmployee(Employee employee,
                                   EmployeeRequest request,
                                   MultipartFile file) throws IOException {
        if (request.getEmployeeId () != null)
            employee.setEmployeeId (request.getEmployeeId ());
        if (request.getFirstName () != null)
            employee.setFirstName (request.getFirstName ());
        if (request.getMiddleName () != null)
            employee.setMiddleName (request.getMiddleName ());
        if (request.getLastName () != null)
            employee.setLastName (request.getLastName ());
        if (request.getGender () != null)
            employee.setGender (Gender.valueOf(request.getGender ().toUpperCase()));
        if (request.getDateOfBirth () != null)
            employee.setDateOfBirth(request.getDateOfBirth ());
        if (request.getMaritalStatus () != null)
            employee.setMaritalStatus (MaritalStatus.valueOf(request.getMaritalStatus ().toUpperCase()));
        if (request.getEmploymentType () != null)
            employee.setEmploymentType (EmploymentType.valueOf(request.getEmploymentType ().toUpperCase()));
        if (request.getHiredDate () != null)
            employee.setHiredDate (request.getHiredDate ());
        if (request.getEndDate () != null)
            employee.setEndDate (request.getEndDate ());
        if (request.getDutyStation () != null)
            employee.setDutyStation (request.getDutyStation ());
        if (request.getFydaNumber () != null)
            employee.setFydaNumber (request.getFydaNumber ());
        if(request.getPassportNumber () != null)
            employee.setPassportNumber (request.getPassportNumber ());
        if (request.getTinNumber () != null)
            employee.setTinNumber (request.getTinNumber ());
        if (request.getPensionNumber () != null)
            employee.setPensionNumber (request.getPensionNumber ());
        if (request.getNationality () != null)
            employee.setNationality (request.getNationality ());

        if (file != null && !file.isEmpty ()) {
            employee.setProfileImageName (file.getOriginalFilename ());
            employee.setProfileImageType (file.getContentType ());
            employee.setProfileImageBytes (FileUtils.compressFile (file.getBytes ()));
        }

        return employee;
    }

    public static int calculateAge(LocalDate dob) {
        LocalDate curDate = LocalDate.now();
        if (dob != null) {
            return Period.between(dob, curDate).getYears();
        } else {
            return 0;
        }
    }
}