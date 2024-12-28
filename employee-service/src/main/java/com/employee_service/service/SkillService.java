package com.employee_service.service;

import com.employee_service.dto.clientDto.TenantDto;
import com.employee_service.dto.request.SkillRequest;
import com.employee_service.dto.response.SkillResponse;
import com.employee_service.model.Employee;
import com.employee_service.model.Skill;
import com.employee_service.exception.ResourceNotFoundException;
import com.employee_service.mapper.SkillMapper;
import com.employee_service.repository.SkillRepository;
import com.employee_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;
    private final ValidationUtil validationUtil;

    public SkillResponse addSkill(UUID tenantId,
                                  UUID employeeId,
                                  SkillRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Skill skill = skillMapper.mapToEntity(tenant, employee, request);
        skill = skillRepository.save(skill);
        return skillMapper.mapToDto(skill);
    }

    public List<SkillResponse> getAllSkills(UUID tenantId,
                                            UUID employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        List<Skill> skills = skillRepository.findByEmployeeId(employee.getId());
        return skills.stream()
                .filter(sk -> sk.getTenantId().equals(tenant.getId()))
                .map(skillMapper::mapToDto)
                .toList();
    }

    public List<SkillResponse> getEmployeeSkills(UUID tenantId,
                                                 String employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeByEmployeeId(tenant.getId(), employeeId);
        List<Skill> skills = skillRepository.findByEmployeeId(employee.getId());
        return skills.stream()
                .filter(sk -> sk.getTenantId().equals(tenant.getId()))
                .map(skillMapper::mapToDto)
                .toList();
    }

    public SkillResponse getSkillById(UUID tenantId,
                                      UUID employeeId,
                                      UUID skillId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Skill skill = getSkillById(tenant.getId(), employee, skillId);
        return skillMapper.mapToDto(skill);
    }

    public SkillResponse updateSkill(UUID tenantId,
                                     UUID employeeId,
                                     UUID skillId,
                                     SkillRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Skill skill = getSkillById(tenant.getId(), employee, skillId);
        skill = skillMapper.updateSkill(skill, request);
        skill = skillRepository.save(skill);
        return skillMapper.mapToDto(skill);
    }

    public void deleteSkill(UUID tenantId,
                            UUID employeeId,
                            UUID skillId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Skill skill = getSkillById(tenant.getId(), employee, skillId);
        skillRepository.delete(skill);
    }

    private Skill getSkillById(UUID tenantId, Employee employee, UUID skillId) {

        return skillRepository.findById(skillId)
                .filter(sk -> sk.getTenantId().equals(tenantId))
                .filter(sk -> sk.getEmployee().equals(employee))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Skill not found with id '" + skillId + "'"));
    }
}