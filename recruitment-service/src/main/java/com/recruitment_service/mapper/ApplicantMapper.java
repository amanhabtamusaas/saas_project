package com.recruitment_service.mapper;

import com.recruitment_service.dto.clientDto.TenantDto;
import com.recruitment_service.dto.request.ApplicantRequest;
import com.recruitment_service.dto.response.ApplicantResponse;
import com.recruitment_service.model.Applicant;
import com.recruitment_service.enums.Gender;
import com.recruitment_service.enums.MaritalStatus;
import com.recruitment_service.model.Recruitment;
import com.recruitment_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicantMapper {

    private final ValidationUtil validationUtil;

    public Applicant mapToEntity(TenantDto tenant,
                                 Recruitment recruitment,
                                 ApplicantRequest request)  {

        Applicant applicant = new Applicant();
        applicant.setTenantId(tenant.getId());
        applicant.setRecruitment(recruitment);
        applicant.setFirstName(request.getFirstName());
        applicant.setMiddleName(request.getMiddleName());
        applicant.setLastName(request.getLastName());
        applicant.setDateOfBirth(request.getDateOfBirth());
        applicant.setGender(Gender.valueOf(request.getGender().toUpperCase()));
        applicant.setMaritalStatus(MaritalStatus.valueOf(
                request.getMaritalStatus().toUpperCase()));
        applicant.setNationality(request.getNationality());
        applicant.setLocationId(request.getLocationId());
        applicant.setPhoneNumber(request.getPhoneNumber());
        applicant.setEmail(request.getEmail());
        applicant.setPoBox(request.getPoBox());
        applicant.setOfficeTelephone(request.getOfficeTelephone());
        applicant.setHomeTelephone(request.getHomeTelephone());
        applicant.setHouseNumber(request.getHouseNumber());
        applicant.setSkills(request.getSkills());
        applicant.setOtherInformation(request.getOtherInformation());
        applicant.setHobbies(request.getHobbies());

        /*if (file != null) {
            applicant.setProfileImageName(file.getOriginalFilename());
            applicant.setProfileImageType(file.getContentType());
            applicant.setProfileImageBytes(FileUtils.compressFile(file.getBytes()));
        }*/

        return applicant;
    }

    public ApplicantResponse mapToDto(Applicant applicant) {

        ApplicantResponse response = new ApplicantResponse();
        response.setId(applicant.getId());
        response.setFirstName(applicant.getFirstName());
        response.setMiddleName(applicant.getMiddleName());
        response.setLastName(applicant.getLastName());
        response.setDateOfBirth(applicant.getDateOfBirth());
        response.setGender(applicant.getGender().getGender());
        response.setMaritalStatus(applicant.getMaritalStatus().getMaritalStatus());
        response.setNationality(applicant.getNationality());
        response.setLocationId(applicant.getLocationId());
        response.setPhoneNumber(applicant.getPhoneNumber());
        response.setEmail(applicant.getEmail());
        response.setPoBox(applicant.getPoBox());
        response.setOfficeTelephone(applicant.getOfficeTelephone());
        response.setHomeTelephone(applicant.getHomeTelephone());
        response.setHouseNumber(applicant.getHouseNumber());
        response.setSkills(applicant.getSkills());
        response.setOtherInformation(applicant.getOtherInformation());
        response.setHobbies(applicant.getHobbies());
        response.setRecruitmentId(applicant.getRecruitment().getId());
        /*response.setProfileImageName(applicant.getProfileImageName());
        response.setProfileImageType(applicant.getProfileImageType());
        if (applicant.getProfileImageType() != null)
            response.setProfileImageBytes(FileUtils.decompressFile(
                    applicant.getProfileImageBytes()));*/
        response.setTenantId(applicant.getTenantId());
        response.setCreatedAt(applicant.getCreatedAt());
        response.setCreatedBy(applicant.getCreatedBy());
        response.setUpdatedAt(applicant.getUpdatedAt());
        response.setUpdatedBy(applicant.getUpdatedBy());

        return response;
    }

    public Applicant updateApplicant(TenantDto tenant,
                                     Applicant applicant,
                                     ApplicantRequest request) {

        Recruitment recruitment = validationUtil
                .getRecruitmentById(tenant.getId(), request.getRecruitmentId());

        if (request.getRecruitmentId() != null) {
            applicant.setRecruitment(recruitment);
        }
        if (request.getFirstName() != null) {
            applicant.setFirstName(request.getFirstName());
        }
        if (request.getMiddleName() != null) {
            applicant.setMiddleName(request.getMiddleName());
        }
        if (request.getLastName() != null) {
            applicant.setLastName(request.getLastName());
        }
        if (request.getDateOfBirth() != null) {
            applicant.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getGender() != null) {
            applicant.setGender(Gender.valueOf(request.getGender().toUpperCase()));
        }
        if (request.getMaritalStatus() != null) {
            applicant.setMaritalStatus(MaritalStatus.valueOf(
                    request.getMaritalStatus().toUpperCase()));
        }
        if (request.getNationality() != null) {
            applicant.setNationality(request.getNationality());
        }
        if (request.getLocationId() != null) {
            applicant.setLocationId(request.getLocationId());
        }
        if (request.getPhoneNumber() != null) {
            applicant.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getEmail() != null) {
            applicant.setEmail(request.getEmail());
        }
        if (request.getPoBox() != null) {
            applicant.setPoBox(request.getPoBox());
        }
        if (request.getOfficeTelephone() != null) {
            applicant.setOfficeTelephone(request.getOfficeTelephone());
        }
        if (request.getHomeTelephone() != null) {
            applicant.setHomeTelephone(request.getHomeTelephone());
        }
        if (request.getHouseNumber() != null) {
            applicant.setHouseNumber(request.getHouseNumber());
        }
        if (request.getSkills() != null) {
            applicant.setSkills(request.getSkills());
        }
        if (request.getOtherInformation() != null) {
            applicant.setOtherInformation(request.getOtherInformation());
        }
        if (request.getHobbies() != null) {
            applicant.setHobbies(request.getHobbies());
        }

        /*if (file != null && !file.isEmpty()) {
            applicant.setProfileImageName(file.getOriginalFilename());
            applicant.setProfileImageType(file.getContentType());
            applicant.setProfileImageBytes(FileUtils.compressFile(file.getBytes()));
        }*/

        return applicant;
    }
}
