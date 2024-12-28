package com.employee_service.repository;

import com.employee_service.model.TitleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TitleNameRepository extends JpaRepository<TitleName, UUID> {

    boolean existsByTitleNameAndTenantId(String titleName, UUID tenantId);
}
