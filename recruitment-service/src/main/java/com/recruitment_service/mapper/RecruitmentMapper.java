package com.recruitment_service.mapper;

import com.recruitment_service.dto.clientDto.DepartmentDto;
import com.recruitment_service.dto.clientDto.EmployeeDto;
import com.recruitment_service.dto.clientDto.JobDto;
import com.recruitment_service.dto.clientDto.TenantDto;
import com.recruitment_service.dto.request.RecruitmentApproveRequest;
import com.recruitment_service.dto.request.RecruitmentRequest;
import com.recruitment_service.dto.response.RecruitmentResponse;
import com.recruitment_service.model.Recruitment;
import com.recruitment_service.enums.RecruitmentMode;
import com.recruitment_service.enums.RecruitmentStatus;
import com.recruitment_service.enums.RecruitmentType;
import com.recruitment_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecruitmentMapper {

    private final ValidationUtil validationUtil;

    public Recruitment mapToEntity(TenantDto tenant,
                                   RecruitmentRequest request) {

        EmployeeDto requester = validationUtil
                .getEmployeeByEmployeeId(tenant.getId(), request.getRequesterEmployeeId());
        DepartmentDto department = validationUtil
                .getDepartmentById(tenant.getId(), request.getDepartmentId());
        JobDto job = validationUtil.getJobById(tenant.getId(), request.getJobId());

        Recruitment recruitment = new Recruitment();
        recruitment.setTenantId(tenant.getId());
        recruitment.setDepartmentId(department.getId());
        recruitment.setRequesterEmployeeId(requester.getId());
        recruitment.setJobId(job.getId());
        recruitment.setNumberOfEmployeesRequested(request.getNumberOfEmployeesRequested());
        recruitment.setRecruitmentType(RecruitmentType.valueOf(
                request.getRecruitmentType().toUpperCase()));
        recruitment.setRecruitmentMode(RecruitmentMode.valueOf(
                request.getRecruitmentMode().toUpperCase()));
        recruitment.setRecruitmentStatus(RecruitmentStatus.PENDING);
        recruitment.setRemark(request.getRemark());

        return recruitment;
    }

    public RecruitmentResponse mapToDto(Recruitment recruitment) {
        RecruitmentResponse response = new RecruitmentResponse();
        response.setId(recruitment.getId());
        response.setRequesterEmployeeId(recruitment.getRequesterEmployeeId());
        response.setDepartmentId(recruitment.getDepartmentId());
        response.setJobId(recruitment.getJobId());
        response.setVacancyNumber(recruitment.getVacancyNumber());
        response.setNumberOfEmployeesRequested(recruitment.getNumberOfEmployeesRequested());
        response.setNumberOfEmployeesApproved(recruitment.getNumberOfEmployeesApproved());
        response.setRecruitmentType(recruitment.getRecruitmentType().getRecruitmentType());
        response.setRecruitmentMode(recruitment.getRecruitmentMode().getRecruitmentMode());
        response.setRecruitmentStatus(recruitment.getRecruitmentStatus().getRecruitmentStatus());
        response.setRemark(recruitment.getRemark());
        response.setTenantId (recruitment.getTenantId ());
        response.setCreatedAt (recruitment.getCreatedAt ());
        response.setUpdatedAt (recruitment.getUpdatedAt ());
        response.setCreatedBy (recruitment.getCreatedBy ());
        response.setUpdatedBy (recruitment.getUpdatedBy ());

        return response;
    }

    public Recruitment updateRecruitment(TenantDto tenant,
                                         Recruitment recruitment,
                                         RecruitmentRequest request) {

        EmployeeDto requester = validationUtil
                .getEmployeeByEmployeeId(tenant.getId(), request.getRequesterEmployeeId());
        DepartmentDto department = validationUtil
                .getDepartmentById(tenant.getId(), request.getDepartmentId());
        JobDto job = validationUtil.getJobById(tenant.getId(), request.getJobId());

        if (request.getDepartmentId() != null) {
            recruitment.setDepartmentId(department.getId());
        }
        if (request.getJobId() != null) {
            recruitment.setJobId(job.getId());
        }
        if (request.getRequesterEmployeeId() != null) {
            recruitment.setRequesterEmployeeId(requester.getId());
        }
        if (request.getNumberOfEmployeesRequested() != null) {
            recruitment.setNumberOfEmployeesRequested(request.getNumberOfEmployeesRequested());
        }
        if (request.getRecruitmentType() != null) {
            recruitment.setRecruitmentType(RecruitmentType.valueOf(
                    request.getRecruitmentType().toUpperCase()));
        }
        if (request.getRecruitmentMode() != null) {
            recruitment.setRecruitmentMode(RecruitmentMode.valueOf(
                    request.getRecruitmentMode().toUpperCase()));
        }
        if (request.getRemark() != null) {
            recruitment.setRemark(request.getRemark());
        }

        return recruitment;
    }

    public Recruitment approveRecruitment(Recruitment recruitment,
                                          RecruitmentApproveRequest request) {

        if (request.getVacancyNumber() != null) {
            recruitment.setVacancyNumber(request.getVacancyNumber());
            recruitment.setRecruitmentStatus(RecruitmentStatus.APPROVED);
        }
        if (request.getNumberOfEmployeesApproved() != null)
            recruitment.setNumberOfEmployeesApproved(request.getNumberOfEmployeesApproved());
        if (request.getDecision() != null) {
            recruitment.setRecruitmentStatus(RecruitmentStatus.valueOf(request.getDecision().toUpperCase()));
        }
        if (request.getRemark() != null) {
            recruitment.setRemark(request.getRemark());
        }

        return recruitment;
    }
}
