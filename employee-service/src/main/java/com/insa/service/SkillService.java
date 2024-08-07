package com.insa.service;

import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.SkillRequest;
import com.insa.dto.response.SkillResponse;
import com.insa.entity.Employee;
import com.insa.entity.Skill;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.SkillMapper;
import com.insa.repository.EmployeeRepository;
import com.insa.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;
    private final EmployeeRepository employeeRepository;
    private final TenantServiceClient tenantServiceClient;

    public SkillResponse addSkill(Long tenantId,
                                  Long employeeId,
                                  SkillRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById (employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Skill skill = skillMapper.mapToEntity (request);
        skill.setTenantId (tenant.getId ());
        skill.setEmployee (employee);
        skill = skillRepository.save (skill);
        return skillMapper.mapToDto (skill);
    }

    public List<SkillResponse> getAllSkills(Long tenantId,
                                            Long employeeId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        List<Skill> skills = skillRepository.findByEmployeeId(employee.getId());
        return skills.stream ()
                .filter (sk -> sk.getTenantId ().equals (tenant.getId ()))
                .map (skillMapper::mapToDto)
                .toList();
    }

    public List<SkillResponse> getEmployeeSkills(Long tenantId,
                                                 String employeeId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        List<Skill> skills = skillRepository.findByEmployeeId(employee.getId());
        return skills.stream ()
                .filter (sk -> sk.getTenantId ().equals (tenant.getId ()))
                .map (skillMapper::mapToDto)
                .toList();
    }

    public SkillResponse getSkillById(Long tenantId,
                                      Long employeeId,
                                      Long skillId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Skill skill = skillRepository.findById (skillId)
                .filter (sk -> sk.getTenantId ().equals (tenant.getId ()))
                .filter (sk -> sk.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Skill not found with id '" + skillId + "' in the specified employee"));
        return skillMapper.mapToDto (skill);
    }

    public SkillResponse updateSkill(Long tenantId,
                                     Long employeeId,
                                     Long skillId,
                                     SkillRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Skill skill = skillRepository.findById (skillId)
                .filter (sk -> sk.getTenantId ().equals (tenant.getId ()))
                .filter (sk -> sk.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Skill not found with id '" + skillId + "' in the specified employee"));
        skill = skillMapper.updateSkill (skill, request);
        skill = skillRepository.save (skill);
        return skillMapper.mapToDto (skill);
    }

    public void deleteSkill(Long tenantId,
                            Long employeeId,
                            Long skillId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Skill skill = skillRepository.findById (skillId)
                .filter (sk -> sk.getTenantId ().equals (tenant.getId ()))
                .filter (sk -> sk.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Sill not found with id '" + skillId + "' in the specified employee"));
        skillRepository.delete (skill);
    }
}
