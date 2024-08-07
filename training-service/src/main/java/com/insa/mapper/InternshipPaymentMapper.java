package com.insa.mapper;

import com.insa.dto.request.InternshipPaymentRequest;
import com.insa.dto.response.InternshipPaymentResponse;
import com.insa.entity.InternshipPayment;
import org.springframework.stereotype.Component;

@Component
public class InternshipPaymentMapper {

    public InternshipPayment mapToEntity(InternshipPaymentRequest request) {

        InternshipPayment internshipPayment = new InternshipPayment();
        internshipPayment.setReferenceLetter(request.getReferenceLetter());
        internshipPayment.setStartDate(request.getStartDate());
        internshipPayment.setEndDate(request.getEndDate());
        internshipPayment.setPaymentAmount(request.getPaymentAmount());
        internshipPayment.setRemark(request.getRemark());

        return internshipPayment;
    }

    public InternshipPaymentResponse mapToDto(InternshipPayment internshipPayment) {

        InternshipPaymentResponse response = new InternshipPaymentResponse();
        response.setId(internshipPayment.getId());
        response.setInternId(internshipPayment.getInternshipStudent().getId());
        response.setReferenceLetter(internshipPayment.getReferenceLetter());
        response.setStartDate(internshipPayment.getStartDate());
        response.setEndDate(internshipPayment.getEndDate());
        response.setPaymentAmount(internshipPayment.getPaymentAmount());
        response.setRemark(internshipPayment.getRemark());
        response.setTenantId(internshipPayment.getTenantId());
        response.setCreatedAt(internshipPayment.getCreatedAt());
        response.setUpdatedAt(internshipPayment.getUpdatedAt());
        response.setCreatedBy(internshipPayment.getCreatedBy());
        response.setUpdatedBy(internshipPayment.getUpdatedBy());

        return response;
    }

    public InternshipPayment updateEntity(InternshipPayment internshipPayment,
                                          InternshipPaymentRequest request) {

        if (request.getReferenceLetter() != null) {
            internshipPayment.setReferenceLetter(request.getReferenceLetter());
        }
        if (request.getStartDate() != null) {
            internshipPayment.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            internshipPayment.setEndDate(request.getEndDate());
        }
        if (request.getPaymentAmount() != null) {
            internshipPayment.setPaymentAmount(request.getPaymentAmount());
        }
        if (request.getRemark() != null) {
            internshipPayment.setRemark(request.getRemark());
        }

        return internshipPayment;
    }
}
