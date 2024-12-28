package com.training_service.service;

import com.training_service.dto.clientDto.BudgetYearDto;
import com.training_service.dto.clientDto.DepartmentDto;
import com.training_service.dto.clientDto.TenantDto;
import com.training_service.dto.request.InternshipPlacementRequest;
import com.training_service.dto.request.InternshipStudentRequest;
import com.training_service.dto.response.InternshipStudentResponse;
import com.training_service.exception.ResourceExistsException;
import com.training_service.model.InternshipStudent;
import com.training_service.enums.InternshipStatus;
import com.training_service.enums.Semester;
import com.training_service.mapper.InternshipStudentMapper;
import com.training_service.repository.InternshipStudentRepository;
import com.training_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InternshipStudentService {

    private final InternshipStudentRepository internshipStudentRepository;
    private final InternshipStudentMapper internshipStudentMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public InternshipStudentResponse addInternshipStudent(UUID tenantId,
                                                          InternshipStudentRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        BudgetYearDto budgetYear = validationUtil
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        if (internshipStudentRepository.existsByTenantIdAndBudgetYearIdAndSemesterAndIdNumber(
                tenant.getId(), budgetYear.getId(),
                Semester.valueOf(request.getSemester().toUpperCase()), request.getIdNumber())) {
            throw new ResourceExistsException(
                    "Internship student with id '" + request.getIdNumber() +
                            "' already exists for semester '" + request.getSemester() + "'");
        }
        InternshipStudent internshipStudent = internshipStudentMapper
                .mapToEntity(tenant, budgetYear, request);
        internshipStudent = internshipStudentRepository.save(internshipStudent);
        return internshipStudentMapper.mapToDto(internshipStudent);
    }

    public List<InternshipStudentResponse> getAllInternshipStudents(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<InternshipStudent> internshipStudents = internshipStudentRepository.findAll();
        return internshipStudents.stream()
                .filter(intern -> intern.getTenantId().equals(tenant.getId()))
                .map(internshipStudentMapper::mapToDto)
                .toList();
    }

    public InternshipStudentResponse getInternshipStudentById(UUID tenantId,
                                                              UUID internId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        InternshipStudent internshipStudent = validationUtil.getStudentById(tenant.getId(), internId);
        return internshipStudentMapper.mapToDto(internshipStudent);
    }

    public List<InternshipStudentResponse> getInternshipStudentsByYearAndSemester(UUID tenantId,
                                                                                  UUID yearId,
                                                                                  String semester) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<InternshipStudent> internshipStudents = internshipStudentRepository
                .findByTenantIdAndBudgetYearIdAndSemester(
                        tenant.getId(), yearId, Semester.valueOf(semester.toUpperCase()));
        return internshipStudents.stream()
                .filter(intern -> intern.getTenantId().equals(tenant.getId()))
                .map(internshipStudentMapper::mapToDto)
                .toList();
    }

    @Transactional
    public InternshipStudentResponse updateInternshipStudent(UUID tenantId,
                                                             UUID internId,
                                                             InternshipStudentRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        InternshipStudent internshipStudent = validationUtil.getStudentById(tenant.getId(), internId);
        internshipStudent = internshipStudentMapper.updateEntity(tenant, internshipStudent, request);
        internshipStudent = internshipStudentRepository.save(internshipStudent);
        return internshipStudentMapper.mapToDto(internshipStudent);
    }

    @Transactional
    public InternshipStudentResponse assignInternToPlacementDepartment(UUID tenantId,
                                                                       UUID internId,
                                                                       InternshipPlacementRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        DepartmentDto department = validationUtil
                .getDepartmentById(tenant.getId(), request.getPlacedDepartmentId());
        InternshipStudent internshipStudent = validationUtil.getStudentById(tenant.getId(), internId);
        if (request.getPlacedDepartmentId() != null) {
            internshipStudent.setPlacedDepartmentId(department.getId());
        }
        if (request.getRemark() != null) {
            internshipStudent.setRemark(request.getRemark());
        }
        internshipStudent = internshipStudentRepository.save(internshipStudent);
        return internshipStudentMapper.mapToDto(internshipStudent);
    }

    @Transactional
    public InternshipStudentResponse assignInternshipStatus(UUID tenantId,
                                                            UUID internId,
                                                            String internshipStatus) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        InternshipStudent internshipStudent = validationUtil.getStudentById(tenant.getId(), internId);
        if (internshipStudent.getPlacedDepartmentId() == null) {
            throw new IllegalStateException("Please assign a placement department for internship student");
        }
        if (internshipStatus != null) {
            internshipStudent.setInternshipStatus(InternshipStatus.valueOf(internshipStatus.toUpperCase()));
        }
        internshipStudent = internshipStudentRepository.save(internshipStudent);
        return internshipStudentMapper.mapToDto(internshipStudent);
    }

    @Transactional
    public void deleteInternshipStudent(UUID tenantId,
                                        UUID internId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        InternshipStudent internshipStudent = validationUtil.getStudentById(tenant.getId(), internId);
        internshipStudentRepository.delete(internshipStudent);
    }
}