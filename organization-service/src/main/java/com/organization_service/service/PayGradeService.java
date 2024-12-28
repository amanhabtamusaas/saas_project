package com.organization_service.service;

import com.organization_service.dto.requestDto.PayGradeRequest;
import com.organization_service.dto.responseDto.PayGradeResponse;
import com.organization_service.model.PayGrade;
import com.organization_service.model.Tenant;
import com.organization_service.exception.ResourceExistsException;
import com.organization_service.exception.ResourceNotFoundException;
import com.organization_service.mapper.PayGradeMapper;
import com.organization_service.repository.PayGradeRepository;
import com.organization_service.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayGradeService {

    private final PayGradeRepository payGradeRepository;
    private final PayGradeMapper payGradeMapper;
    private final TenantRepository tenantRepository;

    public PayGradeResponse createPayGrade(UUID tenantId, PayGradeRequest payGradeRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));

        if (payGradeRepository.existsBySalaryStepAndTenantId(
                payGradeRequest.getSalaryStep(), tenant.getId())) {
            throw new ResourceExistsException("Pay Grade with Name " +
                    payGradeRequest.getSalaryStep() + " already exists");
        }

        PayGrade payGrade = payGradeMapper.mapToEntity(payGradeRequest);
        payGrade.setTenant(tenant);
        payGrade = payGradeRepository.save(payGrade);
        return payGradeMapper.mapToDto(payGrade);
    }

    public List<PayGradeResponse> getAllPayGrades(UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));

        List<PayGrade> payGrades = payGradeRepository.findAll();
        return payGrades.stream()
                .filter(pay -> pay.getTenant().getId().equals(tenant.getId()))
                .map(payGradeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public PayGradeResponse getPayGradeById(UUID id, UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));

        PayGrade payGrade = payGradeRepository.findById(id)
                .filter(pay -> pay.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pay-grade not found with id: " + id + " for the specified tenant "));
        return payGradeMapper.mapToDto(payGrade);
    }

    public List<PayGradeResponse> getPayGradesByJobGradeId(UUID jobGradeId, UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));

        List<PayGrade> payGrades = payGradeRepository.findByJobGradeId(jobGradeId)
                .stream()
                .filter(pay -> pay.getTenant().getId().equals(tenant.getId()))
                .toList();

        return payGrades.stream()
                .map(payGradeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public PayGradeResponse updatePayGrade(UUID id, UUID tenantId, PayGradeRequest payGradeRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));

        PayGrade payGrade = payGradeRepository.findById(id)
                .filter(pay -> pay.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pay-grade not found with id: " + id + " for the specified tenant "));

        payGrade = payGradeMapper.updatePayGrade(payGrade, payGradeRequest);
        payGrade = payGradeRepository.save(payGrade);
        return payGradeMapper.mapToDto(payGrade);
    }

    public void deletePayGrade(UUID id, UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));

        PayGrade payGrade = payGradeRepository.findById(id)
                .filter(pay -> pay.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pay-grade not found with id: " + id + " for the specified tenant "));
        payGradeRepository.delete(payGrade);
    }
}