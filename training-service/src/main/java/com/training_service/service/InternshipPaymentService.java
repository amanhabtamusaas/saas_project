package com.training_service.service;

import com.training_service.dto.clientDto.TenantDto;
import com.training_service.dto.request.InternshipPaymentRequest;
import com.training_service.dto.response.InternshipPaymentResponse;
import com.training_service.exception.ResourceExistsException;
import com.training_service.exception.ResourceNotFoundException;
import com.training_service.model.InternshipPayment;
import com.training_service.model.InternshipStudent;
import com.training_service.mapper.InternshipPaymentMapper;
import com.training_service.repository.InternshipPaymentRepository;
import com.training_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InternshipPaymentService {

    private final InternshipPaymentRepository internshipPaymentRepository;
    private final InternshipPaymentMapper internshipPaymentMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public InternshipPaymentResponse createInternshipPayment(UUID tenantId,
                                                             InternshipPaymentRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        InternshipStudent internshipStudent = validationUtil
                .getStudentById(tenant.getId(), request.getInternId());
        if (internshipPaymentRepository.existsByTenantIdAndInternshipStudentId(
                tenant.getId(), internshipStudent.getId())) {
            throw new ResourceExistsException(
                    "Internship student with id '" + request.getInternId() + "' already paid");
        }
        if (internshipStudent.getPlacedDepartmentId() == null) {
            throw new IllegalStateException(
                    "Cannot add payment for non-placed internship student.");
        }
        InternshipPayment internshipPayment = internshipPaymentMapper
                .mapToEntity(tenant, internshipStudent, request);
        internshipPayment = internshipPaymentRepository.save(internshipPayment);
        return internshipPaymentMapper.mapToDto(internshipPayment);
    }

    public List<InternshipPaymentResponse> getAllInternshipPayments(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<InternshipPayment> internshipPayments = internshipPaymentRepository.findAll();
        return internshipPayments.stream()
                .filter(payment -> payment.getTenantId().equals(tenant.getId()))
                .map(internshipPaymentMapper::mapToDto)
                .toList();
    }

    public InternshipPaymentResponse getInternshipPaymentById(UUID tenantId,
                                                              UUID paymentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        InternshipPayment internshipPayment = getPaymentById(tenant.getId(), paymentId);
        return internshipPaymentMapper.mapToDto(internshipPayment);
    }

    @Transactional
    public InternshipPaymentResponse updateInternshipPayment(UUID tenantId,
                                                             UUID paymentId,
                                                             InternshipPaymentRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        InternshipPayment internshipPayment = getPaymentById(tenant.getId(), paymentId);
        internshipPayment = internshipPaymentMapper.updateEntity(internshipPayment, request);
        internshipPayment = internshipPaymentRepository.save(internshipPayment);
        return internshipPaymentMapper.mapToDto(internshipPayment);
    }

    public InternshipPayment getPaymentById(UUID tenantId, UUID paymentId) {

        return internshipPaymentRepository.findById(paymentId)
                .filter(payment -> payment.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with id '" + paymentId + "'"));
    }
}