package com.insa.mapper;

import com.insa.dto.request.InternshipStudentRequest;
import com.insa.dto.response.InternshipStudentResponse;
import com.insa.entity.InternshipStudent;
import com.insa.enums.InternshipStatus;
import com.insa.enums.Semester;
import org.springframework.stereotype.Component;

@Component
public class InternshipStudentMapper {

    public InternshipStudent mapToEntity(InternshipStudentRequest request) {

        InternshipStudent internshipStudent = new InternshipStudent();
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

    public InternshipStudent updateEntity(InternshipStudent internshipStudent,
                                          InternshipStudentRequest request) {

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
