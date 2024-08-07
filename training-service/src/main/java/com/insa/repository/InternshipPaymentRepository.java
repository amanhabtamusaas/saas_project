package com.insa.repository;

import com.insa.entity.InternshipPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternshipPaymentRepository extends JpaRepository<InternshipPayment, Long> {

    boolean existsByTenantIdAndInternshipStudentId(Long tenantId, Long internId);
}
