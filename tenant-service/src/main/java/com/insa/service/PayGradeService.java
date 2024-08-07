package com.insa.service;

import com.insa.dto.requestDto.PayGradeRequest;
import com.insa.dto.responseDto.PayGradeResponse;
import com.insa.entity.PayGrade;
import com.insa.entity.Tenant;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.PayGradeMapper;
import com.insa.repository.PayGradeRepository;
import com.insa.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayGradeService {

    private final PayGradeRepository payGradeRepository;
    private final PayGradeMapper payGradeMapper;
    private final TenantRepository tenantRepository;

    public PayGradeResponse createPayGrade(Long tenantId, PayGradeRequest payGradeRequest) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + tenantId + " "));
        if (payGradeRepository.existsBySalaryStepAndTenantId(payGradeRequest.getSalaryStep(),tenant.getId())) {
            throw new ResourceExistsException("Pay Grade with Name " + payGradeRequest.getSalaryStep() + " already exists");
        }
        PayGrade payGrade = payGradeMapper.mapToEntity (payGradeRequest);
        payGrade.setTenant (tenant);
        payGrade = payGradeRepository.save (payGrade);
        return payGradeMapper.mapToDto (payGrade);
    }

    public List<PayGradeResponse> getAllPayGrades(Long tenantId) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + tenantId + " "));
        List<PayGrade> payGrades = payGradeRepository.findAll ();
        return payGrades.stream ()
                .filter(pay -> pay.getTenant().getId().equals(tenant.getId ()))
                .map (payGradeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public PayGradeResponse getPayGradeById(Long id, Long tenantId) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + tenantId + " "));
        PayGrade payGrade = payGradeRepository.findById (id)
                .filter(pay -> pay.getTenant().getId().equals(tenant.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Pay-grade not found with id: " + id + " for the specified tenant "));
        return payGradeMapper.mapToDto (payGrade);
    }
    public List<PayGradeResponse> getPayGradesByJobGradeId(Long jobGradeId, Long tenantId) {
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

    public PayGradeResponse updatePayGrade(Long id, Long tenantId, PayGradeRequest payGradeRequest) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + tenantId + " "));
        PayGrade payGrade = payGradeRepository.findById (id)
                .filter(pay -> pay.getTenant().getId().equals(tenant.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Pay-grade not found with id: " + id + " for the specified tenant "));
        payGrade = payGradeMapper.updatePayGrade (payGrade, payGradeRequest);
        payGrade = payGradeRepository.save (payGrade);
        return payGradeMapper.mapToDto (payGrade);
    }

    public void deletePayGrade(Long id, Long tenantId) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + tenantId + " "));
        PayGrade payGrade = payGradeRepository.findById (id)
                .filter(pay -> pay.getTenant().getId().equals(tenant.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Pay-grade not found with id: " + id + " for the specified tenant "));
        payGradeRepository.delete (payGrade);
    }
}
