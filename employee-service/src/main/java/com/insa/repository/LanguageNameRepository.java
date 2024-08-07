package com.insa.repository;

import com.insa.entity.LanguageName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageNameRepository extends JpaRepository<LanguageName, Long> {

    boolean existsByLanguageNameAndTenantId(String languageName, Long tenantId);
}
