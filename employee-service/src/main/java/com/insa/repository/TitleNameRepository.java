package com.insa.repository;

import com.insa.entity.TitleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleNameRepository extends JpaRepository<TitleName, Long> {

    boolean existsByTitleNameAndTenantId(String titleName, Long tenantId);
}
