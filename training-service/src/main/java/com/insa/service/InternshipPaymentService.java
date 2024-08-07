package com.insa.service;

import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.InternshipPaymentRequest;
import com.insa.dto.response.InternshipPaymentResponse;
import com.insa.entity.InternshipPayment;
import com.insa.entity.InternshipStudent;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.InternshipPaymentMapper;
import com.insa.repository.InternshipPaymentRepository;
import com.insa.repository.InternshipStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipPaymentService {

    private final InternshipPaymentRepository internshipPaymentRepository;
    private final InternshipPaymentMapper internshipPaymentMapper;
    private final InternshipStudentRepository internshipStudentRepository;
    private final TenantServiceClient tenantServiceClient;

    @Transactional
    public InternshipPaymentResponse createInternshipPayment(Long tenantId,
                                                             InternshipPaymentRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        InternshipStudent internshipStudent = internshipStudentRepository.findById(request.getInternId())
                .filter(intern -> intern.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Internship student not found with id '" + request.getInternId() +
                                "' in the specified tenant"));
        if (internshipPaymentRepository.existsByTenantIdAndInternshipStudentId(
                tenant.getId(), internshipStudent.getId())) {
            throw new ResourceExistsException(
                    "Internship student with id '" + request.getInternId() + "' already paid");
        }
        if (internshipStudent.getPlacedDepartmentId() == null) {
            throw new IllegalStateException(
                    "Cannot add payment for non-placed internship student.");
        }
        InternshipPayment internshipPayment = internshipPaymentMapper.mapToEntity(request);
        internshipPayment.setTenantId(tenant.getId());
        internshipPayment.setInternshipStudent(internshipStudent);
        internshipPayment = internshipPaymentRepository.save(internshipPayment);
        return internshipPaymentMapper.mapToDto(internshipPayment);
    }

    public List<InternshipPaymentResponse> getAllInternshipPayments(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<InternshipPayment> internshipPayments = internshipPaymentRepository.findAll();
        return internshipPayments.stream()
                .filter(payment -> payment.getTenantId().equals(tenant.getId()))
                .map(internshipPaymentMapper::mapToDto)
                .toList();
    }

    public InternshipPaymentResponse getInternshipPaymentById(Long tenantId,
                                                              Long paymentId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        InternshipPayment internshipPayment = internshipPaymentRepository.findById(paymentId)
                .filter(payment -> payment.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with id '" + paymentId + "' in the specified tenant"));
        return internshipPaymentMapper.mapToDto(internshipPayment);
    }

    @Transactional
    public InternshipPaymentResponse updateInternshipPayment(Long tenantId,
                                                             Long paymentId,
                                                             InternshipPaymentRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        InternshipPayment internshipPayment = internshipPaymentRepository.findById(paymentId)
                .filter(payment -> payment.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with id: " + paymentId + " in the specified tenant"));
        internshipPayment = internshipPaymentMapper.updateEntity(internshipPayment, request);
        internshipPayment = internshipPaymentRepository.save(internshipPayment);
        return internshipPaymentMapper.mapToDto(internshipPayment);
    }
}
