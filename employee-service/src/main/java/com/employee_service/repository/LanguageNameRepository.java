package com.employee_service.repository;

import com.employee_service.model.LanguageName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LanguageNameRepository extends JpaRepository<LanguageName, UUID> {

    boolean existsByLanguageNameAndTenantId(String languageName, UUID tenantId);
}
