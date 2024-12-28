package com.employee_service.mapper;

import com.employee_service.dto.clientDto.TenantDto;
import com.employee_service.dto.request.ExperienceRequest;
import com.employee_service.dto.response.ExperienceResponse;
import com.employee_service.model.Employee;
import com.employee_service.model.Experience;
import com.employee_service.enums.EmploymentType;
import com.employee_service.utility.FileUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

@Component
public class ExperienceMapper {

    public Experience mapToEntity(TenantDto tenant,
                                  Employee employee,
                                  ExperienceRequest request,
                                  MultipartFile file) throws IOException {

        Experience experience = new Experience ();
        experience.setTenantId(tenant.getId());
        experience.setEmployee(employee);
        experience.setInstitution (request.getInstitution ());
        experience.setEmploymentType (EmploymentType.valueOf(request.getEmploymentType ().toUpperCase()));
        experience.setJobTitle (request.getJobTitle ());
        experience.setSalary (request.getSalary ());
        experience.setStartDate (request.getStartDate ());
        experience.setEndDate (request.getEndDate ());
        experience.setResponsibility (request.getResponsibility ());
        experience.setReasonForTermination (request.getReasonForTermination ());

        experience.setDuration (calculateDuration (
                request.getStartDate (), request.getEndDate ()));

        if (file != null){
            experience.setFileName (file.getOriginalFilename ());
            experience.setFileType (file.getContentType ());
            experience.setFileBytes (FileUtil.compressFile (file.getBytes ()));
        }

        return experience;
    }

    public ExperienceResponse mapToDto(Experience experience) {

        ExperienceResponse response = new ExperienceResponse ();
        response.setId (experience.getId ());
        response.setTenantId (experience.getTenantId ());
        response.setInstitution (experience.getInstitution ());
        response.setEmploymentType (experience.getEmploymentType ().name());
        response.setJobTitle (experience.getJobTitle ());
        response.setSalary (experience.getSalary ());
        response.setStartDate (experience.getStartDate ());
        response.setEndDate (experience.getEndDate ());
        response.setDuration (experience.getDuration ());
        response.setResponsibility (experience.getResponsibility ());
        response.setReasonForTermination (experience.getReasonForTermination ());
        response.setFileName (experience.getFileName ());
        response.setFileType (experience.getFileType ());
        response.setFileBytes (experience.getFileBytes ());
        response.setCreatedAt (experience.getCreatedAt ());
        response.setUpdatedAt (experience.getUpdatedAt ());
        response.setCreatedBy (experience.getCreatedBy ());
        response.setUpdatedBy (experience.getUpdatedBy ());
        response.setEmployeeId (experience.getEmployee ().getId ());

        return response;
    }

    public Experience updateExperience(Experience experience,
                                       ExperienceRequest request,
                                       MultipartFile file) throws IOException {

        if (request.getInstitution () != null) {
            experience.setInstitution(request.getInstitution());
        }
        if (request.getEmploymentType () != null) {
            experience.setEmploymentType(EmploymentType.valueOf(request.getEmploymentType().toUpperCase()));
        }
        if (request.getJobTitle () != null) {
            experience.setJobTitle(request.getJobTitle());
        }
        if (request.getSalary () != null) {
            experience.setSalary(request.getSalary());
        }
        if (request.getStartDate () != null) {
            experience.setStartDate(request.getStartDate());
        }
        if (request.getEndDate () != null) {
            experience.setEndDate(request.getEndDate());
        }
        if (request.getStartDate () != null) {
            experience.setStartDate(request.getStartDate());
        }
        if (request.getResponsibility () != null) {
            experience.setResponsibility(request.getResponsibility());
        }
        if (request.getReasonForTermination () != null) {
            experience.setReasonForTermination(request.getReasonForTermination());
        }

        experience.setDuration (calculateDuration (
                request.getStartDate (), request.getEndDate ()));

        if (file != null && !file.isEmpty ()){
            experience.setFileName (file.getOriginalFilename ());
            experience.setFileType (file.getContentType ());
            experience.setFileBytes (FileUtil.compressFile (file.getBytes ()));
        }

        return experience;
    }

    private static String calculateDuration(LocalDate startDate, LocalDate endDate) {

        Period period = Period.between(startDate, endDate);
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays ();

        String daysString = (days > 1) ? "days" : "day";

        return years + " year, "  +
                months + " month and " +  days + " " + daysString;
    }
}
