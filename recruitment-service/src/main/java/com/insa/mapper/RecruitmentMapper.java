package com.insa.mapper;

import com.insa.dto.request.RecruitmentApproveRequest;
import com.insa.dto.request.RecruitmentRequest;
import com.insa.dto.response.RecruitmentResponse;
import com.insa.entity.Recruitment;
import com.insa.enums.RecruitmentMode;
import com.insa.enums.RecruitmentStatus;
import com.insa.enums.RecruitmentType;
import org.springframework.stereotype.Component;

@Component
public class RecruitmentMapper {

    public Recruitment mapToEntity(RecruitmentRequest request) {
        Recruitment recruitment = new Recruitment();
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

    public Recruitment updateRecruitment(Recruitment recruitment,
                                         RecruitmentRequest request) {
        if (request.getNumberOfEmployeesRequested() != null)
            recruitment.setNumberOfEmployeesRequested(request.getNumberOfEmployeesRequested());
        if (request.getRecruitmentType() != null)
            recruitment.setRecruitmentType(RecruitmentType.valueOf(
                request.getRecruitmentType().toUpperCase()));
        if (request.getRecruitmentMode() != null)
            recruitment.setRecruitmentMode(RecruitmentMode.valueOf(
                request.getRecruitmentMode().toUpperCase()));
        if (request.getRemark() != null)
            recruitment.setRemark(request.getRemark());

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
