package com.training_service.repository;

import com.training_service.model.InternshipPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InternshipPaymentRepository extends JpaRepository<InternshipPayment, UUID> {

    boolean existsByTenantIdAndInternshipStudentId(UUID tenantId, UUID internId);
}
