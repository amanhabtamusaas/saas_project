package com.insa.service;

import com.insa.dto.apiDto.BudgetYearDto;
import com.insa.dto.apiDto.DepartmentDto;
import com.insa.dto.apiDto.LocationDto;
import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.InternshipPlacementRequest;
import com.insa.dto.request.InternshipStudentRequest;
import com.insa.dto.response.InternshipStudentResponse;
import com.insa.entity.InternshipStudent;
import com.insa.entity.University;
import com.insa.enums.InternshipStatus;
import com.insa.enums.Semester;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.InternshipStudentMapper;
import com.insa.repository.InternshipStudentRepository;
import com.insa.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipStudentService {

    private final InternshipStudentRepository internshipStudentRepository;
    private final TenantServiceClient tenantServiceClient;
    private final LeaveServiceClient leaveServiceClient;
    private final UniversityRepository universityRepository;
    private final InternshipStudentMapper internshipStudentMapper;

    @Transactional
    public InternshipStudentResponse addInternshipStudent(Long tenantId,
                                                          InternshipStudentRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        BudgetYearDto budgetYear = leaveServiceClient
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        LocationDto location = tenantServiceClient
                .getLocationById(request.getLocationId(), tenant.getId());
        University university = universityRepository.findById(request.getUniversityId())
                .filter(univ -> univ.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "University not found with id '" + request.getUniversityId() + "' in the specified tenant"));
        if (internshipStudentRepository.existsByTenantIdAndBudgetYearIdAndSemesterAndIdNumber(
                tenant.getId(), budgetYear.getId(),
                Semester.valueOf(request.getSemester().toUpperCase()), request.getIdNumber())) {
            throw new ResourceExistsException(
                    "Internship student with id '" + request.getIdNumber() +
                            "' already exists for semester '" + request.getSemester() + "'");
        }
        InternshipStudent internshipStudent = internshipStudentMapper.mapToEntity(request);
        internshipStudent.setTenantId(tenant.getId());
        internshipStudent.setBudgetYearId(budgetYear.getId());
        internshipStudent.setLocationId(location.getId());
        internshipStudent.setUniversity(university);
        internshipStudent = internshipStudentRepository.save(internshipStudent);
        return internshipStudentMapper.mapToDto(internshipStudent);
    }

    public List<InternshipStudentResponse> getAllInternshipStudents(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<InternshipStudent> internshipStudents = internshipStudentRepository.findAll();
        return internshipStudents.stream()
                .filter(intern -> intern.getTenantId().equals(tenant.getId()))
                .map(internshipStudentMapper::mapToDto)
                .toList();
    }

    public InternshipStudentResponse getInternshipStudentById(Long tenantId,
                                                      Long internId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        InternshipStudent internshipStudent = internshipStudentRepository.findById(internId)
                .filter(intern -> intern.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Internship student not found with id '" + internId + "' in the specified tenant"));
        return internshipStudentMapper.mapToDto(internshipStudent);
    }

    public List<InternshipStudentResponse> getInternshipStudentsByYearAndSemester(Long tenantId,
                                                                                  Long yearId,
                                                                                  String semester) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<InternshipStudent> internshipStudents = internshipStudentRepository
                .findByTenantIdAndBudgetYearIdAndSemester(
                        tenant.getId(), yearId, Semester.valueOf(semester.toUpperCase()));
        return internshipStudents.stream()
                .filter(intern -> intern.getTenantId().equals(tenant.getId()))
                .map(internshipStudentMapper::mapToDto)
                .toList();
    }

    @Transactional
    public InternshipStudentResponse updateInternshipStudent(Long tenantId,
                                                             Long internId,
                                                             InternshipStudentRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        BudgetYearDto budgetYear = leaveServiceClient
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        LocationDto location = tenantServiceClient
                .getLocationById(request.getLocationId(), tenant.getId());
        University university = universityRepository.findById(request.getUniversityId())
                .filter(univ -> univ.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "University not found with id '" + request.getUniversityId() + "' in the specified tenant"));
        InternshipStudent internshipStudent = internshipStudentRepository.findById(internId)
                .filter(intern -> intern.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Internship student not found with id '" + internId + "' in the specified tenant"));
        internshipStudent = internshipStudentMapper.updateEntity(internshipStudent, request);
        if (request.getBudgetYearId() != null) {
            internshipStudent.setBudgetYearId(budgetYear.getId());
        }
        if (request.getLocationId() != null) {
            internshipStudent.setLocationId(location.getId());
        }
        if (request.getUniversityId() != null) {
            internshipStudent.setUniversity(university);
        }
        internshipStudent = internshipStudentRepository.save(internshipStudent);
        return internshipStudentMapper.mapToDto(internshipStudent);
    }

    @Transactional
    public InternshipStudentResponse assignInternToPlacementDepartment(Long tenantId,
                                                                          Long internId,
                                                                          InternshipPlacementRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        DepartmentDto department = tenantServiceClient
                .getDepartmentById(request.getPlacedDepartmentId(), tenant.getId());
        InternshipStudent internshipStudent = internshipStudentRepository.findById(internId)
                .filter(intern -> intern.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Internship student not found with id '" + internId + "' in the specified tenant"));
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
    public InternshipStudentResponse assignInternshipStatus(Long tenantId,
                                                            Long internId,
                                                            String internshipStatus) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        InternshipStudent internshipStudent = internshipStudentRepository.findById(internId)
                .filter(intern -> intern.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Internship student not found with id '" + internId + "' in the specified tenant"));
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
    public void deleteInternshipStudent(Long tenantId,
                                        Long internId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        InternshipStudent internshipStudent = internshipStudentRepository.findById(internId)
                .filter(intern -> intern.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Internship student not found with id '" + internId + "' in the specified tenant"));
        internshipStudentRepository.delete(internshipStudent);
    }
}
