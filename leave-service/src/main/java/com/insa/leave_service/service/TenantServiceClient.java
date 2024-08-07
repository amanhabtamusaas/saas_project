package com.insa.leave_service.service;
import com.insa.leave_service.dto.DepartmentDto;
import com.insa.leave_service.dto.TenantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "TENANT-SERVICE", url = "${tenant-service.base-url}")
public interface TenantServiceClient {

    @GetMapping("/api/tenants/get/{id}")
    public TenantDto getTenantById(@PathVariable Long id);

    @GetMapping("/api/departments/{tenant-id}/get/{id}")
    public DepartmentDto getDepartmentById(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId);
}
