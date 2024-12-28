package com.training_service.mapper;

import com.training_service.dto.clientDto.BudgetYearDto;
import com.training_service.dto.clientDto.LocationDto;
import com.training_service.dto.clientDto.TenantDto;
import com.training_service.dto.request.InternshipStudentRequest;
import com.training_service.dto.response.InternshipStudentResponse;
import com.training_service.model.InternshipStudent;
import com.training_service.enums.InternshipStatus;
import com.training_service.enums.Semester;
import com.training_service.model.University;
import com.training_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InternshipStudentMapper {

    private final ValidationUtil validationUtil;

    public InternshipStudent mapToEntity(TenantDto tenant,
                                         BudgetYearDto budgetYear,
                                         InternshipStudentRequest request) {

        LocationDto location = validationUtil.getLocationById(tenant.getId(), request.getLocationId());
        University university = validationUtil.getUniversityById(tenant.getId(), request.getUniversityId());

        InternshipStudent internshipStudent = new InternshipStudent();
        internshipStudent.setTenantId(tenant.getId());
        internshipStudent.setBudgetYearId(budgetYear.getId());
        internshipStudent.setLocationId(location.getId());
        internshipStudent.setUniversity(university);
        internshipStudent.setSemester(Semester.valueOf(request.getSemester().toUpperCase()));
        internshipStudent.setStartDate(request.getStartDate());
        internshipStudent.setEndDate(request.getEndDate());
        internshipStudent.setFirstName(request.getFirstName());
        internshipStudent.setMiddleName(request.getMiddleName());
        internshipStudent.setLastName(request.getLastName());
        internshipStudent.setIdNumber(request.getIdNumber());
        internshipStudent.setPhoneNumber(request.getPhoneNumber());
        internshipStudent.setStream(request.getStream());
        internshipStudent.setInternshipStatus(InternshipStatus.IN_PROGRESS);
        internshipStudent.setRemark(request.getRemark());

        return internshipStudent;
    }

    public InternshipStudentResponse mapToDto(InternshipStudent internshipStudent) {

        InternshipStudentResponse response = new InternshipStudentResponse();
        response.setId(internshipStudent.getId());
        response.setBudgetYearId(internshipStudent.getBudgetYearId());
        response.setSemester(internshipStudent.getSemester().name());
        response.setUniversityId(internshipStudent.getUniversity().getId());
        response.setStartDate(internshipStudent.getStartDate());
        response.setEndDate(internshipStudent.getEndDate());
        response.setFirstName(internshipStudent.getFirstName());
        response.setMiddleName(internshipStudent.getMiddleName());
        response.setLastName(internshipStudent.getLastName());
        response.setIdNumber(internshipStudent.getIdNumber());
        response.setPhoneNumber(internshipStudent.getPhoneNumber());
        response.setLocationId(internshipStudent.getLocationId());
        response.setPlacedDepartmentId(internshipStudent.getPlacedDepartmentId());
        response.setStream(internshipStudent.getStream());
        response.setInternshipStatus(internshipStudent.getInternshipStatus().getInternshipStatus());
        response.setRemark(internshipStudent.getRemark());
        response.setTenantId(internshipStudent.getTenantId());
        response.setCreatedAt(internshipStudent.getCreatedAt());
        response.setUpdatedAt(internshipStudent.getUpdatedAt());
        response.setCreatedBy(internshipStudent.getCreatedBy());
        response.setUpdatedBy(internshipStudent.getUpdatedBy());

        return response;
    }

    public InternshipStudent updateEntity(TenantDto tenant,
                                          InternshipStudent internshipStudent,
                                          InternshipStudentRequest request) {

        BudgetYearDto budgetYear = validationUtil.getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        LocationDto location = validationUtil.getLocationById(tenant.getId(), request.getLocationId());
        University university = validationUtil.getUniversityById(tenant.getId(), request.getUniversityId());

        if (request.getBudgetYearId() != null) {
            internshipStudent.setBudgetYearId(budgetYear.getId());
        }
        if (request.getLocationId() != null) {
            internshipStudent.setLocationId(location.getId());
        }
        if (request.getUniversityId() != null) {
            internshipStudent.setUniversity(university);
        }
        if (internshipStudent.getSemester() != null) {
            internshipStudent.setSemester(Semester.valueOf(request.getSemester().toUpperCase()));
        }
        if (request.getStartDate() != null) {
            internshipStudent.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            internshipStudent.setEndDate(request.getEndDate());
        }
        if (request.getFirstName() != null) {
            internshipStudent.setFirstName(request.getFirstName());
        }
        if (request.getMiddleName() != null) {
            internshipStudent.setMiddleName(request.getMiddleName());
        }
        if (request.getLastName() != null) {
            internshipStudent.setLastName(request.getLastName());
        }
        if (request.getIdNumber() != null) {
            internshipStudent.setIdNumber(request.getIdNumber());
        }
        if (request.getPhoneNumber() != null) {
            internshipStudent.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getStream() != null) {
            internshipStudent.setStream(request.getStream());
        }
        if (request.getRemark() != null) {
            internshipStudent.setRemark(request.getRemark());
        }

        return internshipStudent;
    }
}
