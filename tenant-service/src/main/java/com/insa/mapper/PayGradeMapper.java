package com.insa.mapper;

import com.insa.dto.requestDto.PayGradeRequest;
import com.insa.dto.responseDto.PayGradeResponse;
import com.insa.entity.JobGrade;
import com.insa.entity.PayGrade;
import com.insa.entity.Qualification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayGradeMapper {

    @Autowired
    private TenantMapper tenantMapper;

    public PayGrade mapToEntity(PayGradeRequest payGradeRequest) {
        PayGrade payGrade = new PayGrade ();
        JobGrade jobGrade=new JobGrade();
        jobGrade.setId(payGradeRequest.getJobGradeId());
        payGrade.setJobGrade(jobGrade);
        payGrade.setInitialSalary (payGradeRequest.getInitialSalary ());
        payGrade.setMaximumSalary (payGradeRequest.getMaximumSalary ());
        payGrade.setSalary(payGradeRequest.getSalary());
        payGrade.setSalaryStep (payGradeRequest.getSalaryStep ());
        payGrade.setDescription (payGradeRequest.getDescription ());

        return payGrade;
    }

    public PayGradeResponse mapToDto(PayGrade payGrade) {
        PayGradeResponse response = new PayGradeResponse ();
        response.setId (payGrade.getId ());
      //  response.setPayGrade (payGrade.getPayGrade ());
        response.setInitialSalary (payGrade.getInitialSalary ());
        response.setMaximumSalary (payGrade.getMaximumSalary ());
        response.setSalary(payGrade.getSalary());
        response.setSalaryStep (payGrade.getSalaryStep ());
        response.setDescription (payGrade.getDescription ());
        response.setCreatedAt (payGrade.getCreatedAt ());
        response.setUpdatedAt (payGrade.getUpdatedAt ());
        response.setCreatedBy (payGrade.getCreatedBy ());
        response.setUpdatedBy (payGrade.getUpdatedBy ());
        response.setTenantId (payGrade.getTenant ().getId ());
        response.setJobGradeId(payGrade.getJobGrade().getId());

        return response;
    }

    public PayGrade updatePayGrade(PayGrade payGrade, PayGradeRequest payGradeRequest) {
//        if (payGradeRequest.getPayGrade () != null)
//            payGrade.setPayGrade (payGradeRequest.getPayGrade ());
        if (payGradeRequest.getInitialSalary () != null)
            payGrade.setInitialSalary (payGradeRequest.getInitialSalary ());
        if (payGradeRequest.getMaximumSalary () != null)
            payGrade.setMaximumSalary (payGradeRequest.getMaximumSalary ());
        if (payGradeRequest.getSalaryStep () != null)
            payGrade.setSalaryStep (payGradeRequest.getSalaryStep ());
        if (payGradeRequest.getSalary () != null)
            payGrade.setSalary (payGradeRequest.getSalary ());
        if (payGradeRequest.getDescription () != null)
            payGrade.setDescription (payGradeRequest.getDescription ());
        if (payGradeRequest.getJobGradeId() != null) {
            JobGrade jobGrade=new JobGrade();
            jobGrade.setId(payGradeRequest.getJobGradeId());
            payGrade.setJobGrade(jobGrade);
        }

        return payGrade;
    }
}
