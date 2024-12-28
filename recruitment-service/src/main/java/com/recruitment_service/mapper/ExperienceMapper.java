package com.recruitment_service.mapper;

import com.recruitment_service.dto.clientDto.TenantDto;
import com.recruitment_service.dto.request.ExperienceRequest;
import com.recruitment_service.dto.response.ExperienceResponse;
import com.recruitment_service.model.Applicant;
import com.recruitment_service.model.Experience;
import com.recruitment_service.enums.ExperienceType;
import com.recruitment_service.utility.FileUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

@Component
public class ExperienceMapper {
    public Experience mapToEntity(TenantDto tenant,
                                  Applicant applicant,
                                  ExperienceRequest request,
                                  MultipartFile file) throws IOException {

        Experience experience = new Experience ();
        experience.setTenantId(tenant.getId());
        experience.setApplicant(applicant);
        experience.setInstitution (request.getInstitution ());
        experience.setLocationId(request.getLocationId());
        experience.setJobTitle (request.getJobTitle ());
        experience.setResponsibility (request.getResponsibility ());
        experience.setSalary (request.getSalary ());
        experience.setExperienceType(ExperienceType.valueOf(
                request.getExperienceType().toUpperCase()));
        experience.setStartDate (request.getStartDate ());
        experience.setEndDate (request.getEndDate ());
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
        response.setApplicantId(experience.getApplicant().getId());
        response.setInstitution (experience.getInstitution ());
        response.setLocationId(experience.getLocationId());
        response.setJobTitle (experience.getJobTitle ());
        response.setSalary (experience.getSalary ());
        response.setResponsibility (experience.getResponsibility ());
        response.setExperienceType(experience.getExperienceType().getExperienceType());
        response.setStartDate (experience.getStartDate ());
        response.setEndDate (experience.getEndDate ());
        response.setDuration (experience.getDuration ());
        response.setReasonForTermination (experience.getReasonForTermination ());
        response.setFileName (experience.getFileName ());
        response.setFileType (experience.getFileType ());
        response.setFileBytes (experience.getFileBytes ());
        response.setTenantId (experience.getTenantId ());
        response.setCreatedAt (experience.getCreatedAt ());
        response.setUpdatedAt (experience.getUpdatedAt ());
        response.setCreatedBy (experience.getCreatedBy ());
        response.setUpdatedBy (experience.getUpdatedBy ());

        return response;
    }

    public Experience updateExperience(Experience experience,
                                       ExperienceRequest request,
                                       MultipartFile file) throws IOException {

        if (request.getInstitution () != null) {
            experience.setInstitution(request.getInstitution());
        }
        if (request.getLocationId () != null) {
            experience.setLocationId(request.getLocationId());
        }
        if (request.getJobTitle () != null) {
            experience.setJobTitle(request.getJobTitle());
        }
        if (request.getSalary () != null) {
            experience.setSalary(request.getSalary());
        }
        if (request.getResponsibility () != null) {
            experience.setResponsibility(request.getResponsibility());
        }
        if (request.getExperienceType() != null) {
            experience.setExperienceType(ExperienceType.valueOf(
                    request.getExperienceType().toUpperCase()));
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
